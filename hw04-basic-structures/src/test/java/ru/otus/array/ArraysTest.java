package ru.otus.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArraysTest {

    private final List<DynamicArray<Integer>> arrays = new ArrayList<>();

    @BeforeEach
    void setUp() {
        arrays.add(new SingleArray<>());
        arrays.add(new VectorArray<>(10));
        arrays.add(new FactorArray<>(50, 10));
        arrays.add(new MatrixArray<>(10));
        arrays.add(new ArrayListArray<>());
    }

    @Test
    void shouldAddElementsToTail() {
        int n = 1000;
        List<Integer> expected = IntStream.range(0, n).boxed().toList();
        for (DynamicArray<Integer> array : arrays) {
            for (int i = 0; i < n; i++) {
                array.add(i);
                assertEquals(i + 1, array.size());
            }
            assertEquals(n, array.size());
            for (int i = 0; i < n; i++) {
                assertEquals(expected.get(i), array.get(i));
            }
        }
    }

    @Test
    void shouldAddElementsToHead() {
        int n = 1000;
        List<Integer> expected = IntStream.range(0, n).boxed().sorted(Collections.reverseOrder()).toList();
        for (DynamicArray<Integer> array : arrays) {
            for (int i = 0; i < n; i++) {
                array.add(i, 0);
                assertEquals(i + 1, array.size());
            }
            assertEquals(n, array.size());
            for (int i = 0; i < n; i++) {
                assertEquals(expected.get(i), array.get(i));
            }
        }
    }

    @Test
    void shouldAddElementsToMiddle() {
        int n = 1000;
        for (DynamicArray<Integer> array : arrays) {
            List<Integer> expected = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                array.add(i, i / 2);
                expected.add(i / 2, i);
                assertEquals(i + 1, array.size());
            }
            assertEquals(n, array.size());
            for (int i = 0; i < n; i++) {
                assertEquals(expected.get(i), array.get(i));
            }
        }
    }

    @Test
    void shouldRemoveElementsFromTail() {
        int n = 1000;
        for (DynamicArray<Integer> array : arrays) {
            List<Integer> expected = new ArrayList<>(IntStream.range(0, n).boxed().toList());
            for (int i = 0; i < n; i++) {
                array.add(i);
            }
            assertEquals(n, array.size());
            for (int i = 0; i < n; i++) {
                array.remove(array.size() - 1);
                expected.remove(expected.size() - 1);
                for (int j = 0; j < expected.size(); j++) {
                    assertEquals(expected.get(j), array.get(j));
                }
            }
            assertEquals(0, array.size());
        }
    }

    @Test
    void shouldRemoveElementsFromHead() {
        int n = 1000;
        for (DynamicArray<Integer> array : arrays) {
            List<Integer> expected = new ArrayList<>(IntStream.range(0, n).boxed().toList());
            for (int i = 0; i < n; i++) {
                array.add(i);
            }
            assertEquals(n, array.size());
            for (int i = 0; i < n; i++) {
                array.remove(0);
                expected.remove(0);
                for (int j = 0; j < expected.size(); j++) {
                    assertEquals(expected.get(j), array.get(j));
                }
            }
            assertEquals(0, array.size());
        }
    }

    @Test
    void shouldRemoveElementsFromMiddle() {
        int n = 1000;
        for (DynamicArray<Integer> array : arrays) {
            List<Integer> expected = new ArrayList<>(IntStream.range(0, n).boxed().toList());
            for (int i = 0; i < n; i++) {
                array.add(i);
            }
            assertEquals(n, array.size());
            for (int i = 0; i < n; i++) {
                array.remove((array.size() - 1) / 2);
                expected.remove((expected.size() - 1) / 2);
                for (int j = 0; j < expected.size(); j++) {
                    assertEquals(expected.get(j), array.get(j));
                }
            }
            assertEquals(0, array.size());
        }
    }
}