package ru.otus;

import java.util.List;

public class Lucky implements Task {

    @Override
    public String run(List<String> data) {
        long count = getLuckyCount(Integer.parseInt(data.get(0)));
        return Long.toString(count);
    }

    public long getLuckyCount(int n) {
        if (n <= 0) {
            return 0;
        }
        // массив сумм; размер - по количеству возможных сумм (1 - это 0, 9 * n - это 1,2,...,9,10,...9*n)
        long[] sums = new long[1 + 9 * n];
        long[] sumsTmp = new long[1 + 9 * n];
        // начальный случай - для однозначного числа
        for (int j = 0; j < 10; j++) {
            sums[j] = 1;
        }
        // последующие случаи - сумма предыдущего случая со смещениями для каждой цифры
        for (int i = 2; i <= n; i++) { // кратность
            System.arraycopy(sums, 0, sumsTmp, 0, sums.length);
            for (int s = 0; s < sumsTmp.length; s++) { // для каждой суммы s
                long sum = 0;
                for (int j = 0; j < 10 && s - j >= 0; j++) { // число заканчивается на цифру j
                    sum += sumsTmp[s - j];
                }
                sums[s] = sum;
            }
        }
        // возведение в квадрат и суммирование
        long count = 0;
        for (long sum : sums) {
            count += sum * sum;
        }
        return count;
    }

}
