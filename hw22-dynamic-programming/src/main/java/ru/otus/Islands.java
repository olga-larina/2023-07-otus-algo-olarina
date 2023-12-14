package ru.otus;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Острова
 */
public class Islands {

    /**
     * Поиск количества островов в матрице, образованных единицами
     * @param n размерность матрицы (n x n)
     * @param islands матрица из 1 или 0
     * @return количество островов
     */
    public int solve(int n, int[][] islands) {
        int cnt = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (islands[r][c] == 1) {
                    cnt++;
                    dfs(r, c, islands);
                }
            }
        }
        return cnt;
    }

    private void dfs(int r, int c, int[][] islands) {
        if (r < 0 || r >= islands.length || c < 0 || c >= islands[r].length || islands[r][c] == 0) {
            return;
        }
        islands[r][c] = 0; // обнуляем остров (можно хранить массив visited, но это дополнительный расход памяти)
        dfs(r - 1, c, islands); // вверх
        dfs(r + 1, c, islands); // вниз
        dfs(r, c - 1, islands); // влево
        dfs(r, c + 1, islands); // вправо
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(Pattern.compile("[ \n]*"));
        int n = scanner.nextInt();
        int[][] islandsArr = new int[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                islandsArr[r][c] = scanner.nextInt();
            }
        }
        Islands islands = new Islands();
        System.out.println(islands.solve(n, islandsArr));

//        System.out.println(new Islands().solve(4, new int[][] {
//            new int[] { 1, 1, 1, 1 },
//            new int[] { 0, 1, 0, 1 },
//            new int[] { 0, 0, 0, 0 },
//            new int[] { 1, 0, 1, 1 }
//        })); // 3
    }
}
