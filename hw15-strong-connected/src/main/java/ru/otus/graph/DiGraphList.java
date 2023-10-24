package ru.otus.graph;

import ru.otus.common.Bag;

/**
 * Ориентированный граф на списках смежности
 */
public class DiGraphList extends DigraphBase {

    private final Bag<Integer>[] graph;

    /**
     * @param vertices количество вершин (нумерация от 0)
     */
    @SuppressWarnings("unchecked")
    public DiGraphList(int vertices) {
        super(vertices, 0);
        this.graph = (Bag<Integer>[]) new Bag[vertices];
        for (int v = 0; v < vertices; v++) {
            this.graph[v] = new Bag<>();
        }
    }

    /**
     * Построение из графа на основе векторов смежности
     */
    public DiGraphList(Integer[][] vectorGraph) {
        this(vectorGraph.length);
        for (int v = 0; v < vectorGraph.length; v++) {
            for (Integer w : vectorGraph[v]) {
                if (w == null) {
                    continue;
                }
                addEdge(v, w);
            }
        }
    }

    @Override
    public void addEdge(int v, int w) {
        graph[v].add(w);
        edges++;
    }

    @Override
    public Iterable<Integer> vertices() {
        return VerticesIterator::new;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        return graph[v];
    }

    @Override
    public Digraph reverse() {
        Digraph reversed = new DiGraphList(vertices);
        for (int v : vertices()) {
            for (int w : adj(v)) {
                reversed.addEdge(w, v);
            }
        }
        return reversed;
    }

}
