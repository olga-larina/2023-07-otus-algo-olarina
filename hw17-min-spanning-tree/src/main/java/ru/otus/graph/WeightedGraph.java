package ru.otus.graph;

/**
 * Взвешенный неориентированный граф
 */
public interface WeightedGraph {

    /**
     * Добавить ребро от вершины v к вершине w
     */
    void addEdge(Edge edge);

    /**
     * Вершины
     */
    Iterable<Integer> vertices();

    /**
     * Рёбра
     */
    Iterable<Edge> edges();

    /**
     * Количество вершин
     */
    int verticesCount();

    /**
     * Количество рёбер
     */
    int edgesCount();

    /**
     * Рёбра, инцидентные вершине v
     */
    Iterable<Edge> adj(int v);

}
