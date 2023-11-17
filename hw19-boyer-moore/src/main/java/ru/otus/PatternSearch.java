package ru.otus;

public interface PatternSearch {

    int ALPHABET_SIZE = 128;  // рассматриваем символы с ASCII кодами 0-127

    /**
     * Поиск шаблона в строке
     * @param text строка
     * @param pattern шаблон
     * @return стартовый индекс шаблона в строке или -1
     */
    int find(String text, String pattern);
}
