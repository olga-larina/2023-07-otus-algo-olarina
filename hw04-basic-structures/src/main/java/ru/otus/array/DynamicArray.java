package ru.otus.array;

public interface DynamicArray<T> {

    int size();

    void add(T item);

    T get(int index);

     void add(T item, int index);

     T remove(int index);

     DynamicArray<T> copy();
}
