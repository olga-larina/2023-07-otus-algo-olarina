package ru.otus.sort;

import java.math.BigInteger;

/**
 * Сортировка вставками: бинарный поиск места вставки
 */
public class InsertSortBinary extends SortIntAlgo {

    public InsertSortBinary(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() throws InterruptedException {
        int i;
        int cur;
        for (int j = 1; j < n; j++) {
            cur = arr[j];
            asg = asg.add(BigInteger.ONE);

            int index = binarySearch(0, j - 1, cur);

            for (i = j - 1; i >= index; i--) {
                arr[i + 1] = arr[i];
                asg = asg.add(BigInteger.ONE);

                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Too long execution");
                }
            }

            arr[i + 1] = cur;
            asg = asg.add(BigInteger.ONE);
        }
    }

    private int binarySearch(int lo, int hi, int val) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            cmp = cmp.add(BigInteger.ONE);
            if (val >= arr[mid]) {
                lo = mid + 1; // для сохранения стабильности сортировки
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }
}
