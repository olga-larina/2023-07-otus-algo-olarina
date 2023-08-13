package ru.otus.power;

import ru.otus.tester.Task;

import java.util.List;

/**
 * Middle. 11. Алгоритм (итеративный) возведения в степень через домножение O(N/2+LogN) = O(N)
 */
public class PowerByMultiplyingIter implements Task<Double> {

    @Override
    public Double run(List<String> data) {
        return powerByMultiplyingIterative(Double.parseDouble(data.get(0)), Long.parseLong(data.get(1)));
    }

    @Override
    public String name() {
        return "Алгоритм (итеративный) возведения в степень через домножение O(N/2+LogN) = O(N)";
    }

    private double powerByMultiplyingIterative(double a, long n) {
        if (a <= 0 || n < 0) {
            throw new IllegalArgumentException();
        }
        double result = 1.0;
        while (n > 0) {
            if (n % 2 == 1) {
                result = result * a;
            }
            n = n / 2;
            a = a * a;
        }
        return result;
    }

}
