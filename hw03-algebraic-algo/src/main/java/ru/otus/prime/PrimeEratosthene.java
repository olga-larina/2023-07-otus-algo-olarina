package ru.otus.prime;

import ru.otus.tester.Task;

import java.util.BitSet;
import java.util.List;

/**
 * Middle. 16. Алгоритм "Решето Эратосфена" для быстрого поиска простых чисел O(N Log Log N)
 */
public class PrimeEratosthene implements Task<Integer> {

    @Override
    public Integer run(List<String> data) {
        return primeEratosthene(Integer.parseInt(data.get(0)));
    }

    @Override
    public String name() {
        return "Алгоритм \"Решето Эратосфена\" для быстрого поиска простых чисел O(N Log Log N)";
    }

    private int primeEratosthene(int n) {
        if (n < 2) {
            return 0;
        }
        int count = 0;
        BitSet primes = new BitSet(n + 1);
        primes.flip(0, n + 1);
        int sqrtN = (int) Math.sqrt(n);
        for (int i = 2; i <= n; i++) {
            if (primes.get(i)) {
                count++;
                if (i <= sqrtN) {
                    for (int j = i * i; j <= n; j += i) {
                        primes.set(j, false);
                    }
                }
            }
        }
        return count;
    }
}
