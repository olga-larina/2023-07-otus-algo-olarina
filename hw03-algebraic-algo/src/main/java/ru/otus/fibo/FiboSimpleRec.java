package ru.otus.fibo;

import ru.otus.tester.Task;

import java.math.BigInteger;
import java.util.List;

/**
 * Junior. 02. Рекурсивный O(2^N) алгоритм поиска чисел Фибоначчи
 */
public class FiboSimpleRec implements Task<BigInteger> {

    @Override
    public BigInteger run(List<String> data) throws InterruptedException {
        return fiboSimpleRecursive(Integer.parseInt(data.get(0)));
    }

    @Override
    public String name() {
        return "Рекурсивный O(2^N) алгоритм поиска чисел Фибоначчи";
    }

    private BigInteger fiboSimpleRecursive(long n) throws InterruptedException {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        return fiboSimpleRecursive1(n);
    }

    private BigInteger fiboSimpleRecursive1(long n) throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Too long execution");
        }
        if (n <= 1) {
            return BigInteger.valueOf(n);
        }
        return fiboSimpleRecursive1(n - 1).add(fiboSimpleRecursive1(n - 2));
    }

}
