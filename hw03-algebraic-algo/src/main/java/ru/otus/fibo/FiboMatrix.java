package ru.otus.fibo;

import ru.otus.matrix.Matrix2D;
import ru.otus.tester.Task;

import java.math.BigInteger;
import java.util.List;

/**
 * Middle. 14. Алгоритм поиска чисел Фибоначчи O(LogN) через умножение матриц
 */
public class FiboMatrix implements Task<BigInteger> {

    @Override
    public BigInteger run(List<String> data) {
        return fiboMatrix(Integer.parseInt(data.get(0)));
    }

    @Override
    public String name() {
        return "Алгоритм поиска чисел Фибоначчи O(LogN) через умножение матриц";
    }

    private BigInteger fiboMatrix(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (n == 0) {
            return BigInteger.ZERO;
        }
        Matrix2D resultMatrix = Matrix2D.power(n - 1);
        return resultMatrix.elementAt(0, 0);
    }

}
