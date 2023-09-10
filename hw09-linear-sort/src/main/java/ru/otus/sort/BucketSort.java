package ru.otus.sort;

/**
 * Блочная (вёдерная) сортировка.
 * Реализована только для положительных чисел
 */
public class BucketSort extends SortIntAlgo {

    public BucketSort(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() {
        if (n == 0 || n == 1) {
            return;
        }

        // находим максимум
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (more(arr[i], max)) {
                max = arr[i];
            }
            if (arr[i] < 0) {
                throw new UnsupportedOperationException();
            }
        }

        // заполняем массив блоков
        Node[] blocks = new Node[n];
        Node node;
        Node prev;
        for (int i : arr) {
            int idx = (int) ((long) i * (long) n / ((long) max + 1));
            Node newNode = new Node(i, null);
            node = blocks[idx];
            prev = null;
            while (node != null && lessEq(node.val, i)) {
                prev = node;
                node = node.next;
            }
            newNode.next = node;
            if (prev == null) { // корневой элемент
                blocks[idx] = newNode;
            } else {
                prev.next = newNode;
            }
        }

        // переписываем в начальный массив
        int j = 0;
        for (Node block : blocks) {
            node = block;
            while (node != null) {
                arr[j++] = node.val;
                node = node.next;
            }
        }
    }

    private static class Node {
        private final int val;
        private Node next;

        Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

}
