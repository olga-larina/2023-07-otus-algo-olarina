package ru.otus.graph.path;

import ru.otus.common.Stack;
import ru.otus.graph.DirectedEdge;
import ru.otus.graph.WeightedDigraph;
import ru.otus.graph.WeightedDigraphList;

import java.util.Arrays;

/**
 * Алгоритм Беллмана-Форда для поиска кратчайшего пути из одной вершины во все остальные
 * При наличии отрицательного цикла в графе не применяется
 */
public class BellmanFord {

    private boolean hasNegativeCycle; // есть ли отрицательный цикл
    private final int src; // начальная вершина, из которой ищем пути
    private final int[] distTo; // distTo[w] - кратчайшее расстояние из вершины src в вершину w
    private final DirectedEdge[] edgeTo; // edgeTo[w] - последнее ребро на кратчайшем пути из вершины src в вершину w

    public BellmanFord(WeightedDigraph graph, int src) {
        this.src = src;
        this.distTo = new int[graph.verticesCount()];
        this.edgeTo = new DirectedEdge[graph.verticesCount()];
        bellmanFord(graph, src);
    }

    private void bellmanFord(WeightedDigraph graph, int src) {
        // все расстояния инициализируем максимальным значением
        Arrays.fill(distTo, Integer.MAX_VALUE);
        // расстояние из исходной вершины в саму себя равно 0
        distTo[src] = 0;

        // пробуем улучшать расстояния между вершинами, используя дополнительные рёбра
        // 1 итерация - 1 ребро, 2 итерация - 2 ребра и т.п.
        // максимум рёбер в кратчайшем пути может быть graph.verticesCount() - 1
        for (int i = 1; i < graph.verticesCount(); i++) {
            for (DirectedEdge edge : graph.edges()) {
                if (distTo[edge.getFrom()] != Integer.MAX_VALUE && distTo[edge.getFrom()] + edge.getWeight() < distTo[edge.getTo()]) {
                    distTo[edge.getTo()] = distTo[edge.getFrom()] + edge.getWeight();
                    edgeTo[edge.getTo()] = edge;
                }
            }
        }

        // дополнительный шаг для проверки отрицательных циклов
        // на предыдущем шаге уже должны были найти кратчайшие расстояния
        // если сейчас какое-то расстояние уменьшится, значит, что есть отрицательный цикл
        for (DirectedEdge edge : graph.edges()) {
            if (distTo[edge.getFrom()] != Integer.MAX_VALUE && distTo[edge.getFrom()] + edge.getWeight() < distTo[edge.getTo()]) {
                hasNegativeCycle = true;
                throw new IllegalStateException("Отрицательный цикл в графе");
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
        checkAcyclic();
        checkCyclic();
    }

    private static void checkAcyclic() {
        WeightedDigraph graph = new WeightedDigraphList(4);
        graph.addEdge(new DirectedEdge(0, 1, -2));
        graph.addEdge(new DirectedEdge(0, 2, 5));
        graph.addEdge(new DirectedEdge(0, 3, 7));
        graph.addEdge(new DirectedEdge(1, 2, 6));
        graph.addEdge(new DirectedEdge(1, 3, 8));
        graph.addEdge(new DirectedEdge(2, 0, -1));
        graph.addEdge(new DirectedEdge(3, 1, 3));
        graph.addEdge(new DirectedEdge(3, 2, -4));

        int[] expectedDistTo = new int[] {  0, -2,  2,  6 };
        DirectedEdge[] expectedPath01 = new DirectedEdge[] {
            new DirectedEdge(0, 1, -2)
        };
        DirectedEdge[] expectedPath02 = new DirectedEdge[] {
            new DirectedEdge(0, 1, -2),
            new DirectedEdge(1, 3,  8),
            new DirectedEdge(3, 2, -4)
        };
        DirectedEdge[] expectedPath03 = new DirectedEdge[] {
            new DirectedEdge(0, 1, -2),
            new DirectedEdge(1, 3,  8)
        };

        BellmanFord bellmanFord = new BellmanFord(graph, 0);
        System.out.println("Алгоритм Беллмана-Форда, Кратчайшие пути из вершины 0: " + Arrays.toString(bellmanFord.distTo));
        check(expectedDistTo, bellmanFord.distTo);

        Iterable<DirectedEdge> path01 = bellmanFord.path(1);
        System.out.println("Путь из вершины 0 в вершину 1: " + path01);
        checkPath(expectedPath01, path01);

        Iterable<DirectedEdge> path02 = bellmanFord.path(2);
        System.out.println("Путь из вершины 0 в вершину 2: " + path02);
        checkPath(expectedPath02, path02);

        Iterable<DirectedEdge> path03 = bellmanFord.path(3);
        System.out.println("Путь из вершины 0 в вершину 3: " + path03);
        checkPath(expectedPath03, path03);
    }

    private static void checkCyclic() {
        // 0 -> 1 -> 2 -> 0 - это отрицательный цикл (вес -1)
        WeightedDigraph graph = new WeightedDigraphList(4);
        graph.addEdge(new DirectedEdge(0, 1, -2));
        graph.addEdge(new DirectedEdge(0, 2, 5));
        graph.addEdge(new DirectedEdge(0, 3, 7));
        graph.addEdge(new DirectedEdge(1, 2, 6));
        graph.addEdge(new DirectedEdge(1, 3, 8));
        graph.addEdge(new DirectedEdge(2, 0, -5));
        graph.addEdge(new DirectedEdge(3, 1, 3));
        graph.addEdge(new DirectedEdge(3, 2, -4));

        checkThrows(() -> new BellmanFord(graph, 0));
        System.out.println("Алгоритм Беллмана-Форда: ОТРИЦАТЕЛЬНЫЙ ЦИКЛ");
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
