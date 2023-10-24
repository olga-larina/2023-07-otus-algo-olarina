package ru.otus.graph.topological;

import ru.otus.graph.DiGraphList;
import ru.otus.graph.DiGraphVector;
import ru.otus.graph.Digraph;
import ru.otus.graph.dfs.DepthFirstSearch;

import java.util.Arrays;

/**
 * Алгоритм топологической сортировки Тарьяна
 */
public class Tarjan {

    private final int[] topologicalOrder;

    public Tarjan(Digraph graph) {
        /**
         * выполняем DFS
         */
        DepthFirstSearch depthFirstSearch = new DepthFirstSearch(graph);
        /**
         * топологический порядок (для графа с циклом не определён)
         */
        if (depthFirstSearch.isCycle()) {
            throw new IllegalStateException("Цикл в графе");
        }
        topologicalOrder = new int[graph.verticesCount()];
        int i = 0;
        for (int v : depthFirstSearch.reversePostorder()) {
            topologicalOrder[i++] = v;
        }
    }

    public static void main(String[] args) {
        checkAcyclic();
        checkWithCycle();
    }

    private static void checkAcyclic() {
        Integer[][] vector = new Integer[][] {
            new Integer[] { 1,      null,   null,   null },
            new Integer[] { 4,      null,   null,   null },
            new Integer[] { 3,      null,   null,   null },
            new Integer[] { 0,      1,      4,      5 },
            new Integer[] { 6,      null,   null,   null },
            new Integer[] { 4,      7,      null,   null },
            new Integer[] { 7,      null,   null,   null },
            new Integer[] { null,   null,   null,   null },
            new Integer[] { 9,      null,   null,   null },
            new Integer[] { null,   null,   null,   null },
        };
        // одни из возможных вариантов топологической сортировки (их может быть несколько)
        int[][] expectedOrders = new int[][] {
            new int[] { 2, 3, 5, 0, 1, 4, 6, 7, 8, 9 },
            new int[] { 8, 9, 2, 3, 5, 0, 1, 4, 6, 7 }
        };

        Digraph digraphList = new DiGraphList(vector);
        Digraph digraphVector = new DiGraphVector(vector);

        /**
         * граф на списках смежности
         */
        Tarjan tarjanRecursiveList = new Tarjan(digraphList);
        int[] tarjanRecursiveListOrder = tarjanRecursiveList.topologicalOrder;
        System.out.println("Граф на списках смежности: " + Arrays.toString(tarjanRecursiveListOrder));
        check(expectedOrders, tarjanRecursiveListOrder);

        /**
         * граф на векторах смежности
         */
        Tarjan tarjanRecursiveVector = new Tarjan(digraphVector);
        int[] tarjanRecursiveVectorOrder = tarjanRecursiveVector.topologicalOrder;
        System.out.println("Граф на векторах смежности: " + Arrays.toString(tarjanRecursiveVectorOrder));
        check(expectedOrders, tarjanRecursiveVectorOrder);
    }

    private static void checkWithCycle() {
        Integer[][] vector = new Integer[][] {
            new Integer[] { 1,  null,   null },
            new Integer[] { 2,  4,      5 },
            new Integer[] { 3,  6,      null },
            new Integer[] { 2,  7,      null },
            new Integer[] { 0,  5,      null },
            new Integer[] { 6,  null,   null },
            new Integer[] { 5,  null,   null },
            new Integer[] { 3,  6,      null },
        };

        Digraph digraphList = new DiGraphList(vector);
        Digraph digraphVector = new DiGraphVector(vector);

        /**
         * граф на списках смежности
         */
        checkThrows(() -> new Tarjan(digraphList));
        System.out.println("Граф на списках смежности: ЦИКЛ");

        /**
         * граф на векторах смежности
         */
        checkThrows(() -> new Tarjan(digraphVector));
        System.out.println("Граф на векторах смежности: ЦИКЛ");
    }

    private static void check(int[][] expected, int[] actual) {
        boolean found = false;
        for (int[] exp : expected) {
            if (Arrays.equals(exp, actual)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalStateException("Ошибка!");
        }
    }

    private static void checkThrows(Runnable method) {
        boolean wasThrown = false;
        try {
            method.run();
        } catch (Throwable ignored) {
            wasThrown = true;
        }
        if (!wasThrown) {
            throw new IllegalStateException("Должна быть ошибка!");
        }
    }

}
