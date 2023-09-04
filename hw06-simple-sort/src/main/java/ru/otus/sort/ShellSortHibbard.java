package ru.otus.sort;

/**
 * Сортировка Шелла - шаг 1, 3, 7, 15, 31, 63, ... (Хиббард): (2^k - 1), худшее время - N^1.5
 */
public class ShellSortHibbard extends SortIntAlgo {

    public ShellSortHibbard(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() throws InterruptedException {
        int gap = 1;
        while (gap < n) {
            gap = gap * 2 + 1;
        }
        while (gap >= 1) {
            for (int j = gap; j < n; j++) {
                for (int i = j; i >= gap && more(arr[i - gap], arr[i]); i -= gap) {
                    swap(i - gap, i);

                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException("Too long execution");
                    }
                }
            }
            gap = gap / 2;
        }
    }
}
