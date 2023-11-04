package ru.otus.graph;

import java.util.Objects;

/**
 * Ребро взвешенного ориентированного графа
 */
public class DirectedEdge implements Comparable<DirectedEdge> {

    private final int from;
    private final int to;
    private final int weight;

    public DirectedEdge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectedEdge edge = (DirectedEdge) o;
        return from == edge.from && to == edge.to && weight == edge.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }

    @Override
    public int compareTo(DirectedEdge o) {
        return Integer.compare(weight, o.getWeight());
    }

    @Override
    public String toString() {
        return String.format("[%d -> %d: %d]", from, to, weight);
    }

}
