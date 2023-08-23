package ru.otus.array;

public class MatrixArray<T> implements DynamicArray<T> {

    private int size;
    private final int vector;
    private final DynamicArray<DynamicArray<T>> array;

    public MatrixArray(int vector) {
        this.vector = vector;
        this.array = new SingleArray<>();
        this.size = 0;
    }

    public MatrixArray() {
        this(10);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(T item) {
        if (size == array.size() * vector) {
            array.add(new VectorArray<>(vector));
        }
        array.get(size / vector).add(item);
        size++;
    }

    @Override
    public T get(int index) {
        return array.get(index / vector).get(index % vector);
    }

    @Override
    public void add(T item, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size == array.size() * vector) {
            array.add(new VectorArray<>(vector));
        }
        int i1 = index / vector;
        int i2 = index % vector;
        for (int i = array.size() - 2; i >= i1; i--) {
            DynamicArray<T> row = array.get(i);
            T lastElementInPrevRow = row.remove(row.size() - 1);
            array.get(i + 1).add(lastElementInPrevRow, 0);
        }
        array.get(i1).add(item, i2);
        size++;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        int i1 = index / vector;
        int i2 = index % vector;
        T oldValue = array.get(i1).remove(i2);
        for (int i = i1 + 1; i < array.size(); i++) {
            DynamicArray<T> row = array.get(i);
            T firstElementInNextRow = row.remove(0);
            array.get(i - 1).add(firstElementInNextRow); // добавляем в конец
        }
        if (array.get(array.size() - 1).size() == 0) {
            array.remove(array.size() - 1);
        }
        size--;
        return oldValue;
    }

    @Override
    public DynamicArray<T> copy() {
        DynamicArray<T> newArray = new MatrixArray<>(vector);
        for (int i = 0; i < size(); i++) {
            newArray.add(get(i));
        }
        return newArray;
    }
}
