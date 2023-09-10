package ru.otus;

import ru.otus.sort.*;

import java.util.Random;

public class Hw09Main {

    public static void main(String[] args) throws Exception {
        testWithData();
    }

    public static void testWithData() throws Exception {
        for (int n = 10; n <= 1_000_000_000; n *= 10) {
            for (int r : new int[] {999, 65535}) {
                int[] arr = random(n, r);
//                SortIntAlgo sort = new BucketSort(arr);
//                SortIntAlgo sort = new RadixSortMSD(arr);
//                SortIntAlgo sort = new RadixSort(arr);
                SortIntAlgo sort = new CountingSort(arr);
                long start = System.currentTimeMillis();
                sort.sort();
                long ms = System.currentTimeMillis() - start;
                boolean sorted = testIfSorted(arr);
                System.out.println(sort + "\tr=" + r + "\ttime=" + ms + " ms\t sorted=" + sorted);
            }
        }
    }

    static int[] digits(int n) {
        int[] arr = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(10);
        }
        return arr;
    }

    static int[] random(int n, int r) {
        int[] arr = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(r + 1);
        }
        return arr;
    }

    static boolean testIfSorted(int[] arr) {
        boolean sorted = true;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                sorted = false;
                break;
            }
        }
        return sorted;
    }

}