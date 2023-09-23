package ru.otus;

import ru.otus.tree.SplayBST;
import ru.otus.tree.Tree;

import java.util.Random;

public class Hw11Main {

    private static final Random random = new Random();

    public static void main(String[] args) {
        testWithData();
    }

    public static void testWithData() {
        for (int type = 1; type <= 2; type++) {
            for (int n = 10; n <= 10_000_000; n *= 10) {
                int[] arr = (type == 1) ? random(n) : sorted(n);
                Tree<Integer, Integer> tree = new SplayBST<>();
//                Tree<Integer, Integer> tree = new Treap<>();

                // добавление элементов
                long start = System.currentTimeMillis();
                for (int i: arr) {
                    tree.insert(i, i);
                }
                long msInsert = System.currentTimeMillis() - start;

                // поиск элементов
                start = System.currentTimeMillis();
                for (int i = 0; i < n / 10; i++) {
                    tree.search(random.nextInt(n));
                }
                long msSearch = System.currentTimeMillis() - start;

                // 1000 раз поиск элементов от 1 до 10
                start = System.currentTimeMillis();
                for (int i = 1; i <= 1000; i++) {
                    for (int j = 1; j <= 10; j++) {
                        tree.search(j);
                    }
                }
                long msSearch10 = System.currentTimeMillis() - start;

                // удаление элементов
                start = System.currentTimeMillis();
                for (int i = 0; i < n / 10; i++) {
                    tree.remove(random.nextInt(n));
                }
                long msRemove = System.currentTimeMillis() - start;

                System.out.printf("type=%s\tn=%10d\t Insert=%8d ms\tSearch=%8d ms\tSearch [1;10]=%8d ms\tRemove=%8d ms\n",
                    (type == 1) ? "Random" : "Sorted", n, msInsert, msSearch, msSearch10, msRemove);
            }
        }
    }

    static int[] random(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(n + 1);
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

}