package ru.otus.power;

import ru.otus.tester.Task;

import java.util.List;

/**
 * Junior. 01. Итеративный O(N) алгоритм возведения числа в степень
 */
public class PowerSimple implements Task<Double> {

    @Override
    public Double run(List<String> data) throws InterruptedException {
        return powerSimple(Double.parseDouble(data.get(0)), Long.parseLong(data.get(1)));
    }

    @Override
    public String name() {
        return "Итеративный O(N) алгоритм возведения числа в степень";
    }

    private double powerSimple(double a, long n) throws InterruptedException {
        if (a <= 0 || n < 0) {
            throw new IllegalArgumentException();
        }
        double result = 1.0;
        for (int i = 0; i < n; i++) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Too long execution");
            }
            result = result * a;
        }
        return result;
    }

}
