package ru.otus.queue;

import ru.otus.array.DynamicArray;
import ru.otus.array.FactorArray;

import java.util.NoSuchElementException;

public class PriorityQueueImpl<T> implements PriorityQueue<T> {

    private final DynamicArray<Node<T>> list = new FactorArray<>(50, 10);

    @Override
    public void enqueue(int priority, T item) {
        int s = 0;
        int e = list.size() - 1;
        Node<T> node = null;
        while (s <= e) {
            int mid = s + (e - s) / 2;
            Node<T> midNode = list.get(mid);
            if (midNode.priority == priority) {
                node = midNode;
                break;
            } else if (midNode.priority < priority) {
                s = mid + 1;
            } else {
                e = mid - 1;
            }
        }
        if (node == null) {
            node = new Node<>(priority);
            list.add(node, s);
        }
        node.array.add(item);
    }

    @Override
    public T dequeue() {
        if (list.size() == 0) {
            throw new NoSuchElementException();
        }
        T value = list.get(0).array.remove(0);
        if (list.get(0).array.size() == 0) {
            list.remove(0);
        }
        return value;
    }

    private static class Node<T> {
        final int priority;
        final DynamicArray<T> array;

        Node(int priority) {
            this.priority = priority;
            this.array = new FactorArray<>(50, 10);
        }
    }
}
