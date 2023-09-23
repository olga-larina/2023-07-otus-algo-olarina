package ru.otus.tree;

/**
 * Дерево
 */
public interface Tree<K extends Comparable<K>, V> {

    /**
     * Вставка элемента
     * @param key ключ
     * @param value значение
     */
    void insert(K key, V value);

    /**
     * Поиск элемента
     * @param key ключ
     * @return присутствует ли элемент в дереве
     */
    boolean search(K key);

    /**
     * Получение элемента
     * @param key ключ
     * @return значение или null
     */
    V get(K key);

    /**
     * Удаление элемента
     * @param key ключ
     */
     void remove(K key);
}
