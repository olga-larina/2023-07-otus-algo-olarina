package ru.otus.sort;

import java.util.Arrays;

/**
 * Поразрядная сортировка (LSD - lest significant digits)
 */
public class RadixSort extends SortIntAlgo {

    public RadixSort(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() {
        if (n == 0 || n == 1) {
            return;
        }

        // находим максимум (для определения числа разрядов)
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (more(arr[i], max)) {
                max = arr[i];
            }
        }

        int[] tmp = new int[arr.length];
        int[] cnt = new int[10];

        int r = 1 + (int) Math.log10(max); // максимальное число разрядов

        // обрабатываем поразрядно с конца - единицы, сотни, тысячи, ...
        int k = 1;
        for (int j = 1; j <= r; j++) {
            // обнуляем массив
            Arrays.fill(cnt, 0);

            // считаем числа
            for (int i : arr) {
                cnt[(i / k) % 10]++;
            }

            // накопленный итог
            for (int i = 1; i < cnt.length; i++) {
                cnt[i] += cnt[i - 1];
            }

            // выписываем во временный массив на нужные индексы
            for (int i = arr.length - 1; i >= 0; i--) {
                int val = (arr[i] / k) % 10;
                int pos = --cnt[val];
                tmp[pos] = arr[i];
            }

            // копируем в первоначальный массив
            for (int i = 0; i < tmp.length; i++) {
                arr[i] = tmp[i];
            }

            k = k * 10;
        }
    }

}
