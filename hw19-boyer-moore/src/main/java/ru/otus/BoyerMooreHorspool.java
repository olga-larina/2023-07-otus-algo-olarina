package ru.otus;

import java.util.Arrays;

/**
 * Алгоритм Бойера-Мура-Хорспула
 */
public class BoyerMooreHorspool implements PatternSearch {
    @Override
    public int find(String text, String pattern) {
        if (pattern.isEmpty()) {
            return 0;
        }
        int[] shifts = shifts(pattern);
        int last = pattern.length() - 1; // индекс последнего символа в шаблоне
        int t = 0;
        int p;
        while (t <= text.length() - pattern.length()) {
            p = last;
            while (p >= 0 && text.charAt(t + p) == pattern.charAt(p)) {
                p--;
            }
            if (p == -1) {
                return t;
            }
            t += (last - shifts[text.charAt(t + last)]);
        }
        return -1;
    }

    /**
     * Позиции самого правого вхождения символа в шаблон
     * Не учитываем самый последний символ (чтобы не получился в итоге сдвиг на 0 символов)
     */
    private int[] shifts(String pattern) {
        int[] shift = new int[ALPHABET_SIZE];
        Arrays.fill(shift, -1);
        for (int i = 0; i < pattern.length() - 1; i++) {
            shift[pattern.charAt(i)] = i;
        }
        return shift;
    }

    public static void main(String[] args) {
        PatternSearch patternSearch = new BoyerMooreHorspool();
        System.out.println(patternSearch.find("simplestrings", "string")); // 6
        System.out.println(patternSearch.find("simplestrins", "string")); // -1
        System.out.println(patternSearch.find("stsingstring", "string")); // 6
    }
}
