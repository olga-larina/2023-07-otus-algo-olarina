package ru.otus.graph;

/**
 * Взвешенный ориентированный граф
 */
public interface WeightedDigraph {

    /**
     * Добавить ребро
     */
    void addEdge(DirectedEdge edge);

    /**
     * Вершины
     */
    Iterable<Integer> vertices();

    /**
     * Рёбра
     */
    Iterable<DirectedEdge> edges();

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
    Iterable<DirectedEdge> adj(int v);

}
