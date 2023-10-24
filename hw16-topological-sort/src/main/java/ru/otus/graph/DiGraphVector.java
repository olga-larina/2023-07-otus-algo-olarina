package ru.otus.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Ориентированный граф на векторах смежности
 */
public class DiGraphVector extends DigraphBase {

    private final Integer[][] graph;

    /**
     * @param vertices количество вершин
     * @param maxDegree максимальная полустепень исхода вершины
     */
    public DiGraphVector(int vertices, int maxDegree) {
        super(vertices, 0);
        this.graph = new Integer[vertices][maxDegree];
        for (int v = 0; v < graph.length; v++) {
            graph[v] = new Integer[maxDegree];
        }
    }

    public DiGraphVector(Integer[][] graph) {
        this(graph.length, graph[0].length);
        for (int v = 0; v < graph.length; v++) {
            for (Integer w : graph[v]) {
                if (w == null) {
                    break;
                }
                addEdge(v, w);
            }
        }
    }

    @Override
    public void addEdge(int v, int w) {
        Integer[] adj = graph[v];
        for (int i = 0; i < adj.length; i++) {
            if (adj[i] == null) {
                adj[i] = w;
                edges++;
                break;
            }
        }
    }

    @Override
    public Iterable<Integer> vertices() {
        return VerticesIterator::new;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        return () -> new AdjIterator(v);
    }

    @Override
    public Digraph reverse() {
        // при перевороте графа максимальная полустепень исхода вершины может измениться => сначала считаем её
        int[] maxDegrees = new int[vertices];
        int maxDegree = 0;
        for (int v : vertices()) {
            for (int w : adj(v)) {
                maxDegree = Math.max(maxDegree, ++maxDegrees[w]);
            }
        }

        Digraph reversed = new DiGraphVector(vertices, maxDegree);
        for (int v : vertices()) {
            for (int w : adj(v)) {
                reversed.addEdge(w, v);
            }
        }
        return reversed;
    }

    private class AdjIterator implements Iterator<Integer> {

        private final int v;
        private int curIndex;

        AdjIterator(int v) {
            this.v = v;
            this.curIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return curIndex < graph[v].length && graph[v][curIndex] != null;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return graph[v][curIndex++];
        }
    }

}
