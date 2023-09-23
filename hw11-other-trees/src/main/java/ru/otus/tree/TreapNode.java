package ru.otus.tree;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Узел
 */
class TreapNode<K, V> {
    protected final K key;
    protected final int prior;
    protected V value;
    protected TreapNode<K, V> left;
    protected TreapNode<K, V> right;

    public TreapNode(K key, V value) {
        this.key = key;
        this.prior = ThreadLocalRandom.current().nextInt();
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
