package ru.otus;

import ru.otus.trie.Dictionary;
import ru.otus.trie.TrieDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Hw14Main {

    private static final Random random = new Random();

    public static void main(String[] args) {
        testWithData();
    }

    public static void testWithData() {
        Dictionary<String, Integer> dictionary = new TrieDictionary<>();

        int n = 100;
        int keyLength = 10;

        // генерация значений
        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        List<Integer> newValues = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            keys.add(randomAlphaString(keyLength));
            values.add(random.nextInt(-5000, 5001));
            newValues.add(random.nextInt(-5000, 5001));
        }

        check(dictionary.isEmpty());
        check(dictionary.size() == 0);

        // добавление значений в хэш-таблицу, проверка, замена значения
        for (int i = 0; i < n; i++) {
            Integer oldValue = dictionary.put(keys.get(i), values.get(i));

            check(oldValue == null);
            check(Objects.equals(dictionary.get(keys.get(i)), values.get(i)));
            check(dictionary.contains(keys.get(i)));
            check(dictionary.size() == i + 1);
            check(!dictionary.isEmpty());

            oldValue = dictionary.put(keys.get(i), newValues.get(i));

            check(Objects.equals(oldValue, values.get(i)));
            check(Objects.equals(dictionary.get(keys.get(i)), newValues.get(i)));
            check(dictionary.contains(keys.get(i)));
            check(dictionary.size() == i + 1);
            check(!dictionary.isEmpty());
        }

        // удаляем несуществующее значение (например, другой длины - у нас все ключи длины 20)
        String notExistingKey = randomAlphaString(keyLength + 1);
        check(dictionary.remove(notExistingKey) == null);
        check(dictionary.get(notExistingKey) == null);
        check(!dictionary.contains(notExistingKey));
        check(dictionary.size() == n);

        // удаление значений из хэш-таблицы
        for (int i = n - 1; i >= 0; i--) {
            Integer oldValue = dictionary.remove(keys.get(i));

            check(Objects.equals(oldValue, newValues.get(i)));
            check(dictionary.get(keys.get(i)) == null);
            check(!dictionary.contains(keys.get(i)));
            check(dictionary.size() == i);
            if (i > 0) {
                check(!dictionary.isEmpty());
            } else {
                check(dictionary.isEmpty());
            }
        }

        check(dictionary.isEmpty());
        check(dictionary.size() == 0);
    }

    static void check(boolean value) {
        if (!value) {
            throw new IllegalStateException("Ошибка!");
        }
    }

    static String randomAlphaString(int length) {
        int leftLimit = 97; // 'a'
        int rightLimit = 122; // 'z'
        return random.ints(leftLimit, rightLimit + 1)
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

}