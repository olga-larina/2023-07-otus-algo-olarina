package ru.otus;

import ru.otus.hashtable.HashTable;
import ru.otus.hashtable.QuadraticHashTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Hw13Main {

    private static final Random random = new Random();

    public static void main(String[] args) {
        testWithData();
    }

    public static void testWithData() {
//        HashTable<String, Integer> hashTable = new LinearHashTable<>();
        HashTable<String, Integer> hashTable = new QuadraticHashTable<>();

        int n = 100;
        int keyLength = 20;

        // генерация значений
        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        List<Integer> newValues = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            keys.add(randomAlphaNumericString(keyLength));
            values.add(random.nextInt(-5000, 5001));
            newValues.add(random.nextInt(-5000, 5001));
        }

        check(hashTable.isEmpty());
        check(hashTable.size() == 0);

        // добавление значений в хэш-таблицу, проверка, замена значения
        for (int i = 0; i < n; i++) {
            Integer oldValue = hashTable.put(keys.get(i), values.get(i));

            check(oldValue == null);
            check(Objects.equals(hashTable.get(keys.get(i)), values.get(i)));
            check(hashTable.contains(keys.get(i)));
            check(hashTable.size() == i + 1);
            check(!hashTable.isEmpty());

            oldValue = hashTable.put(keys.get(i), newValues.get(i));

            check(Objects.equals(oldValue, values.get(i)));
            check(Objects.equals(hashTable.get(keys.get(i)), newValues.get(i)));
            check(hashTable.contains(keys.get(i)));
            check(hashTable.size() == i + 1);
            check(!hashTable.isEmpty());
        }

        // удаляем несуществующее значение (например, другой длины - у нас все ключи длины 20)
        String notExistingKey = randomAlphaNumericString(keyLength + 1);
        check(hashTable.remove(notExistingKey) == null);
        check(hashTable.get(notExistingKey) == null);
        check(!hashTable.contains(notExistingKey));
        check(hashTable.size() == n);

        // удаление значений из хэш-таблицы
        for (int i = n - 1; i >= 0; i--) {
            Integer oldValue = hashTable.remove(keys.get(i));

            check(Objects.equals(oldValue, newValues.get(i)));
            check(hashTable.get(keys.get(i)) == null);
            check(!hashTable.contains(keys.get(i)));
            check(hashTable.size() == i);
            if (i > 0) {
                check(!hashTable.isEmpty());
            } else {
                check(hashTable.isEmpty());
            }
        }

        check(hashTable.isEmpty());
        check(hashTable.size() == 0);
    }

    static void check(boolean value) {
        if (!value) {
            throw new IllegalStateException("Ошибка!");
        }
    }

    static String randomAlphaNumericString(int length) {
        int leftLimit = 48; // '0'
        int rightLimit = 122; // 'z'
        return random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

}