package ru.otus.graph;

import ru.otus.common.Bag;

/**
 * Взвешенный ориентированный граф на списках смежности
 */
public class WeightedDigraphList extends WeightedDigraphBase {

    private final Bag<DirectedEdge>[] graph;

    /**
     * @param vertices количество вершин (нумерация от 0)
     */
    @SuppressWarnings("unchecked")
    public WeightedDigraphList(int vertices) {
        super(vertices, 0);
        this.graph = (Bag<DirectedEdge>[]) new Bag[vertices];
        for (int v = 0; v < vertices; v++) {
            this.graph[v] = new Bag<>();
        }
    }

    @Override
    public void addEdge(DirectedEdge edge) {
        graph[edge.getFrom()].add(edge);
        edges++;
    }

    @Override
    public Iterable<Integer> vertices() {
        return VerticesIterator::new;
    }

    @Override
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> list = new Bag<>();
        for (int v : vertices()) {
            for (DirectedEdge e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    }

    @Override
    public Iterable<DirectedEdge> adj(int v) {
        return graph[v];
    }

}
