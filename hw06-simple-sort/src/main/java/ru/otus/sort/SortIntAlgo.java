package ru.otus.sort;

import java.math.BigInteger;
import java.util.Arrays;

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

    public final void sort()  throws InterruptedException{
        sort0();
    }

    abstract protected void sort0() throws InterruptedException;

    protected boolean more(int val1, int val2) {
        cmp = cmp.add(BigInteger.ONE);
        return val1 > val2;
    }

    protected void swap(int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        asg = asg.add(BigInteger.valueOf(3));
    }

    @Override
    public String toString() {
        return String.format("n=%10d asg=%15d cmp=%15d", n, asg, cmp);
    }
}
