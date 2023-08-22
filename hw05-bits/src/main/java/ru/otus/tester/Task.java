package ru.otus.tester;

import java.util.List;

public interface Task<T> {

    /**
     * @param data исходные данные
     * @return результат
     * @throws InterruptedException для неоптимальных алгоритмов может быть возможность остановки потока
     */
    T run(List<String> data) throws InterruptedException;

    String name();

}
