package ru.otus.array;

public class FactorArray<T> implements DynamicArray<T> {

    private Object[] array;
    private final int factor;
    private final int initLength;
    private int size;

    public FactorArray(int factor, int initLength) {
        this.factor = factor;
        this.initLength = initLength;
        this.array = new Object[initLength];
        this.size = 0;
    }

    public FactorArray() {
        this(50, 10);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(T item) {
        if (size() == array.length) {
            resize();
        }
        array[size] = item;
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) array[index];
    }

    @Override
    public void add(T item, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size == array.length) {
            resize();
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = item;
        size++;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        T oldValue = get(index);
        int newSize;
        if ((newSize = size - 1) > index) {
            System.arraycopy(array, index + 1, array, index, newSize - index);
        }
        size = newSize;
        array[size] = null;
        return oldValue;
    }

    @Override
    public DynamicArray<T> copy() {
        DynamicArray<T> newArray = new FactorArray<>(factor, initLength);
        for (int i = 0; i < size(); i++) {
            newArray.add(get(i));
        }
        return newArray;
    }

    private void resize() {
        Object[] newArray = new Object[array.length + array.length * factor / 100];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }
}
