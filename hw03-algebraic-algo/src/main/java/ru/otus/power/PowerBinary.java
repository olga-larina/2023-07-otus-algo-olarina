package ru.otus.power;

import ru.otus.tester.Task;

import java.util.List;

/**
 * Middle. 12. Алгоритм возведения в степень через двоичное разложение показателя степени O(2LogN) = O(LogN)
 */
public class PowerBinary implements Task<Double> {

    @Override
    public Double run(List<String> data) {
        return powerBinary(Double.parseDouble(data.get(0)), Long.parseLong(data.get(1)));
    }

    @Override
    public String name() {
        return "Алгоритм возведения в степень через двоичное разложение показателя степени O(2LogN) = O(LogN)";
    }

    private double powerBinary(double a, long n) {
        if (a <= 0 || n < 0) {
            throw new IllegalArgumentException();
        }
        // Представить показатель степени n в двоичном виде
        String[] binaryN = Long.toBinaryString(n).split("");
        double result = 1.0;
        for (String m : binaryN) {
            // Если m == 1, то текущий результат возводится в квадрат и затем умножается на x
            // Если m == 0, то текущий результат просто возводится в квадрат
            result = result * result * (m.equals("1") ? a : 1.0);
        }
        return result;
    }

}
