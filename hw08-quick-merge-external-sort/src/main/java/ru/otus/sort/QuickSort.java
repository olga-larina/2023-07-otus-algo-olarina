package ru.otus.sort;

import java.util.Random;

/**
 * Быстрая сортировка (StackOverflow для массива digits с большим количеством дублей)
 */
public class QuickSort extends SortIntAlgo {

    private final Random random = new Random();

    public QuickSort(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() throws InterruptedException {
        sort0(0, arr.length - 1);
    }

    private void sort0(int lo, int hi) throws InterruptedException {
        if (lo >= hi) {
            return;
        }
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Too long execution");
        }
        int p = split(lo, hi);
        sort0(lo, p - 1);
        sort0(p + 1, hi);
    }

    private int split(int lo, int hi) {
        int index = random.nextInt(lo, hi + 1);
        swap(index, hi);
        int val = arr[hi];

        int lt = lo - 1;
        for (int i = lo; i <= hi; i++) {
            if (lessEq(arr[i], val)) {
                swap(++lt, i);
            }
        }

        return lt;
    }
}
