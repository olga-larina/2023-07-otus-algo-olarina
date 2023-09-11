package ru.otus.tester;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class IntArrayTester extends Tester<int[]> {

    public IntArrayTester(List<Task<int[]>> tasks, Path path) {
        super(tasks, path);
    }

    @Override
    boolean compare(String expected, int[] actual) {
        return Arrays.equals(
            Arrays.stream(expected.split("\\s+")).mapToInt(v -> Integer.parseInt(v.trim())).toArray(),
            actual
        );
    }
}
