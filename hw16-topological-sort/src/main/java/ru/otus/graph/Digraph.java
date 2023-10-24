package ru.otus.graph;

public interface Digraph extends Graph {

    /**
     * "Перевернуть" граф, т.е. поменять направление
     */
    Digraph reverse();
}
