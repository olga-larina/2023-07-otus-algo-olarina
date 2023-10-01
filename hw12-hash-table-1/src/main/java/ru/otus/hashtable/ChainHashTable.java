package ru.otus.hashtable;

/**
 * Хэш-таблица, использующая метод цепочек
 * null-ключи и null-значения не поддерживаются
 * Рехэширование при удалении элементов не реализовано
 */
public class ChainHashTable<K, V> implements HashTable<K, V> {

    private static final int DEFAULT_CAPACITY = 17;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * реальное количество элементов
     */
    private int size;

    /**
     * размер хэш-таблицы
     */
    private int capacity;

    /**
     * при каком уровне заполнения таблицы проводить рехэширование
     */
    private final float loadFactor;

    /**
     * следующий capacity * loadFactor
     */
    private int threshold;

    /**
     * массив цепочек
     */
    private Node<K, V>[] buckets;

    public ChainHashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }


    @SuppressWarnings({"unchecked"})
    public ChainHashTable(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.threshold = threshold();
        this.buckets = (Node<K, V>[]) new Node[capacity];
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key / Value should be not null");
        }
        // считаем индекс в массиве
        int hash = hash(key);
        Node<K, V> oldNode = buckets[hash];
        // проходимся по списку, ищем, есть ли там уже добавляемый ключ
        // если есть, то сохраняем старое значение (и потом возвращаем) и перезаписываем
        V prevValue = null;
        Node<K, V> node = oldNode;
        while (node != null) {
            if (key.equals(node.key)) {
                prevValue = node.value;
                node.value = value;
                break;
            }
            node = node.next;
        }
        // если не нашли, то создаём новый узел и добавляем в начало списка
        // если бы поддерживали ключи/значения null, то нужно было бы добавить флаг
        if (prevValue == null) {
            Node<K, V> newNode = new Node<>(key, value, oldNode);
            buckets[hash] = newNode;
            // увеличиваем размер и рехэшируем при необходимости
            size++;
            if (size > threshold) {
                resize(size * 2 + 1);
            }
        }
        return prevValue;
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key should be not null");
        }
        // считаем индекс в массиве
        int hash = hash(key);
        // ищем элемент
        Node<K, V> node = buckets[hash];
        V value = null;
        while (node != null) {
            if (key.equals(node.key)) {
                value = node.value;
                break;
            }
            node = node.next;
        }
        return value;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key should be not null");
        }
        // считаем индекс в массиве
        int hash = hash(key);
        Node<K, V> oldNode = buckets[hash];
        // проходимся по списку, ищем удаляемый ключ
        V prevValue = null;
        Node<K, V> node = oldNode;
        Node<K, V> prev = null;
        while (node != null) {
            if (key.equals(node.key)) {
                prevValue = node.value;
                if (node == oldNode) { // если это начало списка, то просто меняем значение в ячейке хэш таблицы
                    buckets[hash] = node.next;
                } else { // иначе, меняем ссылку на следующий у предыдущего (prev не может быть null, если это не oldNode)
                    prev.next = node.next;
                }
                break;
            }
            prev = node;
            node = node.next;
        }

        // уменьшаем размер, если ключ существовал
        // если бы поддерживали ключи/значения null, то нужно было бы добавить флаг
        // можно рехэшировать при необходимости
        if (prevValue != null) {
            size--;
        }
        return prevValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * хэш между 0 и capacity-1 (для определения индекса)
     * (убираем знак и обрабатываем значение -2^31)
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    /**
     * пересчитать следующий capacity * loadFactor, при котором проводить рехэширование
     */
    private int threshold() {
        return (int) (capacity * loadFactor);
    }

    /**
     * рехэширование с новым размером
     */
    @SuppressWarnings({"unchecked"})
    private void resize(int newCapacity) {
        Node<K, V>[] oldBuckets = this.buckets;
        this.buckets = (Node<K, V>[]) new Node[newCapacity];
        this.capacity = newCapacity;
        this.size = 0;
        this.threshold = threshold();
        Node<K, V> oldNode;
        Node<K, V> newNode;
        for (Node<K, V> node : oldBuckets) {
            while (node != null) {
                // с созданием новых объектов
//                put(node.key, node.value);
//                node = node.next;

                // без создания новых объектов
                // ключи будут уникальны, поэтому это не проверяем

                // считаем индекс в массиве
                int hash = hash(node.key);
                // получаем предыдущий первый элемент списка в данной ячейке (если есть)
                oldNode = buckets[hash];
                // сохраняем ссылку на добавляемый элемент
                newNode = node;
                // переходим к следующему в старой хэш-таблице
                node = node.next;
                // добавляем элемент в начало списка
                newNode.next = oldNode;
                buckets[hash] = newNode;
                // меняем размер
                size++;
            }
        }
    }

    static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K,V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
