package ru.otus.tester;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class StringTester extends Tester<String> {

    public StringTester(List<Task<String>> tasks, Path path) {
        super(tasks, path);
    }

    @Override
    boolean compare(String expected, String actual) {
        return Objects.equals(expected, actual);
    }
}
