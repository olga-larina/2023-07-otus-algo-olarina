package ru.otus.graph.mst;

import ru.otus.common.IndexMinHeap;
import ru.otus.common.Queue;
import ru.otus.graph.Edge;
import ru.otus.graph.WeightedGraph;
import ru.otus.graph.WeightedGraphList;

/**
 * Алгоритм Прима для поиска минимального остовного дерева
 * Более быстрая версия, на основе подсчёта расстояний до каждой вершины
 */
public class PrimDistances {

    private int mstWeight; // вес минимального скелета
    private final Queue<Edge> mst; // минимальный скелет
    private final Edge[] edgeTo; // edgeTo[v] - минимальное ребро на текущий момент до вершины v
    private final int[] distTo; // distTo[v] - минимальный путь на текущий момент до вершины v
    private final boolean[] visited; // флаг посещения вершин
    private final IndexMinHeap<Integer> pq; // приоритетная очередь на минимум; индекс - номер вершины, значение (ключ) - текущий вес

    public PrimDistances(WeightedGraph graph) {
        this.mstWeight = 0;
        this.mst = new Queue<>();
        this.edgeTo = new Edge[graph.verticesCount()];
        this.distTo = new int[graph.verticesCount()];
        for (int v : graph.vertices()) {
            distTo[v] = Integer.MAX_VALUE; // сначала все расстояния равны максимальному значению
        }
        this.visited = new boolean[graph.verticesCount()];
        this.pq = new IndexMinHeap<>(graph.verticesCount());
        // поиск минимального скелета
        for (int v : graph.vertices()) {
            if (!visited[v]) {
                prim(graph, v);
            }
        }
        // восстановление минимального скелета
        for (int v : graph.vertices()) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        // подсчёт веса минимального скелета
        for (Edge edge : mst) {
            mstWeight += edge.getWeight();
        }
    }

    private void prim(WeightedGraph graph, int s) {
        distTo[s] = 0; // начинаем из вершины s, значит, расстояние до неё равно 0
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin(); // вершина с минимальным весом
            scan(graph, v);
        }
    }

    /**
     * Проверяем рёбра, инцидентные вершине v
     * Обновляем расстояния до вершин и информацию в приоритетной очереди
     */
    private void scan(WeightedGraph graph, int v) {
        if (visited[v]) {
            return;
        }
        visited[v] = true;
        for (Edge edge : graph.adj(v)) {
            int w = edge.other(v);
            if (visited[w]) {
                continue;
            }
            // если рассматриваемое ребро может уменьшить путь до вершины w, то добавляем его в минимальную очередь
            if (edge.getWeight() < distTo[w]) {
                distTo[w] = edge.getWeight();
                edgeTo[w] = edge;
                if (pq.contains(w)) {
                    pq.decreaseKey(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }

    /**
     * Возможный минимальный скелет - [1-4: 1] [1-2: 2] [2-3: 3] [0-4: 3] [0-5: 3] [4-6: 2] [4-7: 2], вес - 16
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

        PrimDistances primEdges = new PrimDistances(graph);
        System.out.println("Алгоритм Прима, Минимальный скелет: " + primEdges.mst + ", вес: " + primEdges.mstWeight);
        check(expectedWeight, primEdges.mstWeight);

    }

    private static void check(int expected, int actual) {
        if (expected != actual) {
            throw new IllegalStateException("Ошибка!");
        }
    }
}
