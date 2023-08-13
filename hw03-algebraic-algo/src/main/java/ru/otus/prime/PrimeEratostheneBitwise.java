package ru.otus.prime;

import ru.otus.tester.Task;

import java.util.List;

/**
 * Middle. 17. Алгоритм "Решето Эратосфена" с оптимизацией памяти, используя битовую матрицу, сохраняя по 32 значения в одном int; хранить биты только для нечётных чисел
 * https://www.geeksforgeeks.org/bitwise-sieve/
 */
public class PrimeEratostheneBitwise implements Task<Integer> {

    @Override
    public Integer run(List<String> data) {
        return primeEratostheneBitwise(Integer.parseInt(data.get(0)));
    }

    @Override
    public String name() {
        return "Алгоритм \"Решето Эратосфена\" с оптимизацией памяти, используя битовую матрицу, сохраняя по 32 значения в одном int; хранить биты только для нечётных чисел";
    }

    private int primeEratostheneBitwise(int n) {
        if (n < 2) {
            return 0;
        }
        int count = 1;
        int[] primes = new int[n / 64 + 1];
        int sqrtN = (int) Math.sqrt(n);
        // пропускаем чётные
        for (int i = 3; i <= n; i += 2) {
            if (isPrime(primes, i)) {
                count++;
                if (i <= sqrtN) {
                    for (int j = i * i, k = i << 1; j <= n; j += k) {
                        setNotPrime(primes, j);
                    }
                }
            }
        }
        return count;
    }

    /**
     * Проверка, установлен ли бит
     * Используя primes[x / 64], находим слот
     * Чтобы найти битовое значение, делим x на 2 и берём значение по модулю 32
     */
    private boolean isPrime(int[] primes, int x) {
        return (primes[x / 64] & (1 << ((x >> 1) & 31))) == 0;
    }

    /**
     * Установка бита (аналогично isPrime)
     */
    private void setNotPrime(int[] primes, int x) {
        primes[x / 64] |= (1 << ((x >> 1) & 31));
    }
}
