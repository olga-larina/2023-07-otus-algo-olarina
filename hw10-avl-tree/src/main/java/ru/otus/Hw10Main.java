package ru.otus;

import ru.otus.tree.AVLTree;
import ru.otus.tree.BinarySearchTree;
import ru.otus.tree.Tree;

import java.util.Random;

public class Hw10Main {

    private static final Random random = new Random();

    public static void main(String[] args) {
        testWithData();
    }

    public static void testWithData() {
        for (int type = 1; type <= 2; type++) {
            for (int n = 10; n <= 10_000_000; n *= 10) {
                int[] arr = (type == 1) ? random(n) : sorted(n);
                Tree<Integer, Integer> tree = new BinarySearchTree<>();
//                Tree<Integer, Integer> tree = new AVLTree<>();

                // добавление элементов
                long start = System.currentTimeMillis();
                for (int i : arr) {
                    tree.insert(i, i);
                }
                long msInsert = System.currentTimeMillis() - start;

                // поиск элементов
                start = System.currentTimeMillis();
                for (int i = 0; i < n / 10; i++) {
                    tree.search(random.nextInt(Integer.MAX_VALUE));
                }
                long msSearch = System.currentTimeMillis() - start;

                // удаление элементов
                start = System.currentTimeMillis();
                for (int i = 0; i < n / 10; i++) {
                    tree.remove(random.nextInt(Integer.MAX_VALUE));
                }
                long msRemove = System.currentTimeMillis() - start;

                System.out.printf("type=%s\tn=%10d\t Insert=%8d ms\tSearch=%8d ms\tRemove=%8d ms\n", (type == 1) ? "Random" : "Sorted", n, msInsert, msSearch, msRemove);
            }
        }
    }

    static int[] digits(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(10);
        }
        return arr;
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

    static boolean testIfSorted(int[] arr) {
        boolean sorted = true;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                sorted = false;
                break;
            }
        }
        return sorted;
    }

}