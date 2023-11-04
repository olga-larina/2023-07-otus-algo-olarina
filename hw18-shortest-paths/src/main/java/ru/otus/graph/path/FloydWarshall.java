package ru.otus.graph.path;

import ru.otus.common.Stack;
import ru.otus.graph.DirectedEdge;
import ru.otus.graph.WeightedDigraph;
import ru.otus.graph.WeightedDigraphList;

import java.util.Arrays;

/**
 * Алгоритм Флойда-Уоршалла для поиска кратчайшего пути из всех вершин во все
 * При наличии отрицательного цикла в графе не применяется
 */
public class FloydWarshall {

    private boolean hasNegativeCycle; // есть ли отрицательный цикл
    private final int[][] distTo; // distTo[v][w] - кратчайшее расстояние из вершины v в вершину w
    private final DirectedEdge[][] edgeTo; // edgeTo[v][w] - последнее ребро на кратчайшем пути из вершины v в вершину w

    public FloydWarshall(WeightedDigraph graph) {
        this.distTo = new int[graph.verticesCount()][graph.verticesCount()];
        this.edgeTo = new DirectedEdge[graph.verticesCount()][graph.verticesCount()];
        floydWarshall(graph);
    }

    private void floydWarshall(WeightedDigraph graph) {
        // все расстояния инициализируем максимальным значением
        for (int[] dist : distTo) {
            Arrays.fill(dist, Integer.MAX_VALUE);
        }

        // инициализируем расстояния, используя рёбра графа
        for (int v : graph.vertices()) {
            for (DirectedEdge edge : graph.adj(v)) {
                distTo[edge.getFrom()][edge.getTo()] = edge.getWeight();
                edgeTo[edge.getFrom()][edge.getTo()] = edge;
                // обрабатываем петли
                if (distTo[v][v] > 0) {
                    distTo[v][v] = 0;
                    edgeTo[v][v] = null;
                }
            }
        }

        // проходим по всем вершинам i, проверяем, что будет, если добавить вершину i между двумя другими v и w
        // если расстояние в этом случае уменьшается, то выбираем его
        for (int i : graph.vertices()) {
            for (int v : graph.vertices()) {
                // если нет ребра между вершиной v и i, значит, что не сможем уменьшить расстояние
                if (edgeTo[v][i] == null) {
                    continue;
                }
                for (int w : graph.vertices()) {
                    // если нет ребра между вершиной i и w, значит, что не сможем уменьшить расстояние
                    if (edgeTo[i][w] == null) {
                        continue;
                    }
                    if (distTo[v][w] > distTo[v][i] + distTo[i][w]) {
                        distTo[v][w] = distTo[v][i] + distTo[i][w];
                        edgeTo[v][w] = edgeTo[i][w];
                    }
                    // проверка на отрицательный цикл
                    if (distTo[v][v] < 0) {
                        hasNegativeCycle = true;
                        throw new IllegalStateException("Отрицательный цикл в графе");
                    }
                }
            }
        }
    }

    // кратчайший путь из вершины v в вершину w
    public Iterable<DirectedEdge> path(int v, int w) {
        if (distTo[v][w] == Integer.MAX_VALUE) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v][w]; e != null; e = edgeTo[v][e.getFrom()]) {
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

        int[][] expectedDistTo = new int[][] {
            new int[] {  0, -2,  2,  6 },
            new int[] {  3,  0,  4,  8 },
            new int[] { -1, -3,  0,  5 },
            new int[] { -5, -7, -4,  0 }
        };
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

        FloydWarshall floydWarshall = new FloydWarshall(graph);
        System.out.println("Алгоритм Флойда-Уоршалла, Кратчайшие пути: " + Arrays.deepToString(floydWarshall.distTo));
        check(expectedDistTo, floydWarshall.distTo);

        Iterable<DirectedEdge> path01 = floydWarshall.path(0, 1);
        System.out.println("Путь из вершины 0 в вершину 1: " + path01);
        checkPath(expectedPath01, path01);

        Iterable<DirectedEdge> path02 = floydWarshall.path(0, 2);
        System.out.println("Путь из вершины 0 в вершину 2: " + path02);
        checkPath(expectedPath02, path02);

        Iterable<DirectedEdge> path03 = floydWarshall.path(0, 3);
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

        checkThrows(() -> new FloydWarshall(graph));
        System.out.println("Алгоритм Флойда-Уоршалла: ОТРИЦАТЕЛЬНЫЙ ЦИКЛ");
    }

    private static void check(int[][] expected, int[][] actual) {
        if (!Arrays.deepEquals(expected, actual)) {
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
