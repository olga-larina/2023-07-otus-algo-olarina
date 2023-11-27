package ru.otus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Алгоритм RLE (Run-Length encoding) для сжатия / распаковки файлов
 */
public class RLEPlain implements RLE {

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
                byte cnt; // т.к. используем по 1 байту, необходимо, чтобы количество тоже помещалось в 1 байт
                int i = 0;
                while (i < original.length) {
                    cnt = 1;
                    while (i < original.length - 1 && original[i] == original[i + 1] && cnt < Byte.MAX_VALUE) {
                        cnt++;
                        i++;
                    }
                    compressedStream.write(cnt);
                    compressedStream.write(original[i]);
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
                int cnt;
                int value;
                while (compressedStream.available() > 0) {
                    cnt = compressedStream.read();
                    value = compressedStream.read();
                    while (cnt > 0) {
                        decompressedStream.write(value);
                        cnt--;
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
        RLE rlePlain = new RLEPlain();
        byte[] compressed = rlePlain.compress(original.getBytes());
        System.out.println(Arrays.toString(compressed)); // 9# 4. 1# 1. 3# 1. 1# 1. 1# 1. 2# 2. 1# 3. 9#  (# - 35; . - 46)
        byte[] decompressed = rlePlain.decompress(compressed);
        System.out.println(new String(decompressed));
    }
}
