package ru.otus.sort;

import java.util.Random;

/**
 * Быстрая сортировка (с обработкой чисел, равных опорному => работает без StackOverflow для массива digits)
 */
public class QuickSortThreeWay extends SortIntAlgo {

    private final Random random = new Random();

    public QuickSortThreeWay(int[] arr) {
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

        int index = random.nextInt(lo, hi + 1);
        swap(index, lo);
        int val = arr[lo];

        int i = lo + 1;
        int lt = lo;
        int gt = hi;
        while (i <= gt) {
            if (less(arr[i], val)) {
                swap(i, lt);
                i++;
                lt++;
            } else if (eq(arr[i], val)) {
                i++;
            } else {
                swap(i, gt);
                gt--;
            }
        }

        sort0(lo, lt - 1);
        sort0(gt + 1, hi);
    }
}
