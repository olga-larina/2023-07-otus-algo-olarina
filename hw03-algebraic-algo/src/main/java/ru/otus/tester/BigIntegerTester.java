package ru.otus.tester;

import ru.otus.util.ExceptionUtils;

import java.math.BigInteger;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class BigIntegerTester extends Tester<BigInteger> {

    private final BigInteger epsilon = BigInteger.valueOf(Long.MAX_VALUE);

    public BigIntegerTester(List<Task<BigInteger>> tasks, Path path) {
        super(tasks, path);
    }

    @Override
    boolean compare(String expected, BigInteger actual) {
        BigInteger expectedBigint = null;
        try {
            expectedBigint = new BigInteger(expected);
        } catch (Throwable ex) {
            System.err.println(ExceptionUtils.getMessage(ex));
        }
        if ((expectedBigint != null && expectedBigint.compareTo(epsilon) <= 0) || expectedBigint == null || actual == null) {
            return Objects.equals(expectedBigint, actual);
        }
        return expectedBigint.toString().length() == actual.toString().length()
            && expectedBigint.divide(epsilon).equals(actual.divide(epsilon));
    }
}
