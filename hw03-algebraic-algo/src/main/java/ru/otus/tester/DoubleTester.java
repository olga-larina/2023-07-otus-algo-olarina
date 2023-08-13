package ru.otus.tester;

import ru.otus.util.ExceptionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.List;

public class DoubleTester extends Tester<Double> {

    private final BigDecimal epsilon = BigDecimal.valueOf(Math.pow(10, -7));

    public DoubleTester(List<Task<Double>> tasks, Path path) {
        super(tasks, path);
    }

    @Override
    boolean compare(String expected, Double actual) {
        if (actual == null && expected == null) {
            return true;
        }
        if (actual == null || expected == null) {
            return false;
        }
        BigDecimal expectedScaled = null;
        try {
            expectedScaled = new BigDecimal(expected).setScale(10, RoundingMode.HALF_UP);
        } catch (Throwable ex) {
            System.err.println(ExceptionUtils.getMessage(ex));
        }
        if (expectedScaled == null) {
            return false;
        }
        BigDecimal actualScaled = new BigDecimal(actual).setScale(10, RoundingMode.HALF_UP);
        return actualScaled.subtract(expectedScaled).abs().compareTo(epsilon) <= 0;
    }

}
