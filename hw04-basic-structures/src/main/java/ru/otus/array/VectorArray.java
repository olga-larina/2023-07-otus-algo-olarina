package ru.otus.array;

public class VectorArray<T> implements DynamicArray<T> {

    private Object[] array;
    private final int vector;
    private int size;

    public VectorArray(int vector) {
        this.vector = vector;
        this.array = new Object[0];
        this.size = 0;
    }

    public VectorArray() {
        this(10);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(T item) {
        if (size == array.length) {
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
        DynamicArray<T> newArray = new VectorArray<>(vector);
        for (int i = 0; i < size(); i++) {
            newArray.add(get(i));
        }
        return newArray;
    }

    private void resize() {
        Object[] newArray = new Object[array.length + vector];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }
}
