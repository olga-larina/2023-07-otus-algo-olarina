package ru.otus.sort;

import java.math.BigInteger;

/**
 * Сортировка слиянием
 */
public class MergeSort extends SortIntAlgo {

    public MergeSort(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() throws InterruptedException {
        sort0(0, arr.length - 1, new int[arr.length]);
    }

    private void sort0(int lo, int hi, int[] arrTmp) throws InterruptedException {
        if (lo >= hi) {
            return;
        }
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Too long execution");
        }
        int mid = lo + (hi - lo) / 2;
        sort0(lo, mid, arrTmp);
        sort0(mid + 1, hi, arrTmp);
        merge(lo, mid, hi, arrTmp);
    }

    private void merge(int lo, int mid, int hi, int[] arrTmp) {
        for (int t = lo; t <= hi; t++) {
            arrTmp[t] = arr[t];
        }
        int i = lo;
        int j = mid + 1;
        int k = lo;
        while (i <= mid && j <= hi) {
            if (!more(arrTmp[i], arrTmp[j])) {
                arr[k++] = arrTmp[i++];
            } else {
                arr[k++] = arrTmp[j++];
            }
        }
        while (i <= mid) {
            arr[k++] = arrTmp[i++];
        }
        while (j <= hi) {
            arr[k++] = arrTmp[j++];
        }
        asg = asg.add(BigInteger.valueOf(2).multiply(BigInteger.valueOf(hi - lo + 1)));
    }
}
