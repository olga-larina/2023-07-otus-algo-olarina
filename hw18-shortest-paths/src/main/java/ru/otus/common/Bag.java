package ru.otus.common;

import java.util.Iterator;

/**
 * Аналог стека, но без удаления
 */
public class Bag<T> implements Iterable<T> {

    private Node<T> head;
    private int n;

    public Bag() {
        head = null;
        n = 0;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void add(T item) {
        Node<T> node = new Node<>();
        node.value = item;
        node.next = head;
        head = node;
        n++;
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
