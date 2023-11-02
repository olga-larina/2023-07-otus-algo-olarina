package ru.otus.graph.mst;

import ru.otus.common.MinHeap;
import ru.otus.common.Queue;
import ru.otus.graph.Edge;
import ru.otus.graph.WeightedGraph;
import ru.otus.graph.WeightedGraphList;

/**
 * Алгоритм Прима для поиска минимального остовного дерева
 * Более медленная версия, с проверкой всех рёбер
 */
public class PrimEdges {

    private int mstWeight; // вес минимального скелета
    private final Queue<Edge> mst; // минимальный скелет
    private final boolean[] visited; // флаг посещения вершин
    private final MinHeap<Edge> pq; // приоритетная очередь на минимум (для рёбер)

    public PrimEdges(WeightedGraph graph) {
        this.mstWeight = 0;
        this.mst = new Queue<>();
        this.visited = new boolean[graph.verticesCount()];
        this.pq = new MinHeap<>(graph.edgesCount());
        for (int v : graph.vertices()) {
            if (!visited[v]) {
                prim(graph, v);
            }
        }
    }

    private void prim(WeightedGraph graph, int v) {
        // добавляем в очередь инцидентные v рёбра
        scan(graph, v);
        while (!pq.isEmpty()) {
            Edge edge = pq.delMin();
            int v1 = edge.either();
            int v2 = edge.other(v1);
            // если уже посетили обе вершины, то пропускаем, т.к. иначе будет цикл в минимальном скелете
            if (visited[v1] && visited[v2]) {
                continue;
            }
            // добавляем ребро с минимальным весом в минимальный скелет
            mst.enqueue(edge);
            mstWeight += edge.getWeight();
            // добавляем в очередь новые рёбра
            if (!visited[v1]) {
                scan(graph, v1);
            }
            if (!visited[v2]) {
                scan(graph, v2);
            }
        }
    }

    /**
     * Добавляем в очередь новые рёбра, инцидентные вершине v
     */
    private void scan(WeightedGraph graph, int v) {
        if (visited[v]) {
            return;
        }
        visited[v] = true;
        for (Edge edge : graph.adj(v)) {
            if (!visited[edge.other(v)]) {
                pq.insert(edge);
            }
        }
    }

    /**
     * Возможный минимальный скелет - [0-5: 3] [0-4: 3] [1-4: 1] [4-7: 2] [4-6: 2] [1-2: 2] [2-3: 3], вес - 16
     */
    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraphList(8);
        graph.addEdge(new Edge(0, 1, 4));
        graph.addEdge(new Edge(0, 4, 3));
        graph.addEdge(new Edge(0, 5, 3));
        graph.addEdge(new Edge(1, 2, 2));
        graph.addEdge(new Edge(1, 4, 1));
        graph.addEdge(new Edge(1, 7, 3));
        graph.addEdge(new Edge(2, 3, 3));
        graph.addEdge(new Edge(2, 6, 2));
        graph.addEdge(new Edge(3, 4, 4));
        graph.addEdge(new Edge(4, 5, 4));
        graph.addEdge(new Edge(4, 6, 2));
        graph.addEdge(new Edge(4, 7, 2));
        graph.addEdge(new Edge(5, 6, 3));
        int expectedWeight = 16;

        PrimEdges primEdges = new PrimEdges(graph);
        System.out.println("Алгоритм Прима, Минимальный скелет: " + primEdges.mst + ", вес: " + primEdges.mstWeight);
        check(expectedWeight, primEdges.mstWeight);

    }

    private static void check(int expected, int actual) {
        if (expected != actual) {
            throw new IllegalStateException("Ошибка!");
        }
    }
}
