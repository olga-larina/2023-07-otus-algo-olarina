package ru.otus.sort;

import java.math.BigInteger;

public abstract class SortIntAlgo {

    protected final int[] arr;
    protected final int n;
    protected BigInteger asg; // количество присвоений
    protected BigInteger cmp; // количество сравнений

    public SortIntAlgo(int[] arr) {
        this.arr = arr;
        this.n = arr.length;
        this.asg = BigInteger.ZERO;
        this.cmp = BigInteger.ZERO;
    }

    public final void sort()  throws InterruptedException {
        sort0();
    }

    abstract protected void sort0() throws InterruptedException;

    protected boolean less(int val1, int val2) {
        cmp = cmp.add(BigInteger.ONE);
        return val1 < val2;
    }

    protected boolean lessEq(int val1, int val2) {
        cmp = cmp.add(BigInteger.ONE);
        return val1 <= val2;
    }

    protected boolean eq(int val1, int val2) {
        cmp = cmp.add(BigInteger.ONE);
        return val1 == val2;
    }

    protected boolean more(int val1, int val2) {
        cmp = cmp.add(BigInteger.ONE);
        return val1 > val2;
    }

    protected boolean moreEq(int val1, int val2) {
        cmp = cmp.add(BigInteger.ONE);
        return val1 >= val2;
    }

    protected void swap(int i, int j) {
//        if (i != j) {  // из-за этого замедление работы
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        asg = asg.add(BigInteger.valueOf(3));
//        }
    }

    protected int findMax(int to) {
        return findMax(0, to);
    }

    protected int findMax(int from, int to) {
        int maxIndex = from;
        for (int i = from + 1; i <= to; i++) {
            if (more(arr[i], arr[maxIndex])) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    @Override
    public String toString() {
//        return String.format("n=%10d asg=%15d cmp=%15d", n, asg, cmp);
        return String.format("n=%10d", n);
    }
}
