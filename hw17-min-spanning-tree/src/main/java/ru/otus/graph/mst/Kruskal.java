package ru.otus.graph.mst;

import ru.otus.common.Queue;
import ru.otus.common.UnionFind;
import ru.otus.graph.Edge;
import ru.otus.graph.WeightedGraph;
import ru.otus.graph.WeightedGraphList;

import java.util.Arrays;

/**
 * Алгоритм Краскала для поиска минимального остовного дерева
 */
public class Kruskal {

    private int mstWeight; // вес минимального скелета
    private final Queue<Edge> mst; // минимальный скелет
    private final Edge[] edges; // рёбра

    public Kruskal(WeightedGraph graph) {
        this.mstWeight = 0;
        this.mst = new Queue<>();
        this.edges = new Edge[graph.edgesCount()];
        int i = 0;
        for (Edge edge : graph.edges()) {
            edges[i++] = edge;
        }
        kruskal(graph);
    }

    private void kruskal(WeightedGraph graph) {
        // сортируем рёбра по весу
        Arrays.sort(edges);

        // используем систему непересекающихся множеств
        // изначально количество множеств равно количеству вершин; в итоге должно стать равным одному
        UnionFind uf = new UnionFind(graph.verticesCount());
        // проходим по всем рёбрам, начиная с ребра с наименьшим весом, пытаемся уменьшить число множеств
        // размер минимального скелета - число вершин минус 1
        for (int i = 0; i < graph.edgesCount() && mst.size() < graph.verticesCount() - 1; i++) {
            Edge edge = edges[i];
            int v = edge.either();
            int w = edge.other(v);

            // если вершины в разных множествах, т.е. при добавлении ребра не будет цикла, то добавляем его в минимальный скелет
            // и объединяем множества
            if (uf.find(v) != uf.find(w)) {
                uf.union(v, w);
                mst.enqueue(edge);
                mstWeight += edge.getWeight();
            }
        }
    }

    /**
     * Возможный минимальный скелет - [1-4: 1] [4-6: 2] [4-7: 2] [2-6: 2] [5-6: 3] [2-3: 3] [0-4: 3], вес - 16
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

        Kruskal kruskalEdges = new Kruskal(graph);
        System.out.println("Алгоритм Краскала, Минимальный скелет: " + kruskalEdges.mst + ", вес: " + kruskalEdges.mstWeight);
        check(expectedWeight, kruskalEdges.mstWeight);
    }

    private static void check(int expected, int actual) {
        if (expected != actual) {
            throw new IllegalStateException("Ошибка!");
        }
    }
}
