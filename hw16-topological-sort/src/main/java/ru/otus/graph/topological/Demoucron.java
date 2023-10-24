package ru.otus.graph.topological;

import ru.otus.common.Queue;
import ru.otus.graph.DiGraphList;
import ru.otus.graph.DiGraphVector;
import ru.otus.graph.Digraph;

import java.util.Arrays;

/**
 * Алгоритм топологической сортировки Демукрона
 */
public class Demoucron {

    private final int[][] topologicalLevels;

    public Demoucron(Digraph graph) {
        Queue<Queue<Integer>> levels = topologicalSort(graph);
        
        topologicalLevels = new int[levels.size()][];
        int lvl = 0;
        for (Queue<Integer> level : levels) {
            topologicalLevels[lvl] = new int[level.size()];
            int vertices = 0;
            for (int v : level) {
                topologicalLevels[lvl][vertices++] = v;
            }
            lvl++;
        }
    }

    private Queue<Queue<Integer>> topologicalSort(Digraph graph) {
        // уровни топологической сортировки
        Queue<Queue<Integer>> levels = new Queue<>();

        // матрица смежности
        int[][] matrix = graph.matrixAdjacency();

        // полустепени захода
        int[] degrees = new int[graph.verticesCount()];
        for (int col : graph.vertices()) {
            for (int row : graph.vertices()) {
                degrees[col] += matrix[row][col];
            }
        }

        boolean cycle; // есть ли цикл
        while (true) {
            Queue<Integer> level = new Queue<>();
            boolean positive = false;
            // ищем нулевые полустепени захода, помечаем их как -1, чтобы больше не обрабатывать
            // сохраняем флаг наличия положительных полустепеней для определения цикла при необходимости
            for (int i = 0; i < degrees.length; i++) {
                if (degrees[i] == 0) {
                    level.enqueue(i);
                    degrees[i] = -1;
                } else if (degrees[i] > 0) {
                    positive = true;
                }
            }

            // если на данном уровне нет нулевых полустепеней захода, то либо обошли все вершины, либо есть цикл
            // чтобы определить наличие цикла, смотрим на флаг positive
            if (level.isEmpty()) {
                cycle = positive;
                break;
            }

            // вычитаем обработанные на данном уровне вершины, чтобы убрать их вклад в полустепени захода
            for (int v : level) {
                for (int i = 0; i < degrees.length; i++) {
                    if (degrees[i] > 0) {
                        degrees[i] -= matrix[v][i];
                    }
                }
            }

            // данный уровень непустой => добавляем в очередь уровней
            levels.enqueue(level);

            // повторяем, пока не будут обработаны все вершины или не найдётся цикл
        }

        if (cycle) {
            throw new IllegalStateException("Цикл в графе");
        }
        
        return levels;
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
        // топологическая сортировка по уровням
        int[][] expectedLevels = new int[][] {
            new int[] { 2, 8 },
            new int[] { 3, 9 },
            new int[] { 0, 5 },
            new int[] { 1 },
            new int[] { 4 },
            new int[] { 6 },
            new int[] { 7 }
        };

        Digraph digraphList = new DiGraphList(vector);
        Digraph digraphVector = new DiGraphVector(vector);

        /**
         * граф на списках смежности
         */
        Demoucron demoucronList = new Demoucron(digraphList);
        int[][] demoucronListLevels = demoucronList.topologicalLevels;
        System.out.println("Граф на списках смежности: " + Arrays.deepToString(demoucronListLevels));
        check(expectedLevels, demoucronListLevels);

        /**
         * граф на векторах смежности
         */
        Demoucron demoucronVector = new Demoucron(digraphVector);
        int[][] demoucronVectorLevels = demoucronVector.topologicalLevels;
        System.out.println("Граф на векторах смежности: " + Arrays.deepToString(demoucronVectorLevels));
        check(expectedLevels, demoucronVectorLevels);
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
        checkThrows(() -> new Demoucron(digraphList));
        System.out.println("Граф на списках смежности: ЦИКЛ");

        /**
         * граф на векторах смежности
         */
        checkThrows(() -> new Demoucron(digraphVector));
        System.out.println("Граф на векторах смежности: ЦИКЛ");
    }

    private static void check(int[][] expected, int[][] actual) {
        if (!Arrays.deepEquals(expected, actual)) {
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
