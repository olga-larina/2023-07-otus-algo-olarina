package ru.otus.fibo;

import ru.otus.tester.Task;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

/**
 * Middle. 13. Алгоритм поиска чисел Фибоначчи по формуле золотого сечения
 * Запускать отдельно для больших чисел, не реализовано прерывание
 * Неточный алгоритм
 */
public class FiboGoldenRatio implements Task<BigInteger> {

    @Override
    public BigInteger run(List<String> data) {
        return fiboGoldenRatio(Integer.parseInt(data.get(0)));
    }

    @Override
    public String name() {
        return "Алгоритм поиска чисел Фибоначчи по формуле золотого сечения";
    }

    private BigInteger fiboGoldenRatio(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        double sqrt5 = Math.sqrt(5);
        BigDecimal phi = BigDecimal.valueOf((1 + sqrt5) / 2.0);
        BigDecimal result = phi.pow(n).divide(BigDecimal.valueOf(sqrt5), 0, RoundingMode.HALF_UP).add(BigDecimal.valueOf(0.5));
        return result.toBigInteger();
    }

}
