package ru.otus.power;

import ru.otus.tester.Task;

import java.util.List;

/**
 * Middle. 11. Алгоритм (рекурсивный) возведения в степень через домножение O(N/2+LogN) = O(N)
 */
public class PowerByMultiplyingRec implements Task<Double> {

    @Override
    public Double run(List<String> data) {
        return powerByMultiplyingRecursive(Double.parseDouble(data.get(0)), Long.parseLong(data.get(1)));
    }

    @Override
    public String name() {
        return "Алгоритм (рекурсивный) возведения в степень через домножение O(N/2+LogN) = O(N)";
    }

    private double powerByMultiplyingRecursive(double a, long n) {
        if (a <= 0 || n < 0) {
            throw new IllegalArgumentException();
        }
        return powerByMultiplyingRecursive1(a, n);
    }

    private double powerByMultiplyingRecursive1(double a, long n) {
        if (n == 0) {
            return 1.0;
        }
        if (n == 1) {
            return a;
        }
        double halfResult = powerByMultiplyingRecursive1(a, n / 2);
        double result = halfResult * halfResult;
        if (n % 2 == 0) {
            return result;
        } else {
            return result * a;
        }
    }

}
