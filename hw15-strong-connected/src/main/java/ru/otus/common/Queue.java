package ru.otus.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Очередь
 */
public class Queue<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int n;

    public Queue() {
        head = null;
        tail = null;
        n = 0;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void enqueue(T item) {
        Node<T> node = new Node<>();
        node.value = item;
        if (isEmpty()) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        n++;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.value;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T item = head.value;
        head = head.next;
        n--;
        if (isEmpty()) {
            tail = null;
        }
        return item;
    }

    @Override
    public Iterator<T> iterator() {
        return new NodeIterator<>(head);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }
}
