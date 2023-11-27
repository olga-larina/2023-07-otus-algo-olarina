package ru.otus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Улучшенный алгоритм RLE (Run-Length encoding) для сжатия / распаковки файлов
 */
public class RLEImproved implements RLE {

    @Override
    public void compress(Path originalFile, Path compressedFile) {
        try {
            byte[] original = Files.readAllBytes(originalFile);
            compress(original, compressedStream -> {
                try {
                    Files.createDirectories(compressedFile.getParent());
                    Files.deleteIfExists(compressedFile);
                    Files.createFile(compressedFile);
                    try (OutputStream outputStream = new FileOutputStream(compressedFile.toFile())) {
                        compressedStream.writeTo(outputStream);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] compress(byte[] original) {
        AtomicReference<byte[]> compressed = new AtomicReference<>();
        compress(original, compressedStream -> {
            compressed.set(compressedStream.toByteArray());
        });
        return compressed.get();
    }

    private void compress(byte[] original, Consumer<ByteArrayOutputStream> processCompressedStream) {
        try {
            try (ByteArrayOutputStream compressedStream = new ByteArrayOutputStream()) {
                // т.к. используем по 1 байту, необходимо, чтобы количество тоже помещалось в 1 байт
                byte cntPos; // количество повторяющихся символов
                byte cntNeg; // количество символов без повторов
                int st; // начало группы символов
                int i = 0; // индекс
                while (i < original.length) {
                    st = i;
                    cntPos = 1;
                    cntNeg = -1;
                    // аналогично обычному алгоритму считаем
                    while (i < original.length - 1 && original[i] == original[i + 1] && cntPos < Byte.MAX_VALUE) {
                        cntPos++;
                        i++;
                    }
                    // если всего один повторяющийся символ, то пробуем добавить к нему последующие одиночные символы
                    if (cntPos == 1) {
                        while (i < original.length - 1 && original[i] != original[i + 1] && cntNeg > Byte.MIN_VALUE) {
                            cntNeg--;
                            i++;
                        }
                        // если последний просмотренный символ входит в группу с повторами, то откатываемся на 1 назад
                        if (i < original.length - 1 && original[i] == original[i + 1]) {
                            cntNeg++;
                            i--;
                        }
                        // записываем отрицательное кол-во и символы по одному
                        compressedStream.write(cntNeg);
                        for (int j = st; j <= i; j++) {
                            compressedStream.write(original[j]);
                        }
                    } else {
                        // записываем положительное кол-во и соответствующий символ
                        compressedStream.write(cntPos);
                        compressedStream.write(original[i]);
                    }
                    i++;
                }
                processCompressedStream.accept(compressedStream);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void decompress(Path compressedFile, Path decompressedFile) {
        try {
            byte[] compressed = Files.readAllBytes(compressedFile);
            decompress(compressed, decompressedStream -> {
                try {
                    Files.createDirectories(decompressedFile.getParent());
                    Files.deleteIfExists(decompressedFile);
                    Files.createFile(decompressedFile);
                    try (OutputStream outputStream = new FileOutputStream(decompressedFile.toFile())) {
                        decompressedStream.writeTo(outputStream);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] decompress(byte[] compressed) {
        AtomicReference<byte[]> decompressed = new AtomicReference<>();
        decompress(compressed, decompressedStream -> {
            decompressed.set(decompressedStream.toByteArray());
        });
        return decompressed.get();
    }

    private void decompress(byte[] compressed, Consumer<ByteArrayOutputStream> processDecompressedStream) {
        try {
            try (
                ByteArrayOutputStream decompressedStream = new ByteArrayOutputStream();
                ByteArrayInputStream compressedStream = new ByteArrayInputStream(compressed);
            ) {
                byte cnt;
                int value;
                while (compressedStream.available() > 0) {
                    cnt = (byte) compressedStream.read(); // приводим обратно к отрицательным значениям (т.к. read() возвращает только положительные, т.е. & 0xff)
                    if (cnt < 0) {
                        while (cnt < 0) {
                            value = compressedStream.read();
                            decompressedStream.write(value);
                            cnt++;
                        }
                    } else {
                        value = compressedStream.read();
                        while (cnt > 0) {
                            decompressedStream.write(value);
                            cnt--;
                        }
                    }
                }
                processDecompressedStream.accept(decompressedStream);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String original =
            "########" +
            "#....#.#" +
            "##.#.#.#" +
            "#..#...#" +
            "########";
        RLE rleImproved = new RLEImproved();
        byte[] compressed = rleImproved.compress(original.getBytes());
        System.out.println(Arrays.toString(compressed));  // 9# 4. -2#. 3# -5.#.#. 2# 2. -1# 3. 9#  (# - 35; . - 46)
        byte[] decompressed = rleImproved.decompress(compressed);
        System.out.println(new String(decompressed));
    }
}
