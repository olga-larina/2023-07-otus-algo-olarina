package ru.otus.sort;

/**
 * Сортировка Шелла - шаг 1, 4, 13, 40, 121, ... (Кнут): (3^k - 1) / 2, не больше N/3, худшее время - N^1.5
 */
public class ShellSortKnuth extends SortIntAlgo {

    public ShellSortKnuth(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() throws InterruptedException {
        int gap = 1;
        while (gap < n / 3) {
            gap = gap * 3 + 1;
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
            gap = gap / 3;
        }
    }
}
