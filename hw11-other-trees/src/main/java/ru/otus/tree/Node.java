package ru.otus.tree;

/**
 * Узел
 */
class Node<K, V> {
    protected final K key;
    protected V value;
    protected Node<K, V> left;
    protected Node<K, V> right;
    protected int size;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.size = 1;
        this.left = null;
        this.right = null;
    }
}
