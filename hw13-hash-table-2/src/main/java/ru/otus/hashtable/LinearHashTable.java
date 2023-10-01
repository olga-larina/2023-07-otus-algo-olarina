package ru.otus.hashtable;

/**
 * Хэш таблица с линейным пробингом
 * hash(k,i) = (hash′(k) + i) mod M
 */
public class LinearHashTable<K, V> extends OpenAddressHashTable<K, V> {

    private static final int DEFAULT_CAPACITY = 17;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public LinearHashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public LinearHashTable(int capacity, float loadFactor) {
        super(capacity, loadFactor);
    }

    @Override
    protected int index(int hash, int attempt) {
        return (hash + attempt) % capacity;
    }

    @Override
    protected int recalcCapacity() {
        return capacity * 2 + 1;
    }
}
