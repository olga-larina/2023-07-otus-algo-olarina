package ru.otus.sort;

import ru.otus.tester.Task;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class SortIntTask implements Task<int[]> {

    private final Function<int[], SortIntAlgo> sortAlgoFunc;
    private final String name;
    private SortIntAlgo lastAlgo;

    public SortIntTask(Function<int[], SortIntAlgo> sortAlgoFunc, String name) {
        this.sortAlgoFunc = sortAlgoFunc;
        this.name = name;
    }

    @Override
    public int[] run(List<String> data) throws InterruptedException {
        int size = Integer.parseInt(data.get(0));
        int[] arr = new int[size];
        int i = 0;
        for (String str : data.get(1).split(" ")) {
            arr[i++] = Integer.parseInt(str);
        }
        SortIntAlgo sortAlgo = sortAlgoFunc.apply(arr);
        lastAlgo = sortAlgo;
        sortAlgo.sort();
        return arr;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String info() {
        if (lastAlgo != null) {
            return lastAlgo.toString();
        } else {
            return Task.super.info();
        }
    }
}
