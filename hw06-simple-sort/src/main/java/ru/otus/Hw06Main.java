package ru.otus;

import ru.otus.sort.*;
import ru.otus.tester.IntArrayTester;
import ru.otus.tester.Task;
import ru.otus.tester.Tester;

import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class Hw06Main {

    public static void main(String[] args) throws Exception {
        testWithData();
//        testWithFiles();
    }

    public static void testWithData() throws Exception {
        for (int n = 10; n <= 1_000_000; n *= 10) {
            SortIntAlgo sort = new InsertSortShift(random(n));
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
            new SortIntTask(BubbleSort::new, "Bubble Simple"),
            new SortIntTask(BubbleSortOptimal::new, "Bubble Optimized"),
            new SortIntTask(InsertSort::new, "Insert Simple"),
            new SortIntTask(InsertSortShift::new, "Insert Shift"),
            new SortIntTask(InsertSortBinary::new, "Insert Binary"),
            new SortIntTask(ShellSort::new, "Shell Simple"),
            new SortIntTask(ShellSortKnuth::new, "Shell Knuth"),
            new SortIntTask(ShellSortHibbard::new, "Shell Hibbard")
        );

        List<String> dirs = List.of(
            "0.random", // массив из случайных чисел
            "1.digits", // массив из случайных цифр
            "2.sorted", // на 99% отсортированный массив
            "3.revers" // обратно отсортированный массив
        );
        for (String dir : dirs) {
            System.out.println("---------------------------------------------------------");
            System.out.println("Dir: " + dir);
            Tester<int[]> sortTester = new IntArrayTester(tasks, Path.of(Hw06Main.class.getClassLoader().getResource(dir).toURI()));
            sortTester.runTests();
            sortTester.stop();
        }
    }
}