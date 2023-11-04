package ru.otus.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

class NodeIterator<T> implements Iterator<T> {

    private Node<T> cur;

    NodeIterator(Node<T> head) {
        cur = head;
    }

    @Override
    public boolean hasNext() {
        return cur != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T item = cur.value;
        cur = cur.next;
        return item;
    }
}
