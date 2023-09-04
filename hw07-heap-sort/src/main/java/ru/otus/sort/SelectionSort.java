package ru.otus.sort;

/**
 * Сортировка выбором
 */
public class SelectionSort extends SortIntAlgo {

    public SelectionSort(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() throws InterruptedException {
        for (int j = n - 1; j > 0; j--) {
            swap(findMax(j), j);

            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Too long execution");
            }
        }
    }
}
