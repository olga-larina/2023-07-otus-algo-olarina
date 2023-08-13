package ru.otus.prime;

import ru.otus.tester.Task;

import java.util.List;

/**
 * Junior. 03. Алгоритм поиска количества простых чисел через перебор делителей, O(N^2)
 */
public class PrimeSimple implements Task<Integer> {

    @Override
    public Integer run(List<String> data) throws InterruptedException {
        return primeSimple(Integer.parseInt(data.get(0)));
    }

    @Override
    public String name() {
        return "Алгоритм поиска количества простых чисел через перебор делителей, O(N^2)";
    }

    private int primeSimple(int n) throws InterruptedException {
        if (n < 2) {
            return 0;
        }
        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Too long execution");
            }
            if (isPrime(i)) {
                count++;
            }
        }
        return count;
    }

    private boolean isPrime(int p) {
        if (p == 2) {
            return true;
        }
        if (p % 2 == 0) {
            return false;
        }
        for (int j = 3; j < p / 2; j += 2) {
            if (p % j == 0) {
                return false;
            }
        }
        return true;
    }
}
