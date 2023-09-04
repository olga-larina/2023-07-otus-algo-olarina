package ru.otus.sort;

/**
 * Сортировка Шелла (обычная)
 */
public class ShellSort extends SortIntAlgo {

    public ShellSort(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() throws InterruptedException {
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int j = gap; j < n; j++) {
                for (int i = j; i >= gap && more(arr[i - gap], arr[i]); i -= gap) {
                    swap(i - gap, i);

                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException("Too long execution");
                    }
                }
            }
        }
    }
}
