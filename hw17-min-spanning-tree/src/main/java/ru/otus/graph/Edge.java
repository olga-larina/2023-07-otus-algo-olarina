package ru.otus.graph;

import java.util.Objects;

/**
 * Ребро взвешенного неориентированного графа
 */
public class Edge implements Comparable<Edge> {

    private final int v; // меньшая вершина
    private final int w; // большая вершина
    private final int weight;

    public Edge(int v1, int v2, int weight) {
        if (v1 < v2) {
            this.v = v1;
            this.w = v2;
        } else {
            this.v = v2;
            this.w = v1;
        }
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    /**
     * Какая-либо вершина
     * Из-за того, что граф ненаправленный, просто использовать v и w не получится, т.к. рёбер одна и та же вершина может быть как v, так и w
     */
    public int either() {
        return v;
    }

    /**
     * Противоположная вершина
     */
    public int other(int vertex) {
        if (vertex == v) {
            return w;
        }
        if (vertex == w) {
            return v;
        }
        throw new IllegalArgumentException("Wrong vertex");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return v == edge.v && w == edge.w && weight == edge.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v, w, weight);
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(weight, o.getWeight());
    }

    @Override
    public String toString() {
        return String.format("[%d-%d: %d]", v, w, weight);
    }

}
