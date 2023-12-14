package ru.otus;

import java.util.Scanner;

/**
 * Пятью восемь
 */
public class FiveEight {

    /**
     * Поиск количества N-значных чисел, которые можно составить, используя цифры 5 и 8, при условии, что 3 одинаковые цифры не стоят рядом
     * @param n количество цифр числа
     * @return количество чисел
     */
    public int solve(int n) {
        // начальные значения (для n = 1)
        int n5 = 1; // числа, заканчивающиеся на 5 (не 55)
        int n55 = 0; // числа, заканчивающиеся на 55
        int n8 = 1; // числа, заканчивающиеся на 8 (не 88)
        int n88 = 0; // числа, заканчивающиеся на 88
        int t5, t55, t8, t88; // временные переменные
        for (int i = 2; i <= n; i++) {
            t5 = n8 + n88;
            t8 = n5 + n55;
            t55 = n5;
            t88 = n8;
            n5 = t5;
            n8 = t8;
            n55 = t55;
            n88 = t88;
        }
        return n5 + n55 + n8 + n88;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        FiveEight fiveEight = new FiveEight();
        System.out.println(fiveEight.solve(n));

//        System.out.println(new FiveEight().solve(3)); // 6
    }
}
