package ru.otus;

import java.util.Arrays;

/**
 * Алгоритм Бойера-Мура
 */
public class BoyerMoore implements PatternSearch {
    @Override
    public int find(String text, String pattern) {
        if (pattern.isEmpty()) {
            return 0;
        }
        int[] badCharacter = badCharacter(pattern); // сдвиги для эвристики "плохого символа" (ищем по коду символа)
        int[] suffix = suffixes(pattern); // сдвиги для эвристики "хорошего суффикса" (ищем по количеству символов в суффиксе)
        int last = pattern.length() - 1; // индекс последнего символа в шаблоне
        int t = last;
        int p;
        while (t < text.length()) {
            p = last;
            while (p >= 0 && text.charAt(t) == pattern.charAt(p)) {
                t--;
                p--;
            }
            if (p == -1) {
                return t + 1;
            }
            t += Math.max(badCharacter[text.charAt(t)], suffix[last - p]);
        }
        return -1;
    }

    /**
     * Сдвиги для символов (эвристика "плохой символ")
     * Рассчитываются на основе позиций самого правого вхождения символа в шаблон
     */
    private int[] badCharacter(String pattern) {
        int[] shift = new int[ALPHABET_SIZE];
        int m = pattern.length();
        Arrays.fill(shift, m);
        for (int i = 0; i < m; i++) {
            shift[pattern.charAt(i)] = m - 1 - i;
        }
        return shift;
    }

    /**
     * Сдвиги для суффиксов (эвристика "хороший суффикс")
     */
    private int[] suffixes(String pattern) {
        int m = pattern.length();
        int[] table = new int[m];
        int lastPrefixPosition = m;
        // проверка суффиксов на совпадение с префиксами
        for (int p = m - 1; p >= 0; p--) {
            if (isPrefix(pattern, p + 1)) {
                lastPrefixPosition = p + 1; // если суффикс, начинающийся с позиции p, является префиксом, то запоминаем
            }
            table[m - 1 - p] = lastPrefixPosition - p + m - 1;
        }
        // вычисление сдвигов на основе длины суффиксов
        for (int p = 0; p < m - 1; p++) {
            int slen = suffixLength(pattern, p);
            table[slen] = m - 1 - p + slen;
        }
        return table;
    }

    /**
     * Является ли суффикс, начинающийся с индекса p, префиксом шаблона
     */
    private boolean isPrefix(String pattern, int p) {
        for (int j = 0; j < pattern.length() - p; j++) {
            if (pattern.charAt(j) != pattern.charAt(p + j)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает для позиции p длину максимальной подстроки, которая является суффиксом шаблона pattern
     */
    private int suffixLength(String pattern, int p) {
        int len = 0;
        int i = p;
        int j = pattern.length() - 1;
        while (i >= 0 && pattern.charAt(i) == pattern.charAt(j)) {
            len++;
            i--;
            j--;
        }
        return len;
    }

    public static void main(String[] args) {
        PatternSearch patternSearch = new BoyerMoore();
        System.out.println(patternSearch.find("simplestrings", "string")); // 6
        System.out.println(patternSearch.find("simplestrins", "string")); // -1
        System.out.println(patternSearch.find("stsingstring", "string")); // 6
    }
}
