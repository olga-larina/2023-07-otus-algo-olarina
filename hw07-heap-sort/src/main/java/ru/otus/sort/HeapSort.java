package ru.otus.sort;

/**
 * Пирамидальная сортировка
 */
public class HeapSort extends SortIntAlgo {

    public HeapSort(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() throws InterruptedException {
        // построение кучи
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(i, n);

            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Too long execution");
            }
        }

        // сортировка
        for (int j = n - 1; j > 0; j--) {
            swap(0, j);
            heapify(0, j);

            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Too long execution");
            }
        }
    }

    private void heapify(int root, int size) {
        int x = root;
        int left = 2 * x + 1;
        int right = 2 * x + 2;
        if (left < size && more(arr[left], arr[x])) {
            x = left;
        }
        if (right < size && more(arr[right], arr[x])) {
            x = right;
        }
        if (x == root) {
            return;
        }
        swap(root, x);
        heapify(x, size);
    }

}
