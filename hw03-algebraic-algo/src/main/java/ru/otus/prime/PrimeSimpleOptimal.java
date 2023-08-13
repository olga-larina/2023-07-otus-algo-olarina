package ru.otus.prime;

import ru.otus.tester.Task;

import java.util.Arrays;
import java.util.List;

/**
 * Middle. 15. Алгоритм поиска простых чисел с оптимизациями поиска и делением только на простые числа, O(N * Sqrt(N) / Ln (N))
 */
public class PrimeSimpleOptimal implements Task<Integer> {

    @Override
    public Integer run(List<String> data) throws InterruptedException {
        return primeSimpleOptimal(Integer.parseInt(data.get(0)));
    }

    @Override
    public String name() {
        return "Алгоритм поиска простых чисел с оптимизациями поиска и делением только на простые числа, O(N * Sqrt(N) / Ln (N))";
    }

    private int primeSimpleOptimal(int n) throws InterruptedException {
        if (n < 2) {
            return 0;
        }
        int count = 0;
        boolean[] primes = new boolean[n + 1];
        Arrays.fill(primes, true);
        primes[0] = false;
        primes[1] = false;
        for (int i = 2; i <= n; i++) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Too long execution");
            }
            boolean isPrime = isPrime(i, primes);
            primes[i] = isPrime;
            if (isPrime) {
                count++;
            }
        }
        return count;
    }

    private boolean isPrime(int p, boolean[] primes) {
        if (p == 2) {
            return true;
        }
        if (p % 2 == 0) {
            return false;
        }
        int sqrt = (int) Math.sqrt(p);
        for (int j = 3; j <= sqrt; j += 2) {
            if (primes[j] && p % j == 0) {
                return false;
            }
        }
        return true;
    }
}
