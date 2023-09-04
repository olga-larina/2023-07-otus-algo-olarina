package ru.otus;

import ru.otus.sort.HeapSort;
import ru.otus.sort.SelectionSort;
import ru.otus.sort.SortIntAlgo;
import ru.otus.sort.SortIntTask;
import ru.otus.tester.IntArrayTester;
import ru.otus.tester.Task;
import ru.otus.tester.Tester;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Hw07Main {

    public static void main(String[] args) throws Exception {
//        testWithData();
        testWithFiles();
    }

    public static void testWithData() throws Exception {
        for (int n = 10; n <= 1_000_000; n *= 10) {
            SortIntAlgo sort = new SelectionSort(random(n));
            long start = System.currentTimeMillis();
            sort.sort();
            long ms = System.currentTimeMillis() - start;
            System.out.println(sort + "\ttime=" + ms + " ms");
        }
    }

    static int[] random(int n) {
        int[] arr = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(n);
        }
        return arr;
    }

    static int[] sorted(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        return arr;
    }

    static int[] reversed(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = n - i;
        }
        return arr;
    }

    public static void testWithFiles() throws Exception {

        List<Task<int[]>> tasks = List.of(
            new SortIntTask(SelectionSort::new, "Selection"),
            new SortIntTask(HeapSort::new, "Heap")
        );

        Path filesPath = Paths.get("files").toAbsolutePath().resolve("sorting-tests");

        List<String> dirs = List.of(
            "0.random", // массив из случайных чисел
            "1.digits", // массив из случайных цифр
            "2.sorted", // на 99% отсортированный массив
            "3.revers" // обратно отсортированный массив
        );
        for (String dir : dirs) {
            System.out.println("---------------------------------------------------------");
            System.out.println("Dir: " + dir);
            Tester<int[]> sortTester = new IntArrayTester(tasks, filesPath.resolve(dir));
            sortTester.runTests();
            sortTester.stop();
        }
    }
}