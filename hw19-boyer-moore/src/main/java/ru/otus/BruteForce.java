package ru.otus;

/**
 * Алгоритм поиска подстроки полным перебором
 */
public class BruteForce implements PatternSearch {
    @Override
    public int find(String text, String pattern) {
        if (pattern.isEmpty()) {
            return 0;
        }
        int t = 0;
        int p;
        while (t <= text.length() - pattern.length()) {
            p = 0;
            while (p < pattern.length() && text.charAt(t + p) == pattern.charAt(p)) {
                p++;
            }
            if (p == pattern.length()) {
                return t;
            }
            t++;
        }
        return -1;
    }

    public static void main(String[] args) {
        PatternSearch patternSearch = new BruteForce();
        System.out.println(patternSearch.find("simplestrings", "string")); // 6
        System.out.println(patternSearch.find("simplestrins", "string")); // -1
        System.out.println(patternSearch.find("stsingstring", "string")); // 6
    }
}
