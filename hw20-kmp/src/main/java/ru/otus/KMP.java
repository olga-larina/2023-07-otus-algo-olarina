package ru.otus;

import java.util.Arrays;

/**
 * Алгоритм поиска подстроки Кнута-Морриса-Пратта
 */
public class KMP implements PatternSearch {

    private final char notExistingChar = '@';

    private final boolean slow;

    public KMP() {
        this(false);
    }

    public KMP(boolean slow) {
        this.slow = slow;
    }

    @Override
    public int find(String text, String pattern) {
        if (pattern.isEmpty()) {
            return 0;
        }
        int patternLen = pattern.length();
        String line = pattern + notExistingChar + text;
        int[] pi = slow ? createPiSlow(line) : createPiFast(line);
        for (int i = patternLen + 1; i < pi.length; i++) {
            if (pi[i] == pattern.length()) {
                return i - (patternLen + 1) - patternLen;
            }
        }
        return -1;
    }

    /**
     * Медленный вариант вычисления префикс-функции
     */
    protected int[] createPiSlow(String pattern) {
        int[] pi = new int[pattern.length() + 1];
        for (int q = 0; q <= pattern.length(); q++) {
            String line = left(pattern, q);
            for (int len = 0; len < q; len++) {
                if (left(line, len).equals(right(line, len))) {
                    pi[q] = len;
                }
            }
        }
        return pi;
    }

    /**
     * Быстрый вариант вычисления префикс-функции
     */
    protected int[] createPiFast(String pattern) {
        int[] pi = new int[pattern.length() + 1];
        pi[1] = 0;
        for (int q = 1; q < pattern.length(); q++) {
            int len = pi[q];
            while (len > 0 && pattern.charAt(len) != pattern.charAt(q)) {
                len = pi[len];
            }
            if (pattern.charAt(len) == pattern.charAt(q)) {
                len++;
            }
            pi[q + 1] = len;
        }
        return pi;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new KMP().createPiSlow("aabaabaaaba"))); // 0 0 1 0 1 2 3 4 5 2 3 4
        System.out.println(Arrays.toString(new KMP().createPiFast("aabaabaaaba"))); // 0 0 1 0 1 2 3 4 5 2 3 4
        for (boolean slow : new boolean[] { true, false }) {
            System.out.printf("KMP slow=%b\n", slow);
            KMP kmp = new KMP(slow);
            System.out.println(kmp.find("simplestrings", "string")); // 6
            System.out.println(kmp.find("simplestrins", "string")); // -1
            System.out.println(kmp.find("stsingstring", "string")); // 6
            System.out.println(kmp.find("aabaabaabaaaba", "aabaabaaaba")); // 3
        }
    }
}
