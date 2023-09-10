package ru.otus.sort;

/**
 * Сортировка подсчётом
 */
public class CountingSort extends SortIntAlgo {

    public CountingSort(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() {
        if (n == 0 || n == 1) {
            return;
        }

        // находим минимум / максимум
        int min = arr[0];
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (more(arr[i], max)) {
                max = arr[i];
            }
            if (less(arr[i], min)) {
                min = arr[i];
            }
        }

        // отступ
        int offset = min;

        // считаем числа
        int[] cnt = new int[max - min + 1];
        for (int i : arr) {
            cnt[i - offset]++;
        }

        // накопленный итог
        for (int j = 1; j < cnt.length; j++) {
            cnt[j] += cnt[j - 1];
        }

        // выписываем во временный массив на нужные индексы
        int[] tmp = new int[arr.length];
        for (int j = arr.length - 1; j >= 0; j--) {
            int pos = --cnt[arr[j] - offset];
            tmp[pos] = arr[j];
        }

        // копируем в первоначальный массив
        for (int i = 0; i < tmp.length; i++) {
            arr[i] = tmp[i];
        }
    }

}
