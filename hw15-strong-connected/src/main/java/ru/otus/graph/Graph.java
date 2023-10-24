package ru.otus.graph;

public interface Graph {

    /**
     * Добавить ребро от вершины v к вершине w
     */
    void addEdge(int v, int w);

    /**
     * Вершины
     */
    Iterable<Integer> vertices();

    /**
     * Количество вершин
     */
    int verticesCount();

    /**
     * Вершины, смежные вершине v
     */
    Iterable<Integer> adj(int v);

    /**
     * Матрица смежности
     * В случае цикла значение будет равно 2
     */
    default int[][] matrixAdjacency() {
        int[][] matrix = new int[verticesCount()][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new int[verticesCount()];
        }
        for (int v : vertices()) {
            for (int w : adj(v)) {
                matrix[v][w]++;
            }
        }
        return matrix;
    }

}
