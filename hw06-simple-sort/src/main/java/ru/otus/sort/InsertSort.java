package ru.otus.sort;

/**
 * Сортировка вставками (обычная)
 */
public class InsertSort extends SortIntAlgo {

    public InsertSort(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() throws InterruptedException {
        for (int j = 1; j < n; j++) {
            for (int i = j - 1; i >= 0 && more(arr[i], arr[i + 1]); i--) {
                swap(i, i + 1);

                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Too long execution");
                }
            }
        }
    }
}
