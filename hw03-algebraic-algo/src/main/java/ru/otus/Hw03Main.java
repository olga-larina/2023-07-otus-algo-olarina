package ru.otus;

import ru.otus.fibo.FiboMatrix;
import ru.otus.fibo.FiboSimpleIter;
import ru.otus.fibo.FiboSimpleRec;
import ru.otus.power.PowerBinary;
import ru.otus.power.PowerByMultiplyingIter;
import ru.otus.power.PowerByMultiplyingRec;
import ru.otus.power.PowerSimple;
import ru.otus.prime.*;
import ru.otus.tester.*;

import java.math.BigInteger;
import java.nio.file.Path;
import java.util.List;

public class Hw03Main {
    public static void main(String[] args) throws Exception {
        // алгоритмы возведения в степень
        List<Task<Double>> powers = List.of(
            new PowerSimple(),
            new PowerByMultiplyingRec(),
            new PowerByMultiplyingIter(),
            new PowerBinary()
        );
        Tester<Double> powersTester = new DoubleTester(powers, Path.of(Hw03Main.class.getClassLoader().getResource("3.Power").toURI()));
        powersTester.runTests();
        powersTester.stop();

        // алгоритмы поиска чисел Фибоначчи
        List<Task<BigInteger>> fibos = List.of(
            new FiboSimpleRec(),
            new FiboSimpleIter(),
//            new FiboGoldenRatio() // запускать отдельно для больших чисел, не реализовано прерывание
            new FiboMatrix()
        );
        Tester<BigInteger> fibosTester = new BigIntegerTester(fibos, Path.of(Hw03Main.class.getClassLoader().getResource("4.Fibo").toURI()));
        fibosTester.runTests();
        fibosTester.stop();

        // алгоритмы поиска простых чисел
        List<Task<Integer>> primes = List.of(
            new PrimeSimple(),
            new PrimeSimpleOptimal(),
            new PrimeEratosthene(),
            new PrimeEratostheneBitwise(),
            new PrimeEratostheneLinear()
        );
        Tester<Integer> primesTester = new IntTester(primes, Path.of(Hw03Main.class.getClassLoader().getResource("5.Primes").toURI()));
        primesTester.runTests();
        primesTester.stop();
    }
}