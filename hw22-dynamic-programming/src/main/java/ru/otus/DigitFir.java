package ru.otus;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Цифровая ёлочка
 */
public class DigitFir {

    /**
     * Поиск максимальной суммы от верха ели до подножья
     * @param n высота ели
     * @param fir ель, 1 строка - 1 цифра, 2 строка - 2 цифры, n-я строка - n цифр
     * @return максимальная сумма
     */
    public int solve(int n, int[][] fir) {
        for (int r = n - 2; r >= 0; r--) { // по строкам, начиная с предпоследней
            for (int c = 0; c <= r; c++) { // по значениям
                // накопленный итог
                // берём текущее значение и максимальное в ячейках снизу слева и снизу справа
                fir[r][c] += Math.max(fir[r + 1][c], fir[r + 1][c + 1]);
            }
        }
        return fir[0][0];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(Pattern.compile("[ \n]*"));
        int n = scanner.nextInt();
        int[][] fir = new int[n][];
        for (int r = 0; r < n; r++) {
            fir[r] = new int[r + 1];
            for (int c = 0; c <= r; c++) {
                fir[r][c] = scanner.nextInt();
            }
        }
        DigitFir digitFir = new DigitFir();
        System.out.println(digitFir.solve(n, fir));

//        System.out.println(new DigitFir().solve(4, new int[][] {
//            new int[] { 1 },
//            new int[] { 2, 3 },
//            new int[] { 4, 5, 6 },
//            new int[] { 9, 8, 0, 3 }
//        })); // 17
    }
}
