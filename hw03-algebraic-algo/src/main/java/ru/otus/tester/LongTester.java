package ru.otus.tester;

import ru.otus.util.ExceptionUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class LongTester extends Tester<Long> {

    public LongTester(List<Task<Long>> tasks, Path path) {
        super(tasks, path);
    }

    @Override
    boolean compare(String expected, Long actual) {
        Long expectedLong = null;
        try {
            expectedLong = Long.parseLong(expected);
        } catch (Throwable ex) {
            System.err.println(ExceptionUtils.getMessage(ex));
        }
        return Objects.equals(expectedLong, actual);
    }
}
