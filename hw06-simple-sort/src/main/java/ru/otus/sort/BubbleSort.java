package ru.otus.sort;

/**
 * Сортировка пузырьком (обычная)
 */
public class BubbleSort extends SortIntAlgo {

    public BubbleSort(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() throws InterruptedException {
        for (int j = n - 1; j > 0; j--) {
            for (int i = 0; i < j; i++) {
                if (more(arr[i], arr[i + 1])) {
                    swap(i, i + 1);
                }

                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Too long execution");
                }
            }
        }
    }
}
