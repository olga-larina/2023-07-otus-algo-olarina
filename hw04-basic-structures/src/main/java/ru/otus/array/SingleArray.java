package ru.otus.array;

public class SingleArray<T> implements DynamicArray<T> {

    private Object[] array;

    public SingleArray() {
        this.array = new Object[0];
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public void add(T item) {
        resize();
        array[size() - 1] = item;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) array[index];
    }

    @Override
    public void add(T item, int index) {
        int s = size();
        if (index > s || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        resize();
        System.arraycopy(array, index, array, index + 1, s - index);
        array[index] = item;
    }

    @Override
    public T remove(int index) {
        int s = size();
        if (index >= s || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        T oldValue = get(index);
        Object[] newArray = new Object[s - 1];
        if (index > 0) {
            System.arraycopy(array, 0, newArray, 0, index);
        }
        if (s - 1 > index) {
            System.arraycopy(array, index + 1, newArray, index, s - 1 - index);
        }
        array = newArray;
        return oldValue;
    }

    @Override
    public DynamicArray<T> copy() {
        DynamicArray<T> newArray = new SingleArray<>();
        for (int i = 0; i < size(); i++) {
            newArray.add(get(i));
        }
        return newArray;
    }

    private void resize() {
        Object[] newArray = new Object[size() + 1];
        System.arraycopy(array, 0, newArray, 0, size());
//        for (int j = 0; j < size(); j++) {
//            newArray[j] = array[j];
//        }
        array = newArray;
    }
}
