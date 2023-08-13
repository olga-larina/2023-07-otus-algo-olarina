package ru.otus.fibo;

import ru.otus.tester.Task;

import java.math.BigInteger;
import java.util.List;

/**
 * Junior. 02. Итеративный O(N) алгоритм поиска чисел Фибоначчи
 */
public class FiboSimpleIter implements Task<BigInteger> {

    @Override
    public BigInteger run(List<String> data) throws InterruptedException {
        return fiboSimpleIterative(Integer.parseInt(data.get(0)));
    }

    @Override
    public String name() {
        return "Итеративный O(N) алгоритм поиска чисел Фибоначчи";
    }

    private BigInteger fiboSimpleIterative(long n) throws InterruptedException {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (n == 0) {
            return BigInteger.ZERO;
        }
        BigInteger cur = BigInteger.ONE;
        BigInteger prev = BigInteger.ZERO;
        BigInteger prevPrev;
        for (int i = 1; i < n; i++) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Too long execution");
            }
            prevPrev = prev;
            prev = cur;
            cur = prevPrev.add(prev);
        }
        return cur;
    }

}
