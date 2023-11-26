package ru.otus;

/**
 * Алгоритм поиска подстроки с использованием конечного автомата
 */
public class StateMachine implements PatternSearch {

    @Override
    public int find(String text, String pattern) {
        if (pattern.isEmpty()) {
            return 0;
        }
        int[][] delta = createDelta(pattern);
        int q = 0;
        for (int i = 0; i < text.length(); i++) {
            q = delta[q][text.charAt(i) - ALPHABET.charAt(0)];
            if (q == pattern.length()) {
                return i - pattern.length() + 1;
            }
        }
        return -1;
    }

    private int[][] createDelta(String pattern) {
        int[][] delta = new int[pattern.length()][ALPHABET.length()];
        for (int q = 0; q < pattern.length(); q++) {
            for (char c : ALPHABET.toCharArray()) {
                String line = left(pattern, q) + c;
                int k = q + 1;
                while (!left(pattern, k).equals(right(line, k))) {
                    k--;
                }
                delta[q][c - ALPHABET.charAt(0)] = k;
            }
        }
        return delta;
    }

    public static void main(String[] args) {
        PatternSearch stateMachine = new StateMachine();
        System.out.println(stateMachine.find("simplestrings", "string")); // 6
        System.out.println(stateMachine.find("simplestrins", "string")); // -1
        System.out.println(stateMachine.find("stsingstring", "string")); // 6
        System.out.println(stateMachine.find("aabaabaabaaaba", "aabaabaaaba")); // 3
    }
}
