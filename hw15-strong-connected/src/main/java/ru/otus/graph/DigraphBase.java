package ru.otus.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class DigraphBase implements Digraph {

    protected final int vertices;
    protected int edges;

    public DigraphBase(int vertices, int edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    @Override
    public int verticesCount() {
        return vertices;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(vertices).append(" vertices, ").append(edges).append(" edges\n");
        for (int v = 0; v < vertices; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj(v)) {
                s.append(String.format("%d ", w));
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
