package ru.otus;

public interface PatternSearch {

    String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Поиск шаблона в строке
     * @param text строка
     * @param pattern шаблон
     * @return стартовый индекс шаблона в строке или -1
     */
    int find(String text, String pattern);

    default String left(String txt, int x) {
        return txt.substring(0, x);
    }

    default String right(String txt, int x) {
        return txt.substring(txt.length() - x);
    }

}
