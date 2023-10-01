package ru.otus.hashtable;

/**
 * Хэш-таблица
 */
public interface HashTable<K, V> {

    /**
     * Вставка элемента
     * @param key ключ
     * @param value значение
     * @return предыдущее значение
     */
    V put(K key, V value);

    /**
     * Проверка элемента на существование
     * @param key ключ
     * @return присутствует ли элемент в дереве
     */
    boolean contains(K key);

    /**
     * Получение элемента
     * @param key ключ
     * @return значение или null
     */
    V get(K key);

    /**
     * Удаление элемента
     * @param key ключ
     * @return предыдущее значение
     */
     V remove(K key);

    /**
     * Получить размер
     * @return количество элементов
     */
    int size();

    /**
     * Является ли пустой
     * @return true, если пустая; иначе - false
     */
    boolean isEmpty();
}
