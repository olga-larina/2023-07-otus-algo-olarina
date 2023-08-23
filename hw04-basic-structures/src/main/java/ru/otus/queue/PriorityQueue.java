package ru.otus.queue;

/**
 * Очередь с приоритетом
 */
public interface PriorityQueue<T> {

    /**
     * Поместить элемент в очередь
     * @param priority чем меньше значение, тем выше приоритет
     * @param item элемент
     */
    void enqueue(int priority, T item);

    /**
     * Выбрать (и удалить) элемент из очереди
     * @return элемент с наименьшим значением priority. Если элементов с одинаковым priority несколько, то первый пришедший.
     */
    T dequeue();
}
