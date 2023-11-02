package ru.otus.graph.mst;

import ru.otus.common.Queue;
import ru.otus.common.UnionFind;
import ru.otus.graph.Edge;
import ru.otus.graph.WeightedGraph;
import ru.otus.graph.WeightedGraphList;

import java.util.Arrays;

/**
 * Алгоритм Борувки для поиска минимального остовного дерева
 */
public class Boruvka {

    private int mstWeight; // вес минимального скелета
    private final Queue<Edge> mst; // минимальный скелет

    public Boruvka(WeightedGraph graph) {
        this.mstWeight = 0;
        this.mst = new Queue<>();
        boruvka(graph);
    }

    private void boruvka(WeightedGraph graph) {
        // используем систему непересекающихся множеств
        UnionFind uf = new UnionFind(graph.verticesCount());

        Edge[] closest = new Edge[graph.verticesCount()];

        // на каждой итерации число множеств уменьшается минимум в 2 раза, поэтому будет не более log(verticesCount) итераций
        // проходим log(verticesCount) итераций или пока в минимальном скелете не будет достаточное количество рёбер (verticesCount - 1)
        for (int t = 1; t < graph.verticesCount() && mst.size() < graph.verticesCount() - 1; t = t + t) {
            Arrays.fill(closest, null);

            // проходим по всем рёбрам, для каждого непересекающегося множества находим минимальное ребро
            // если вес рёбер одинаков, то обязательно каждый раз выбирать ребро однозначно, иначе можем получить цикл
            // в данном случае выбирается первое возвращаемое в graph.edges() (там они возвращаются всегда в одинаковом порядке)
            for (Edge edge : graph.edges()) {
                int v = edge.either();
                int w = edge.other(v);
                int rootV = uf.find(v);
                int rootW = uf.find(w);
                if (rootV == rootW) {
                    continue; // уже объединены
                }
                // если минимальное ребро, инцидентное вершине, ещё не найдено, то сохраняем
                if (closest[rootV] == null || less(edge, closest[rootV])) {
                    closest[rootV] = edge;
                }
                if (closest[rootW] == null || less(edge, closest[rootW])) {
                    closest[rootW] = edge;
                }
            }

            // добавляем новые рёбра в минимальный скелет
            for (Edge edge : closest) {
                if (edge != null) {
                    int v = edge.either();
                    int w = edge.other(v);
                    // проверяем, что не было добавлено
                    if (uf.find(v) != uf.find(w)) {
                        mst.enqueue(edge);
                        mstWeight += edge.getWeight();
                        uf.union(v, w);
                    }
                }
            }
        }
    }

    // вес ребра e меньше веса ребра f
    private static boolean less(Edge e, Edge f) {
        return e.compareTo(f) < 0;
    }

    /**
     * Возможный минимальный скелет - [0-4: 3] [1-4: 1] [2-6: 2] [2-3: 3] [5-6: 3] [4-6: 2] [4-7: 2], вес - 16
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

        Boruvka boruvkaEdges = new Boruvka(graph);
        System.out.println("Алгоритм Борувки, Минимальный скелет: " + boruvkaEdges.mst + ", вес: " + boruvkaEdges.mstWeight);
        check(expectedWeight, boruvkaEdges.mstWeight);
    }

    private static void check(int expected, int actual) {
        if (expected != actual) {
            throw new IllegalStateException("Ошибка!");
        }
    }
}
