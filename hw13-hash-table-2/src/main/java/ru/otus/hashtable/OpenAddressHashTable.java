package ru.otus.hashtable;

/**
 * Хэш-таблица с открытой адресацией
 * "Ленивое" удаление (при вставке нового элемента или рехэшировании)
 * null-ключи и null-значения не поддерживаются
 * Рехэширование при удалении элементов не реализовано
 */
public abstract class OpenAddressHashTable<K, V> implements HashTable<K, V> {

    /**
     * узел, которым подменяем удалённые элементы (для реализации "ленивого удаления")
     */
    private final Node<K, V> dummy = new Node<>(null, null);

    /**
     * реальное количество элементов
     */
    protected int size;

    /**
     * размер хэш-таблицы
     */
    protected int capacity;

    /**
     * при каком уровне заполнения таблицы проводить рехэширование
     */
    private final float loadFactor;

    /**
     * следующий capacity * loadFactor
     */
    private int threshold;

    /**
     * массив ключ-значение
     */
    private Node<K, V>[] buckets;


    @SuppressWarnings({"unchecked"})
    public OpenAddressHashTable(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.threshold = threshold();
        this.buckets = (Node<K, V>[]) new Node[capacity];
    }

    /**
     * расчёт индекса (пробинг)
     */
    protected abstract int index(int hash, int attempt);

    /**
     * новый размер массива в зависимости от количества элементов
     */
    protected abstract int recalcCapacity();

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key / Value should be not null");
        }
        // находим хэш
        int hash = hash(key);

        // ищем свободное место в таблице (или узел с таким же ключом)
        // если есть такой ключ, то сохраняем старое значение (и потом возвращаем) и перезаписываем
        // если находим удалённый узел (== dummy), то поместим на его место, а не на null (но сначала пройдёмся по всем, чтобы проверить наличие ключа)
        V prevValue = null;
        Node<K, V> node;
        int index = -1; // индекс, на место которого можем поместить элемент (первый dummy или null)
        for (int attempt = 0; attempt < capacity; attempt++) {
            int curIndex = index(hash, attempt);
            node = buckets[curIndex];
            if (node == null) { // если пустая, то ключ не нашли, перезаписываем index (если до этого не было dummy) и выходим
                if (index == -1) {
                    index = curIndex;
                }
                break;
            } else if (node == dummy) { // если dummy, то перезаписываем индекс (если это первый dummy); не выходим, т.к. ищем ключ
                if (index == -1) {
                    index = curIndex;
                }
            } else if (key.equals(node.key)) { // нашли ключ, подменили значение, выходим
                prevValue = node.value;
                node.value = value;
                break;
            }
        }

        // если не нашли, то создаём новый узел и добавляем в найденный индекс
        // если бы поддерживали ключи/значения null, то нужно было бы добавить флаг
        if (prevValue == null) {
            Node<K, V> newNode = new Node<>(key, value);
            buckets[index] = newNode;
            // увеличиваем размер и рехэшируем при необходимости
            size++;
            if (size > threshold) {
                resize(recalcCapacity());
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
        // находим хэш
        int hash = hash(key);

        // проходимся по списку, ищем ключ
        V value = null;
        Node<K, V> node;
        for (int attempt = 0; attempt < capacity; attempt++) {
            int curIndex = index(hash, attempt);
            node = buckets[curIndex];
            if (node == null) { // если пустая, то ключ не нашли => выходим
                break;
            } else if (key.equals(node.key)) { // нашли ключ, выходим; null ключей нет, поэтому сравнение с dummy всегда будет false
                value = node.value;
                break;
            }
        }
        return value;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key should be not null");
        }

        // находим хэш
        int hash = hash(key);

        // проходимся по списку, ищем удаляемый ключ
        V prevValue = null;
        Node<K, V> node;
        for (int attempt = 0; attempt < capacity; attempt++) {
            int curIndex = index(hash, attempt);
            node = buckets[curIndex];
            if (node == null) { // если пустая, то ключ не нашли => выходим
                break;
            } else if (key.equals(node.key)) { // нашли ключ, заменяем на dummy, выходим
                prevValue = node.value;
                buckets[curIndex] = dummy;
                break;
            }
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
     * рехэширование с новым размером; удаляются dummy
     * одинаковых ключей быть не может
     */
    @SuppressWarnings({"unchecked"})
    private void resize(int newCapacity) {
        Node<K, V>[] oldBuckets = this.buckets;
        this.buckets = (Node<K, V>[]) new Node[newCapacity];
        this.capacity = newCapacity;
        this.size = 0;
        this.threshold = threshold();

        for (Node<K, V> oldNode : oldBuckets) {
            if (oldNode == null || oldNode == dummy) {
                continue;
            }

            // находим хэш
            int hash = hash(oldNode.key);

            // ищем свободное место в таблице
            // ключи будут уникальны, поэтому это не проверяем
            int index = -1; // индекс, на место которого можем поместить элемент
            for (int attempt = 0; attempt < capacity; attempt++) {
                index = index(hash, attempt);
                if (buckets[index] == null) {
                    break;
                }
            }

            buckets[index] = oldNode;
            size++;
        }
    }

    static class Node<K, V> {
        final K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
