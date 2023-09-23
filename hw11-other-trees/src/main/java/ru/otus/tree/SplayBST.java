package ru.otus.tree;

/**
 * Расширяющееся дерево
 * Источники:
 *  - https://www.geeksforgeeks.org/introduction-to-splay-tree-data-structure/
 *  - https://www.geeksforgeeks.org/insertion-in-splay-tree/
 *  - https://www.geeksforgeeks.org/searching-in-splay-tree/
 *  - https://www.geeksforgeeks.org/deletion-in-splay-tree/
 *  - https://algs4.cs.princeton.edu/33balanced/SplayBST.java.html
 */
public class SplayBST<K extends Comparable<K>, V> implements Tree<K, V> {

    private Node<K, V> root; // корень дерева

    /**
     * Получение элемента.
     * При поиске элемента происходит расширение дерева.
     */
    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        root = splay(root, key);
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return root.value;
        }
        return null;
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
        // дерево пустое
        if (root == null) {
            root = new Node<>(key, value);
            return;
        }

        // перемещаем ближайший листовой узел в корень
        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        if (cmp == 0) { // ключ уже есть в дереве => заменяем значение
            root.value = value;
        } else if (cmp < 0) { // ключ должен быть в левом поддереве
            Node<K, V> n = new Node<>(key, value);
            n.left = root.left;
            n.right = root;
            root.left = null;
            root = n;

            // в root.left ничего не меняется
            // обновляем размер бывшего root (теперь это n.right или root.right), т.к. у него забрали левое поддерево
            updateSize(root.right);
            // обновляем размер нового root
            updateSize(root);
        } else { // ключ должен быть в правом поддереве
            Node<K, V> n = new Node<>(key, value);
            n.right = root.right;
            n.left = root;
            root.right = null;
            root = n;

            // в root.right ничего не меняется
            // обновляем размер бывшего root (теперь это n.left или root.left), т.к. у него забрали правое поддерево
            updateSize(root.left);
            // обновляем размер нового root
            updateSize(root);
        }

    }

    /**
     * Удаление из дерева
     */
    @Override
    public void remove(K key) {
        if (root == null) {
            return;
        }

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        if (cmp != 0) { // ключа нет в дереве
            return;
        }

        if (root.left == null) { // нет левого поддерева => просто делаем корнем верхний узел правого
            root = root.right;
            // размеры не меняются
        } else {
            // key == root.key => поэтому после splay(root.left, key) не останется правого поддерева
            Node<K, V> x = root.right;
            root = splay(root.left, key);
            // правый сын предыдущего корня становится правым сыном нового корня
            root.right = x;
            // размер x не меняется
            // обновляем размер root
            updateSize(root);
        }
    }

    /**
     * Splay (расширение)
     * Если ключ есть в дереве, то он помещается в корень.
     * Иначе в корень помещается элемент, к которому обратились позже всех при поиске ключа
     */
    private Node<K, V> splay(Node<K, V> h, K key) {
        if (h == null) {
            return null;
        }

        int cmp1 = key.compareTo(h.key);

        if (cmp1 < 0) { // ключ в левом поддереве
            // ключа нет в дереве => завершаем обработку
            if (h.left == null) {
                return h;
            }

            int cmp2 = key.compareTo(h.left.key);

            if (cmp2 < 0) { // Zig-Zig (Left Left)
                // рекурсивно перемещаем key в корень left-left дерева
                h.left.left = splay(h.left.left, key);

                // правый поворот
                h = rotateRight(h);
            } else if (cmp2 > 0) { // Zig-Zag (Left Right)
                // рекурсивно перемещаем key в корень left-right дерева
                h.left.right = splay(h.left.right, key);

                // левый поворот
                if (h.left.right != null) {
                    h.left = rotateLeft(h.left);
                }
            }

            // второй поворот
            return (h.left == null) ? h : rotateRight(h);
        } else if (cmp1 > 0) { // ключ в правом поддереве
            // ключа нет в дереве => завершаем обработку
            if (h.right == null) {
                return h;
            }

            int cmp2 = key.compareTo(h.right.key);

            if (cmp2 < 0) { // Zig-Zag (Right Left)
                // рекурсивно перемещаем key в корень right-left дерева
                h.right.left  = splay(h.right.left, key);

                // правый поворот
                if (h.right.left != null) {
                    h.right = rotateRight(h.right);
                }
            } else if (cmp2 > 0) { // Zag-Zag (Right Right)
                // рекурсивно перемещаем key в корень right-right дерева
                h.right.right = splay(h.right.right, key);

                // левый поворот
                h = rotateLeft(h);
            }

            // второй поворот
            return (h.right == null) ? h : rotateLeft(h);
        } else { // ключ присутствует в дереве
            return h;
        }
    }

    /**
     * Вспомогательные функции
     */

    public int size() {
        return size(root);
    }

    private int size(Node<K, V> x) {
        if (x == null) {
            return 0;
        }
        else {
            return 1 + size(x.left) + size(x.right);
        }
    }

    private void updateSize(Node<K, V> x) {
        if (x != null) {
            x.size = size(x);
        }
    }

    /**
     * правый поворот
     */
    private Node<K, V> rotateRight(Node<K, V> h) {
        Node<K, V> x = h.left;
        if (x == null) {
            return h;
        }
        h.left = x.right;
        x.right = h;

        x.size = h.size;
        updateSize(h);
        return x;
    }

    /**
     * левый поворот
     */
    private Node<K, V> rotateLeft(Node<K, V> h) {
        Node<K, V> x = h.right;
        if (x == null) {
            return h;
        }
        h.right = x.left;
        x.left = h;

        x.size = h.size;
        updateSize(h);
        return x;
    }

}
