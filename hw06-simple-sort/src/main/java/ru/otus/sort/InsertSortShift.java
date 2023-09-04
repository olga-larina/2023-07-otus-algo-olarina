package ru.otus.sort;

import java.math.BigInteger;

/**
 * Сортировка вставками: сдвиг элементов вместо обмена
 */
public class InsertSortShift extends SortIntAlgo {

    public InsertSortShift(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() throws InterruptedException {
        int i;
        int cur;
        for (int j = 1; j < n; j++) {
            cur = arr[j];
            asg = asg.add(BigInteger.ONE);

            for (i = j - 1; i >= 0 && more(arr[i], cur); i--) {
                arr[i + 1] = arr[i];

                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Too long execution");
                }
            }
            asg = asg.add(BigInteger.valueOf(j - 1 - i));

            arr[i + 1] = cur;
            asg = asg.add(BigInteger.ONE);
        }
    }
}
