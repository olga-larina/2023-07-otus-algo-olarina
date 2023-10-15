package ru.otus.trie;

/**
 * https://leetcode.com/problems/implement-trie-prefix-tree/
 * https://leetcode.com/problems/implement-trie-prefix-tree/submissions/1075788591/
 * 208. Implement Trie (Prefix Tree)
 */
public class Trie {

    private static final int A = 26;
    private final Node root;

    public Trie() {
        this.root = new Node();
    }

    public void insert(String word) {
        Node node = root;
        for (char c : word.toCharArray()) {
            node = node.insert(c);
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        Node node = find(word);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        return find(prefix) != null;
    }

    private Node find(String word) {
        Node node = root;
        for (char c : word.toCharArray()) {
            if (node == null) {
                break;
            }
            node = node.get(c);
        }
        return node;
    }

    private static class Node {
        private Node[] children;
        private boolean isEnd;

        Node() {
            this.children = null;
            this.isEnd = false;
        }

        private Node insert(char c) {
            if (children == null) {
                children = new Node[A];
            }
            if (children[c - 'a'] == null) {
                children[c - 'a'] = new Node();
            }
            return children[c - 'a'];
        }

        private Node get(char c) {
            if (children == null) {
                return null;
            }
            return children[c - 'a'];
        }
    }

}
