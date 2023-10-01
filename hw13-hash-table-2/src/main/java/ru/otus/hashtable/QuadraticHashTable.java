package ru.otus.hashtable;

/**
 * Хэш таблица с квадратичным пробингом
 * hash(k) = (hash′(k) + (i + i^2) / 2) mod M, где c1=c2=1/2, M=2^k
 */
public class QuadraticHashTable<K, V> extends OpenAddressHashTable<K, V> {

    private static final int DEFAULT_CAPACITY = 16;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public QuadraticHashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public QuadraticHashTable(int capacity, float loadFactor) {
        super(capacity, loadFactor);
    }

    @Override
    protected int index(int hash, int attempt) {
        return (hash + (attempt + attempt * attempt) / 2) % capacity;
    }

    @Override
    protected int recalcCapacity() {
        return capacity << 1;
    }
}
