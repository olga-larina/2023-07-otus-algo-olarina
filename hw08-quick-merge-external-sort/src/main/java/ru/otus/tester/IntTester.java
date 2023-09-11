package ru.otus.tester;

import ru.otus.util.ExceptionUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class IntTester extends Tester<Integer> {

    public IntTester(List<Task<Integer>> tasks, Path path) {
        super(tasks, path);
    }

    @Override
    boolean compare(String expected, Integer actual) {
        Integer expectedInt = null;
        try {
            expectedInt = Integer.parseInt(expected);
        } catch (Throwable ex) {
            System.err.println(ExceptionUtils.getMessage(ex));
        }
        return Objects.equals(expectedInt, actual);
    }
}
