package ru.otus.sort;

/**
 * Поразрядная сортировка (MSD - most significant digits)
 */
public class RadixSortMSD extends SortIntAlgo {

    public RadixSortMSD(int[] arr) {
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

        int r = 1 + (int) Math.log10(max); // максимальное число разрядов
        int k = 1;
        for (int j = 1; j < r; j++) {
            k = k * 10;
        }
        // обрабатываем поразрядно с начала - единицы, сотни, тысячи, ...
        sort0(k, tmp, 0, n - 1);
    }

    private void sort0(int k, int[] tmp, int lo, int hi) {
        if (lo >= hi || k == 0) {
            return;
        }

        int[] cnt = new int[10];

        // считаем числа
        for (int i = lo; i <= hi; i++) {
            cnt[(arr[i] / k) % 10]++;
        }

        // накопленный итог
        for (int i = 1; i < cnt.length; i++) {
            cnt[i] += cnt[i - 1];
        }

        // выписываем во временный массив на нужные индексы
        for (int i = hi; i >= lo; i--) {
            int val = (arr[i] / k) % 10;
            int pos = --cnt[val];
            tmp[pos + lo] = arr[i];
        }

        // копируем в первоначальный массив
        for (int i = lo; i <= hi; i++) {
            arr[i] = tmp[i];
        }

        // сортируем рекурсивно по группам
        k = k / 10;
        for (int i = 0; i < cnt.length; i++) {
            sort0(k, tmp, lo + cnt[i], (i == cnt.length - 1) ? hi : (lo + cnt[i + 1] - 1));
        }
    }
}
