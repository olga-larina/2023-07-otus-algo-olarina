package ru.otus.tree;

/**
 * Декартово дерево
 * Источники:
 *  - https://habr.com/ru/articles/101818/
 *  - https://alexdremov.me/treap-algorithm-explained/
 */
public class Treap<K extends Comparable<K>, V> implements Tree<K, V> {

    private TreapNode<K, V> root; // корень дерева

    /**
     * Получение элемента.
     */
    @Override
    public V get(K key) {
        TreapNode<K, V> result = get(root, key);
        return (result == null) ? null : result.value;
    }

    /**
     * Поиск элемента
     */
    @Override
    public boolean search(K key) {
        return get(key) != null;
    }

    /**
     * Вставка в дерево
     */
    @Override
    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    private TreapNode<K, V> insert(TreapNode<K, V> head, K key, V value) {
        if (head == null) {
            return new TreapNode<>(key, value);
        }
        TreapNode<K, V>[] treaps = split(head, key);
        // проверяем, существует ли уже такой ключ (ищем в правом поддереве, т.к. там элементы >= key)
        TreapNode<K, V> existingNode = get(treaps[1], key);
        if (existingNode != null) {
            existingNode.value = value;
            return merge(treaps[0], treaps[1]);
        }
        TreapNode<K, V> newNode = new TreapNode<>(key, value);
        return merge(treaps[0], merge(newNode, treaps[1]));
    }

    /**
     * Удаление из дерева
     */
    @Override
    public void remove(K key) {
        root = remove(root, key);
    }

    private TreapNode<K, V> remove(TreapNode<K, V> head, K key) {
        if (head == null) {
            return null;
        }
        TreapNode<K, V>[] treaps = split(head, key);
        if (treaps[1] != null) { // есть элементы >= key
            // снова разделяем, слева элементы == key, справа - > key
            TreapNode<K, V>[] rightTreaps = split(treaps[1], key, true);
            if (rightTreaps[0] == null) { // ключа нет, сливаем обратно
                return merge(treaps[0], rightTreaps[1]);
            }

            // удаляем ключ (он единственный элемент в rightTreaps[0]), сливаем обратно
            return merge(treaps[0], rightTreaps[1]);
        }
        // ключа нет, сливаем обратно
        return merge(treaps[0], treaps[1]);
    }

    /**
     * Поиск
     */
    private TreapNode<K, V> get(TreapNode<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node;
        }
    }

    @SuppressWarnings("unchecked")
    private TreapNode<K, V>[] split(TreapNode<K, V> node, K key) {
        return split(node, key, false);
    }

    /**
     * Разбиение дерева по ключу key.
     * В левом поддереве - элементы меньше key, в правом - больше key.
     * В зависимости от equalOnTheLeft равные элементы помещаются в левое (==true) или правое (==false) поддерево.
     */
    @SuppressWarnings("unchecked")
    private TreapNode<K, V>[] split(TreapNode<K, V> node, K key, boolean equalOnTheLeft) {
        TreapNode<K, V>[] res = (TreapNode<K, V>[]) new TreapNode[2];
        // при equalOnTheLeft == false:
        // res[0]: < key
        // res[1]: >= key

        if (node == null) {
            return res;
        }

        int cmp = key.compareTo(node.key);
        if (cmp > 0 || (equalOnTheLeft && cmp == 0)) {
            TreapNode<K, V>[] t = split(node.right, key, equalOnTheLeft);
            node.right = t[0]; // в t[0] узлы правого поддерева, которые меньше key
            res[0] = node;
            res[1] = t[1];
        } else {
            TreapNode<K, V>[] t = split(node.left, key, equalOnTheLeft);
            node.left = t[1]; // в t[1] узлы левого поддерева, которые больше или равны key
            res[0] = t[0];
            res[1] = node;
        }

        return res;
    }

    /**
     * Слияние
     * @param left левое поддерево
     * @param right правое поддерево
     * @return объединённое дерево
     */
    private TreapNode<K, V> merge(TreapNode<K, V> left, TreapNode<K, V> right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        if (left.prior > right.prior) {
            left.right = merge(left.right, right);
            return left;
        } else {
            right.left = merge(left, right.left);
            return right;
        }
    }

}
