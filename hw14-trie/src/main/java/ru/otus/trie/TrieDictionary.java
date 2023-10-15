package ru.otus.trie;

/**
 * Ассоциативный массив на основе префиксного дерева
 * Значения null невозможны
 */
public class TrieDictionary<V> implements Dictionary<String, V> {

    private static final int A = 26;
    private final Node<V> root;
    private int size;

    public TrieDictionary() {
        this.root = new Node<>();
        this.size = 0;
    }

    @Override
    public V put(String key, V value) {
        Node<V> node = root;
        for (char c : key.toCharArray()) {
            node = node.insert(c);
        }
        V prev = node.value;
        node.value = value;
        if (prev == null) {
            size++;
        }
        return prev;
    }

    @Override
    public boolean contains(String key) {
        Node<V> node = find(key);
        return node != null && node.value != null;
    }

    @Override
    public V get(String key) {
        Node<V> node = find(key);
        return (node == null) ? null : node.value;
    }

    @Override
    public V remove(String key) {
        return remove(root, key, 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<V> find(String word) {
        Node<V> node = root;
        for (char c : word.toCharArray()) {
            if (node == null) {
                break;
            }
            node = node.get(c);
        }
        return node;
    }

    private V remove(Node<V> node, String word, int depth) {
        if (node == null) {
            return null;
        }
        if (depth == word.length()) {
            V prevValue = node.value;
            node.value = null;
            if (prevValue != null) {
                size--;
            }
            return prevValue;
        }
        V removed = remove(node.get(word.charAt(depth)), word, depth + 1);
        if (removed == null) {
            return null;
        }
        node.usages--;
        if (node.usages == 0) {
            node.children = null;
        }
        return removed;
    }

    private static class Node<V> {
        private Node<V>[] children;
        private V value;
        private int usages;

        Node() {
            this.children = null;
            this.value = null;
            this.usages = 0;
        }

        @SuppressWarnings({"unchecked"})
        private Node<V> insert(char c) {
            this.usages++;
            if (children == null) {
                children = (Node<V>[]) (new Node[A]);
            }
            Node<V> node = children[c - 'a'];
            if (node == null) {
                node = new Node<>();
                children[c - 'a'] = node;
            }
            node.usages++;
            return node;
        }

        private Node<V> get(char c) {
            if (children == null) {
                return null;
            }
            return children[c - 'a'];
        }
    }
}
