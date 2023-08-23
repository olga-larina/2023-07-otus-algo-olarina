package ru.otus;

import ru.otus.array.*;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Hw04Main {

    public static void main(String[] args) {
        new Hw04Main().measure();
    }

    private Map<String, DynamicArray<Integer>> initMap() {
        Map<String, DynamicArray<Integer>> arraysMap = new LinkedHashMap<>();
        arraysMap.put("Single", new SingleArray<>());
        arraysMap.put("Vector 10", new VectorArray<>(10));
        arraysMap.put("Vector 100", new VectorArray<>(100));
        arraysMap.put("Vector 1000", new VectorArray<>(1000));
        arraysMap.put("Factor 50,10", new FactorArray<>(50, 10));
        arraysMap.put("Factor 100,10", new FactorArray<>(100, 10));
        arraysMap.put("Matrix 10", new MatrixArray<>(10));
        arraysMap.put("Matrix 100", new MatrixArray<>(100));
        arraysMap.put("Matrix 1000", new MatrixArray<>(1000));
        arraysMap.put("ArrayList", new ArrayListArray<>());
        return arraysMap;
    }

    private void measure() {
        int[] cnt = new int[]{ 10, 100, 1000, 10_000, 100_000 };

        Map<String, DynamicArray<Integer>> initArraysMap = initMap();
        Map<String, DynamicArray<Integer>> initArraysMapWithValues = initMap();
        for (int n : cnt) {
            for (DynamicArray<Integer> array : initArraysMapWithValues.values()) {
                for (int i = 0; i < n; i++) {
                    array.add(i);
                }
            }
        }

        List<Action> actions = new ArrayList<>();
        actions.add(new Action("addToTail", this::addToTail, false));
        actions.add(new Action("addToHead", this::addToHead, false));
        actions.add(new Action("addToMiddle", this::addToMiddle, false));
        actions.add(new Action("removeFromTail", this::removeFromTail, true));
        actions.add(new Action("removeFromHead", this::removeFromHead, true));
        actions.add(new Action("removeFromMiddle", this::removeFromMiddle, true));
        actions.add(new Action("getFromTail", this::getFromTail, true));
        actions.add(new Action("getFromHead", this::getFromHead, true));
        actions.add(new Action("getFromMiddle", this::getFromMiddle, true));
        for (Action action : actions) {
            Map<Integer, Map<String, Long>> results = new LinkedHashMap<>();
            for (int n : cnt) {
                Map<String, Long> resultsByArray = new LinkedHashMap<>();
                for (String arrayName : initArraysMap.keySet()) {
                    DynamicArray<Integer> array = (action.withValues ? initArraysMapWithValues.get(arrayName) : initArraysMap.get(arrayName)).copy();
                    long start = System.currentTimeMillis();
                    action.method.accept(n, array);
                    resultsByArray.put(arrayName, (System.currentTimeMillis() - start));
                }
                results.put(n, resultsByArray);
            }
            print(action.name, results);
        }
    }

    private void addToTail(int n, DynamicArray<Integer> array) {
        for (int i = 0; i < n; i++) {
            array.add(i);
        }
    }

    private void addToHead(int n, DynamicArray<Integer> array) {
        for (int i = 0; i < n; i++) {
            array.add(i, 0);
        }
    }

    private void addToMiddle(int n, DynamicArray<Integer> array) {
        for (int i = 0; i < n; i++) {
            array.add(i, i / 2);
        }
    }

    private void removeFromTail(int n, DynamicArray<Integer> array) {
        for (int i = 0; i < n; i++) {
            array.remove(array.size() - 1);
        }
    }

    private void removeFromHead(int n, DynamicArray<Integer> array) {
        for (int i = 0; i < n; i++) {
            array.remove(0);
        }
    }

    private void removeFromMiddle(int n, DynamicArray<Integer> array) {
        for (int i = 0; i < n; i++) {
            array.remove((array.size() - 1) / 2);
        }
    }

    private void getFromTail(int n, DynamicArray<Integer> array) {
        for (int i = 0; i < n; i++) {
            array.get(i);
        }
    }

    private void getFromHead(int n, DynamicArray<Integer> array) {
        for (int i = 0; i < n; i++) {
            array.get(0);
        }
    }

    private void getFromMiddle(int n, DynamicArray<Integer> array) {
        for (int i = 0; i < n; i++) {
            array.get((n - 1) / 2);
        }
    }

    private void print(String action, Map<Integer, Map<String, Long>> results) {
        Set<String> arrays = results.values().stream().flatMap(res -> res.keySet().stream()).collect(Collectors.toCollection(LinkedHashSet::new));
        StringBuilder headerFormatSb = new StringBuilder().append("| N      |");
        StringBuilder delimFormatSb = new StringBuilder().append("+--------+");
        for (String array : arrays) {
            headerFormatSb.append(String.format(" %-15s |", array));
            delimFormatSb.append("-----------------+");
        }
        headerFormatSb.append("%n");
        delimFormatSb.append("%n");
        String headerFormat = headerFormatSb.toString();
        String delimFormat = delimFormatSb.toString();

        System.out.printf("Action: %s\n", action);
        System.out.format(delimFormat);
        System.out.format(headerFormat);
        System.out.format(delimFormat);
        for (Map.Entry<Integer, Map<String, Long>> resultEntry : results.entrySet()) {
            Integer n = resultEntry.getKey();
            Map<String, Long> msByArray = resultEntry.getValue();
            StringBuilder rowFormatSb = new StringBuilder().append(String.format("| %-6d |", n));
            msByArray.forEach((array, ms) -> rowFormatSb.append(String.format(" %-15d |", ms)));
            System.out.println(rowFormatSb);
        }
        System.out.format(delimFormat);
    }

    private static class Action {
        String name;
        BiConsumer<Integer, DynamicArray<Integer>> method;
        boolean withValues;

        Action(String name, BiConsumer<Integer, DynamicArray<Integer>> method, boolean withValues) {
            this.name = name;
            this.method = method;
            this.withValues = withValues;
        }
    }
}