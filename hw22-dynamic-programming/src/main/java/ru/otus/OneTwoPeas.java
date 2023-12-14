package ru.otus;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Раз / два горох
 */
public class OneTwoPeas {

    /**
     * Рассчитать a/b+c/d и сократить дробь
     * @param one первая дробь (a/b от общего количества)
     * @param two вторая дробь (c/d от общего количества)
     * @return несократимая дробь a/b+c/d от общего количества
     */
    public Fraction solve(Fraction one, Fraction two) {
        int x = one.numerator * two.denominator + one.denominator * two.numerator; // числитель результата
        int y = one.denominator * two.denominator; // знаменатель результата
        int gcd = gcd(x, y); // НОД
        return new Fraction(x / gcd, y / gcd); // сокращаем числитель и знаменатель, возвращаем несократимую дробь
    }

    /**
     * Наибольший общий делитель
     */
    private int gcd(int x, int y) {
        if (x == y) {
            return x;
        }
        if (x == 0) {
            return y;
        }
        if (y == 0) {
            return x;
        }
        if (isEven(x) && isEven(y)) {
            return gcd(x >> 1, y >> 1) << 1;
        }
        if (isEven(x)) { // && isOdd(y)
            return gcd(x >> 1, y);
        }
        if (isEven(y)) { // && isOdd(x)
            return gcd(x, y >> 1);
        }
        // isOdd(x) && isOdd(y)
        if (x > y) {
            return gcd((x - y) >> 1, y);
        } else {
            return gcd(x, (y - x) >> 1);
        }
    }

    /**
     * Является ли чётным
     */
    private boolean isEven(int n) {
        return (n & 1) == 0;
    }

    /**
     * @param numerator   числитель
     * @param denominator знаменатель
     */
    public record Fraction(int numerator, int denominator) {
        @Override
        public String toString() {
            return String.format("%d/%d", numerator, denominator);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(Pattern.compile("[/+\n]"));
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        int d = scanner.nextInt();
        OneTwoPeas oneTwoPeas = new OneTwoPeas();
        Fraction result = oneTwoPeas.solve(new Fraction(a, b), new Fraction(c, d));
        System.out.println(result);

//        System.out.println(new OneTwoPeas().solve(new Fraction(2, 100), new Fraction(3, 100))); // 1/20
    }
}
