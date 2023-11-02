package ru.otus.common;

import java.util.NoSuchElementException;

/**
 * Приоритетная очередь на минимум
 */
public class MinHeap<T extends Comparable<T>> {

    private final T[] pq; // индексация с 1
    private int n;

    @SuppressWarnings("unchecked")
    public MinHeap(int capacity) {
        this.pq = (T[]) new Comparable[capacity + 1];
        this.n = 0;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void insert(T x) {
        if (n == pq.length - 1) {
            throw new IllegalStateException("Queue is full");
        }
        n++;
        pq[n] = x;
        shiftUp(n);
    }

    public T min() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return pq[1];
    }

    public T delMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T x = pq[1];
        swap(1, n);
        pq[n] = null;
        n--;
        shiftDown(1);
        return x;
    }

    private void shiftUp(int k) {
        int parent;
        while ((parent = parent(k)) > 0 && greater(parent, k)) {
            swap(parent, k);
            k = parent;
        }
    }

    private void shiftDown(int k) {
        int left;
        int right;
        while ((left = left(k)) <= n) {
            right = right(k);
            int max = (left == n || greater(right, left)) ? left : right;
            if (greater(k, max)) {
                swap(max, k);
                k = max;
            } else {
                break;
            }
        }
    }

    private int parent(int k) {
        return k / 2;
    }

    private int left(int k) {
        return k * 2;
    }

    private int right(int k) {
        return k * 2 + 1;
    }

    private void swap(int i, int j) {
        T tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

    private boolean greater(int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }

    public static void main(String[] args) {
        MinHeap<Integer> heap1 = new MinHeap<>(10);
        for (int i = 10; i >= 1; i--) {
            heap1.insert(i);
        }
        while (!heap1.isEmpty()) {
            System.out.print(heap1.delMin() + " ");
        }

        System.out.println();

        int[] arr = {10, 8, 7, 1, 6, 4, 9, 5, 2, 3};
        MinHeap<Integer> heap2 = new MinHeap<>(arr.length);
        for (int i : arr) {
            heap2.insert(i);
        }
        while (!heap2.isEmpty()) {
            System.out.print(heap2.delMin() + " ");
        }
    }
}
