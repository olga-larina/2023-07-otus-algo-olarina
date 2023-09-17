package ru.otus.tree;

/**
 * АВЛ-дерево
 */
public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    @Override
    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Items should not be null");
        }
        this.root = insert(root, key, value);
    }

    @Override
    public boolean search(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Items should not be null");
        }
        return search(root, key) != null;
    }

    @Override
    public void remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Items should not be null");
        }
        this.root = remove(root, key);
    }

    @Override
    protected Node<K, V> rebalance(Node<K, V> node) {
        int balance = balance(node);

        // balance > 1 => высота левого поддерева больше высоты правого
        // balance(node.left) >= 0 - высота левого поддерева node >= высоте правого поддерева

        if (balance > 1 && balance(node.left) >= 0) {
            return smallRightRotate(node);
        }
        if (balance > 1 && balance(node.left) < 0) {
            return bigRightRotate(node);
        }
        if (balance < -1 && balance(node.right) <= 0) {
            return smallLeftRotate(node);
        }
        if (balance < -1 && balance(node.right) > 0) {
            return bigLeftRotate(node);
        }

        return node;
    }

    private int balance(Node<K, V> node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    //     x                   y
    // t1      y      ->    x     t3
    //      t2   t3      t1   t2
    private Node<K, V> smallLeftRotate(Node<K, V> x) {
        Node<K, V> y = x.right;
        Node<K, V> t2 = y.left;

        y.left = x;
        x.right = t2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    //        y               x
    //    x     t3    ->  t1     y
    // t1   t2                t2   t3
    private Node<K, V> smallRightRotate(Node<K, V> y) {
        Node<K, V> x = y.left;
        Node<K, V> t2 = x.right;

        x.right = y;
        y.left = t2;

        updateHeight(x);
        updateHeight(y);

        return x;
    }

    private Node<K, V> bigLeftRotate(Node<K, V> node) {
        node.right = smallRightRotate(node.right);
        return smallLeftRotate(node);
    }

    private Node<K, V> bigRightRotate(Node<K, V> node) {
        node.left = smallLeftRotate(node.left);
        return smallRightRotate(node);
    }

}
