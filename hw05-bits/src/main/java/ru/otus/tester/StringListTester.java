package ru.otus.tester;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StringListTester extends Tester<List<String>> {

    public StringListTester(List<Task<List<String>>> tasks, Path path) {
        super(tasks, path);
    }

    @Override
    boolean compare(String expected, List<String> actual) {
        return Objects.equals(Arrays.stream(expected.split("\\s+")).map(String::trim).toList(), actual);
    }
}
