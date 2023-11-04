package ru.otus.common;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Приоритетная очередь на минимум с поддержкой индексов
 * Есть возможность обратиться к уже существующему в очереди ключу, поменять и удалить
 */
public class IndexMinHeap<T extends Comparable<T>> {

    private final int[] pq; // приоритетная очередь (индексация с 1)
                            // индекс - расположение в приоритетной очереди n,
                            // значение - переданный индекс элемента i
    private final int[] qp; // быстрый поиск элементов по индексу в приоритетной очереди;
                            // индекс - переданный индекс элемента i,
                            // значение - расположение в приоритетной очереди n, т.е. qp[i] = n; pq[n] = i
    private final T[] keys; // значение для i-го элемента, которое определяет приоритет
    private int n; // количество элементов в приоритетной очереди
    private final int capacity;

    @SuppressWarnings("unchecked")
    public IndexMinHeap(int capacity) {
        this.capacity = capacity;
        this.pq = new int[capacity + 1];
        this.qp = new int[capacity + 1];
        Arrays.fill(qp, -1);
        this.keys = (T[]) new Comparable[capacity + 1];
        this.n = 0;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(int i) {
        validateIndex(i);
        return qp[i] != -1;
    }

    /**
     * Вставить элемент x с индексом idx в очередь
     */
    public void insert(int idx, T x) {
        validateIndex(idx);
        if (n == pq.length - 1) {
            throw new IllegalStateException("Queue is full");
        }
        if (contains(idx)) {
            throw new IllegalArgumentException("index is already in the priority queue");
        }
        n++;
        pq[n] = idx;
        qp[idx] = n;
        keys[idx] = x;
        shiftUp(n);
    }

    /**
     * Индекс минимального элемента в приоритетной очереди
     */
    public int minIndex() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return pq[1];
    }

    /**
     * Минимальный элемент в приоритетной очереди
     */
    public T min() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return keys[pq[1]];
    }

    /**
     * Удалить минимальный элемент и вернуть соответствующий индекс
     */
    public int delMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = pq[1];
        swap(1, n);
        qp[idx] = -1;
        keys[idx] = null;
        n--;
        shiftDown(1);
        return idx;
    }

    public T keyOf(int i) {
        validateIndex(i);
        if (!contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }
        return keys[i];
    }

    /**
     * Изменить значение по индексу
     * Необходимо выполнить перебалансировку приоритетной очереди (и вверх, и вниз)
     */
    public void changeKey(int i, T key) {
        validateIndex(i);
        if (!contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }
        keys[i] = key;
        shiftUp(qp[i]);
        shiftDown(qp[i]);
    }

    /**
     * Уменьшить значение по индексу
     * Требуется выполнить только shiftUp, т.к. уменьшили значение
     */
    public void decreaseKey(int i, T key) {
        validateIndex(i);
        if (!contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }
        if (keys[i].compareTo(key) == 0) {
            throw new IllegalArgumentException("Calling decreaseKey() with a key equal to the key in the priority queue");
        }
        if (keys[i].compareTo(key) < 0) {
            throw new IllegalArgumentException("Calling decreaseKey() with a key strictly greater than the key in the priority queue");
        }
        keys[i] = key;
        shiftUp(qp[i]);
    }

    /**
     * Увеличить значение по индексу
     * Требуется выполнить только shiftDown, т.к. увеличили значение
     */
    public void increaseKey(int i, T key) {
        validateIndex(i);
        if (!contains(i))
            throw new NoSuchElementException("index is not in the priority queue");
        if (keys[i].compareTo(key) == 0) {
            throw new IllegalArgumentException("Calling increaseKey() with a key equal to the key in the priority queue");
        }
        if (keys[i].compareTo(key) > 0) {
            throw new IllegalArgumentException("Calling increaseKey() with a key strictly less than the key in the priority queue");
        }
        keys[i] = key;
        shiftDown(qp[i]);
    }

    /**
     * Удалить индекс и значение из приоритетной очереди
     */
    public void delete(int i) {
        validateIndex(i);
        if (!contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }
        int index = qp[i];
        swap(index, n);
        qp[i] = -1;
        keys[i] = null;
        n--;
        shiftUp(index);
        shiftDown(index);
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

    /**
     * обменять элементы на индексах i и j в приоритетной очереди
     * дополнительно меняются значения в qp
     */
    private void swap(int i, int j) {
        int tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    private void validateIndex(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("index is negative: " + i);
        }
        if (i >= capacity) {
            throw new IllegalArgumentException("index >= capacity: " + i);
        }
    }
}
