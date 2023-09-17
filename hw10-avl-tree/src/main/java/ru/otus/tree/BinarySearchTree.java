package ru.otus.tree;

/**
 * Двоичное дерево поиска
 */
public class BinarySearchTree<K extends Comparable<K>, V> implements Tree<K, V> {

    protected Node<K, V> root;

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

    protected int height(Node<K, V> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    protected void updateHeight(Node<K, V> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    protected Node<K, V> rebalance(Node<K, V> node) {
        return node;
    }

    protected Node<K, V> insert(Node<K, V> node, K key, V value) {

        // Обычная вставка в BST
        if (node == null) {
            return new Node<>(key, value, 1);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, key, value);
        } else {
            node.value = value;
        }

        // Обновление высоты
        updateHeight(node);

        // Балансировка
        return rebalance(node);
    }

    protected Node<K, V> search(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return search(node.left, key);
        } else if (cmp > 0) {
            return search(node.right, key);
        } else {
            return node;
        }
    }

    // Hibbard deletion
    protected Node<K, V> remove(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.right == null) { // нет правого потомка
                return node.left;
            }
            if (node.left == null) { // нет левого потомка
                return node.right;
            }

            Node<K, V> tmp = node;
            node = min(tmp.right); // ищем минимальный элемент в правом поддереве (мин.элемент > текущего); заменяем текущий элемент на него
            node.right = deleteMin(tmp.right); // удаляем этот минимальный элемент (у него max 1 потомок, который запишется в потомки родителя); в правый дочерний записываем родителя
            node.left = tmp.left; // левый дочерний элемент не меняется
        }
        updateHeight(node);
        return rebalance(node);
    }

    protected Node<K, V> deleteMin(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        updateHeight(node);
        return rebalance(node);
    }

    protected Node<K, V> min(Node<K, V> node) {
        if (node == null || node.left == null) {
            return node;
        }
        return min(node.left);
    }
}
