package ru.otus;

import java.nio.file.Path;

public interface RLE {

    /**
     * Сжать содержимое файла
     * @param originalFile файл для сжатия
     * @param compressedFile сжатый файл
     */
    void compress(Path originalFile, Path compressedFile);

    /**
     * Сжать массив
     * @param original массив для сжатия
     * @return сжатый массив
     */
    byte[] compress(byte[] original);

    /**
     * Распаковать содержимое файла
     * @param compressedFile сжатый файл
     * @param decompressedFile распакованный файл
     */
    void decompress(Path compressedFile, Path decompressedFile);

    /**
     * Распаковать массив
     * @param compressed сжатый массив
     * @return распакованный массив
     */
    byte[] decompress(byte[] compressed);

}
