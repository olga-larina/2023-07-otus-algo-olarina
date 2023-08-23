package ru.otus.array;

import java.util.ArrayList;
import java.util.List;

public class ArrayListArray<T> implements DynamicArray<T> {

    private final List<T> list;

    public ArrayListArray() {
        this.list = new ArrayList<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(T item) {
        list.add(item);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public void add(T item, int index) {
        list.add(index, item);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public DynamicArray<T> copy() {
        DynamicArray<T> newArray = new ArrayListArray<>();
        for (int i = 0; i < size(); i++) {
            newArray.add(get(i));
        }
        return newArray;
    }

}
