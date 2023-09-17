package ru.otus.tree;

/**
 * Узел
 */
class Node<K, V> {
    protected final K key;
    protected V value;
    protected Node<K, V> left;
    protected Node<K, V> right;
    protected int height;

    public Node(K key, V value, int height) {
        this.key = key;
        this.value = value;
        this.height = height;
        this.left = null;
        this.right = null;
    }
}
