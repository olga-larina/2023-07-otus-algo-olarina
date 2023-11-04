package ru.otus.common;

/**
 * Система непересекающихся множеств (union find, disjoint-set)
 */
public class UnionFind {

    private final int[] parent; // parent[i] - родительский элемент для i
    private final int[] size; // size[i] - количество элементов, у которых i - это родительский элемент

    public UnionFind(int n) {
        this.parent = new int[n];
        this.size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 0;
        }
    }

    /**
     * Найти родительский элемент
     */
    public int find(int p) {
        validate(p);
        while (p != parent[p]) { // пока p - это не родительский
            parent[p] = parent[parent[p]]; // уменьшаем путь в 2 раза, чтобы цепочка была короче; т.е. сразу переходим к дедушке, а не родителю
            p = parent[p];
        }
        return p;
    }

    /**
     * Объединить множества
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP == rootQ) {
            return;
        }

        // маленькое множество прикрепляем к большому (для сглаживания)
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }

    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }
}
