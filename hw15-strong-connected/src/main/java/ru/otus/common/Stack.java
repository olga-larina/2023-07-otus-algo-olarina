package ru.otus.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Стек
 */
public class Stack<T> implements Iterable<T> {

    private Node<T> head;
    private int n;

    public Stack() {
        head = null;
        n = 0;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void push(T item) {
        Node<T> node = new Node<>();
        node.value = item;
        node.next = head;
        head = node;
        n++;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.value;
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T item = head.value;
        head = head.next;
        n--;
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
