package ru.otus.prime;

import ru.otus.tester.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Middle. 18. Алгоритм "Решето Эратосфена" со сложностью O(N)
 * https://habr.com/ru/articles/452388/
 */
public class PrimeEratostheneLinear implements Task<Integer> {

    @Override
    public Integer run(List<String> data) {
        return primeEratostheneLinear(Integer.parseInt(data.get(0)));
    }

    @Override
    public String name() {
        return "Алгоритм \"Решето Эратосфена\" со сложностью O(N)";
    }

    private int primeEratostheneLinear(int n) {
        if (n < 2) {
            return 0;
        }
        int[] lp = new int[n + 1]; // минимальный делитель числа
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (lp[i] == 0) {
                lp[i] = i;
                primes.add(i);
            }
            for (Integer prime : primes) {
                if (prime <= lp[i] && prime * i <= n) {
                    lp[prime * i] = prime;
                } else {
                    break;
                }
            }
        }
        return primes.size();
    }
}
