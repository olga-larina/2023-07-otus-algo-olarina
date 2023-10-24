package ru.otus.graph.components;

import ru.otus.graph.DiGraphList;
import ru.otus.graph.DiGraphVector;
import ru.otus.graph.Digraph;
import ru.otus.graph.dfs.DepthFirstSearch;

import java.util.Arrays;

/**
 * Алгоритм Косарайю для поиска компонентов сильной связности в ориентированном графе
 */
public class Kosaraju {

    private final int[] component;

    public Kosaraju(Digraph graph, boolean recursive) {
        /**
         * "переворачиваем" граф
         */
        Digraph reversed = graph.reverse();
        /**
         * запускаем поиск в глубину для перевёрнутого графа в любом порядке обхода вершин
         */
        DepthFirstSearch depthFirstSearchReversed = new DepthFirstSearch(reversed, recursive);
        /**
         * берём развёрнутый список вершин в порядке выхода из DFS (5-6-2-3-7-0-4-1)
         */
        Iterable<Integer> reversePostorder = depthFirstSearchReversed.reversePostorder();
        /**
         * выполняем DFS на начальном графе в порядке вершин из reversePostorder, сохраняем номер компоненты
         */
        DepthFirstSearch depthFirstSearch = new DepthFirstSearch(graph, recursive, reversePostorder);
        /**
         * компоненты сильной связности
         */
        component = depthFirstSearch.components();
    }

    /**
     * компоненты: 0-1-4, 2-3-7, 5-6
     */
    public static void main(String[] args) {
        Integer[][] vector = new Integer[][] {
            new Integer[] { 1,  null,   null },
            new Integer[] { 2,  4,      5 },
            new Integer[] { 3,  6,      null },
            new Integer[] { 2,  7,      null },
            new Integer[] { 0,  5,      null },
            new Integer[] { 6,  null,   null },
            new Integer[] { 5,  null,   null },
            new Integer[] { 3,  6,      null }
        };
        int[] expectedComponents = new int[] { 2, 2, 1, 1, 2, 0, 0, 1 };

        Digraph digraphList = new DiGraphList(vector);
        Digraph digraphVector = new DiGraphVector(vector);

        /**
         * рекурсивный DFS, граф на списках смежности
         */
        Kosaraju kosarajuRecursiveList = new Kosaraju(digraphList, true);
        int[] kosarajuRecursiveListComponents = kosarajuRecursiveList.component;
        System.out.println("Рекурсивный DFS, граф на списках смежности: " + Arrays.toString(kosarajuRecursiveListComponents));
        check(expectedComponents, kosarajuRecursiveListComponents);

        /**
         * рекурсивный DFS, граф на векторах смежности
         */
        Kosaraju kosarajuRecursiveVector = new Kosaraju(digraphVector, true);
        int[] kosarajuRecursiveVectorComponents = kosarajuRecursiveVector.component;
        System.out.println("Рекурсивный DFS, граф на векторах смежности: " + Arrays.toString(kosarajuRecursiveVectorComponents));
        check(expectedComponents, kosarajuRecursiveVectorComponents);

        /**
         * итеративный DFS, граф на списках смежности
         */
        Kosaraju kosarajuIterativeList = new Kosaraju(digraphList, false);
        int[] kosarajuIterativeListComponents = kosarajuIterativeList.component;
        System.out.println("Итеративный DFS, граф на списках смежности: " + Arrays.toString(kosarajuIterativeListComponents));
        check(expectedComponents, kosarajuIterativeListComponents);

        /**
         * итеративный DFS, граф на векторах смежности
         */
        Kosaraju kosarajuIterativeVector = new Kosaraju(digraphVector, false);
        int[] kosarajuIterativeVectorComponents = kosarajuIterativeVector.component;
        System.out.println("Итеративный DFS, граф на векторах смежности: " + Arrays.toString(kosarajuIterativeVectorComponents));
        check(expectedComponents, kosarajuIterativeVectorComponents);

    }

    private static void check(int[] expected, int[] actual) {
        if (!Arrays.equals(expected, actual)) {
            throw new IllegalStateException("Ошибка!");
        }
    }
}
