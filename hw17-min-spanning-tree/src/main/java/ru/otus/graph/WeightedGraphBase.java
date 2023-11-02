package ru.otus.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class WeightedGraphBase implements WeightedGraph {

    protected final int vertices;
    protected int edges;

    public WeightedGraphBase(int vertices, int edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    @Override
    public int verticesCount() {
        return vertices;
    }

    @Override
    public int edgesCount() {
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(vertices).append(" vertices, ").append(edges).append(" edges\n");
        for (int v = 0; v < vertices; v++) {
            s.append(String.format("%d: ", v));
            for (Edge e : adj(v)) {
                s.append(e).append(' ');
            }
            s.append("\n");
        }
        return s.toString();
    }

    class VerticesIterator implements Iterator<Integer> {
        private int curVertex = 0;

        @Override
        public boolean hasNext() {
            return curVertex < vertices;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return curVertex++;
        }
    }
}
