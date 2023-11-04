package ru.otus.graph.path;

import ru.otus.common.IndexMinHeap;
import ru.otus.common.Stack;
import ru.otus.graph.DirectedEdge;
import ru.otus.graph.WeightedDigraph;
import ru.otus.graph.WeightedDigraphList;

import java.util.Arrays;

/**
 * Алгоритм Дейкстры для поиска кратчайшего пути из одной вершины во все остальные
 * Работает только с неотрицательными весами
 */
public class Dijkstra {

    private final int src; // начальная вершина, из которой ищем пути
    private final int[] distTo; // distTo[w] - кратчайшее расстояние из вершины src в вершину w
    private final DirectedEdge[] edgeTo; // edgeTo[w] - последнее ребро на кратчайшем пути из вершины src в вершину w
    private final IndexMinHeap<Integer> pq; // приоритетная очередь на минимум; индекс - номер вершины, значение (ключ) - текущий вес

    public Dijkstra(WeightedDigraph graph, int src) {
        this.src = src;
        this.distTo = new int[graph.verticesCount()];
        this.edgeTo = new DirectedEdge[graph.verticesCount()];
        this.pq = new IndexMinHeap<>(graph.verticesCount());
        dijkstra(graph, src);
    }

    private void dijkstra(WeightedDigraph graph, int src) {
        // проверяем рёбра на наличие отрицательного веса
        for (DirectedEdge edge : graph.edges()) {
            if (edge.getWeight() < 0) {
                throw new IllegalStateException("Отрицательный вес у ребра " + edge);
            }
        }

        // все расстояния инициализируем максимальным значением
        Arrays.fill(distTo, Integer.MAX_VALUE);
        // расстояние из исходной вершины в саму себя равно 0
        distTo[src] = 0;

        // пробуем улучшать расстояния между вершинами, каждый раз удаляем из приоритетной очереди минимальное значение
        // берём первую вершину, добавляем в очередь её соседей с весом;
        // больше первую вершину не рассматриваем, т.к. в неё не сможем попасть с меньшим весом (нет отрицательных весов)
        // затем рассматриваем следующую вершину с минимальным весом и т.п.
        pq.insert(src, distTo[src]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge edge : graph.adj(v)) {
                // если рассматриваемое ребро улучшает расстояние между вершинами, то используем его
                if (distTo[edge.getFrom()] != Integer.MAX_VALUE && distTo[edge.getFrom()] + edge.getWeight() < distTo[edge.getTo()]) {
                    distTo[edge.getTo()] = distTo[edge.getFrom()] + edge.getWeight();
                    edgeTo[edge.getTo()] = edge;
                    // обновляем значение в приоритетной очереди для вершины
                    if (pq.contains(edge.getTo())) {
                        pq.decreaseKey(edge.getTo(), distTo[edge.getTo()]);
                    } else {
                        pq.insert(edge.getTo(), distTo[edge.getTo()]);
                    }
                }
            }
        }
    }

    // кратчайший путь из вершины src в вершину w
    public Iterable<DirectedEdge> path(int w) {
        if (distTo[w] == Integer.MAX_VALUE) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[w]; e != null; e = edgeTo[e.getFrom()]) {
            path.push(e);
        }
        return path;
    }

    public static void main(String[] args) {
        checkPositive();
        checkNegative();
    }

    private static void checkPositive() {
        WeightedDigraph graph = new WeightedDigraphList(8);
        graph.addEdge(new DirectedEdge(0, 2, 1));
        graph.addEdge(new DirectedEdge(0, 4, 8));
        graph.addEdge(new DirectedEdge(1, 3, 3));
        graph.addEdge(new DirectedEdge(2, 7, 5));
        graph.addEdge(new DirectedEdge(3, 6, 11));
        graph.addEdge(new DirectedEdge(4, 5, 6));
        graph.addEdge(new DirectedEdge(4, 7, 7));
        graph.addEdge(new DirectedEdge(5, 1, 4));
        graph.addEdge(new DirectedEdge(5, 4, 6));
        graph.addEdge(new DirectedEdge(5, 7, 2));
        graph.addEdge(new DirectedEdge(6, 0, 12));
        graph.addEdge(new DirectedEdge(6, 2, 10));
        graph.addEdge(new DirectedEdge(6, 4, 13));
        graph.addEdge(new DirectedEdge(7, 3, 9));
        graph.addEdge(new DirectedEdge(7, 5, 2));

        int[] expectedDistTo = new int[] { 0, 12, 1, 15, 8, 8, 26, 6 };
        DirectedEdge[] expectedPath01 = new DirectedEdge[] {
            new DirectedEdge(0, 2, 1),
            new DirectedEdge(2, 7, 5),
            new DirectedEdge(7, 5, 2),
            new DirectedEdge(5, 1, 4)
        };
        DirectedEdge[] expectedPath02 = new DirectedEdge[] {
            new DirectedEdge(0, 2, 1)
        };
        DirectedEdge[] expectedPath03 = new DirectedEdge[] {
            new DirectedEdge(0, 2, 1),
            new DirectedEdge(2, 7, 5),
            new DirectedEdge(7, 3, 9)
        };
        DirectedEdge[] expectedPath04 = new DirectedEdge[] {
            new DirectedEdge(0, 4, 8)
        };
        DirectedEdge[] expectedPath05 = new DirectedEdge[] {
            new DirectedEdge(0, 2, 1),
            new DirectedEdge(2, 7, 5),
            new DirectedEdge(7, 5, 2)
        };
        DirectedEdge[] expectedPath06 = new DirectedEdge[] {
            new DirectedEdge(0, 2, 1),
            new DirectedEdge(2, 7, 5),
            new DirectedEdge(7, 3, 9),
            new DirectedEdge(3, 6, 11)
        };
        DirectedEdge[] expectedPath07 = new DirectedEdge[] {
            new DirectedEdge(0, 2, 1),
            new DirectedEdge(2, 7, 5)
        };

        Dijkstra dijkstra = new Dijkstra(graph, 0);
        System.out.println("Алгоритм Дейкстры, Кратчайшие пути из вершины 0: " + Arrays.toString(dijkstra.distTo));
        check(expectedDistTo, dijkstra.distTo);

        Iterable<DirectedEdge> path01 = dijkstra.path(1);
        System.out.println("Путь из вершины 0 в вершину 1: " + path01);
        checkPath(expectedPath01, path01);

        Iterable<DirectedEdge> path02 = dijkstra.path(2);
        System.out.println("Путь из вершины 0 в вершину 2: " + path02);
        checkPath(expectedPath02, path02);

        Iterable<DirectedEdge> path03 = dijkstra.path(3);
        System.out.println("Путь из вершины 0 в вершину 3: " + path03);
        checkPath(expectedPath03, path03);

        Iterable<DirectedEdge> path04 = dijkstra.path(4);
        System.out.println("Путь из вершины 0 в вершину 4: " + path04);
        checkPath(expectedPath04, path04);

        Iterable<DirectedEdge> path05 = dijkstra.path(5);
        System.out.println("Путь из вершины 0 в вершину 5: " + path05);
        checkPath(expectedPath05, path05);

        Iterable<DirectedEdge> path06 = dijkstra.path(6);
        System.out.println("Путь из вершины 0 в вершину 6: " + path06);
        checkPath(expectedPath06, path06);

        Iterable<DirectedEdge> path07 = dijkstra.path(7);
        System.out.println("Путь из вершины 0 в вершину 7: " + path07);
        checkPath(expectedPath07, path07);
    }

    private static void checkNegative() {
        WeightedDigraph graph = new WeightedDigraphList(4);
        graph.addEdge(new DirectedEdge(0, 1, -2));
        graph.addEdge(new DirectedEdge(0, 2, 5));
        graph.addEdge(new DirectedEdge(0, 3, 7));
        graph.addEdge(new DirectedEdge(1, 2, 6));
        graph.addEdge(new DirectedEdge(1, 3, 8));
        graph.addEdge(new DirectedEdge(2, 0, -1));
        graph.addEdge(new DirectedEdge(3, 1, 3));
        graph.addEdge(new DirectedEdge(3, 2, -4));

        checkThrows(() -> new Dijkstra(graph, 0));
        System.out.println("Алгоритм Дейкстры: ОТРИЦАТЕЛЬНЫЕ ВЕСА");
    }

    private static void check(int[] expected, int[] actual) {
        if (!Arrays.equals(expected, actual)) {
            throw new IllegalStateException("Ошибка!");
        }
    }

    private static void checkPath(DirectedEdge[] expected, Iterable<DirectedEdge> actual) {
        boolean ok = actual != null;
        if (ok) {
            int i = 0;
            for (DirectedEdge edge : actual) {
                ok = i < expected.length && expected[i++].equals(edge);
                if (!ok) {
                    break;
                }
            }
            if (ok && i != expected.length) {
                ok = false;
            }
        }
        if (!ok) {
            throw new IllegalStateException("Ошибка!");
        }
    }

    private static void checkThrows(Runnable method) {
        try {
            method.run();
            throw new IllegalStateException("Должна быть ошибка!");
        } catch (Throwable ignored) {
        }
    }
}
