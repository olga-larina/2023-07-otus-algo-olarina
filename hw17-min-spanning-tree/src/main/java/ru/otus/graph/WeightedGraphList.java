package ru.otus.graph;

import ru.otus.common.Bag;

/**
 * Взвешенный неориентированный граф на списках смежности
 */
public class WeightedGraphList extends WeightedGraphBase {

    private final Bag<Edge>[] graph;

    /**
     * @param vertices количество вершин (нумерация от 0)
     */
    @SuppressWarnings("unchecked")
    public WeightedGraphList(int vertices) {
        super(vertices, 0);
        this.graph = (Bag<Edge>[]) new Bag[vertices];
        for (int v = 0; v < vertices; v++) {
            this.graph[v] = new Bag<>();
        }
    }

    @Override
    public void addEdge(Edge edge) {
        int v = edge.either();
        int w = edge.other(v);
        graph[v].add(edge);
        graph[w].add(edge);
        edges++;
    }

    @Override
    public Iterable<Integer> vertices() {
        return VerticesIterator::new;
    }

    @Override
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<>();
        for (int v : vertices()) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                } else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) { // добавляем ребро только один раз в случае петли
                        list.add(e);
                    }
                    selfLoops++;
                }
            }
        }
        return list;
    }

    @Override
    public Iterable<Edge> adj(int v) {
        return graph[v];
    }

}
