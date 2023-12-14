package ru.otus.barn;

/**
 * Маленький сарай (простой перебор)
 */
public class SmallBarn {

    /**
     * участок, 1 - постройка, 0 - свободно
     */
    private final int[][] area;

    public SmallBarn(int[][] area) {
        this.area = area;
    }

    /**
     * Поиск максимальной площади сарая простым перебором
     * @return максимальная площадь сарая
     */
    public int maxSquare() {
        int maxSquare = 0;
        for (int r = 0; r < area.length; r++) { // строки
            for (int c = 0; c < area[r].length; c++) { // столбцы
                int square = maxSquare(r, c);
                maxSquare = Math.max(maxSquare, square);
            }
        }
        return maxSquare;
    }

    /**
     * Поиск максимальной площади сарая с левой нижней границей в (r, c)
     * @param r начальная строка
     * @param c начальный столбец
     * @return максимальная площадь сарая
     */
    private int maxSquare(int r, int c) {
        int maxSquare = 0;
        int limHeight = area.length; // максимально возможная высота вверх от данной ячейки для разных вариантов ширины
        for (int width = 1; width + c - 1 < area[r].length; width++) { // ширина
            int height = maxHeight(r, c + width - 1, limHeight); // максимальная высота для рассматриваемой ячейки
            limHeight = Math.min(limHeight, height); // обновляем максимальную высоту для ячейки (r, c)

            maxSquare = Math.max(maxSquare, limHeight * width);
        }
        return maxSquare;
    }

    /**
     * Поиск максимальной высоты
     * @param r начальная строка
     * @param c начальный столбец
     * @param limHeight лимит высоты
     * @return свободная высота, т.е. количество свободных ячеек вверх от координаты (r, c)
     */
    private int maxHeight(int r, int c, int limHeight) {
        int height = 0;
        while (r >= 0 && area[r][c] == 0 && height < limHeight) {
            height++;
            r--;
        }
        return height;
    }

    public static void main(String[] args) {
        SmallBarn barn = new SmallBarn(new int[][] {
            new int[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 1, 0, 1, 0, 0, 1, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
            new int[] { 0, 0, 1, 0, 0, 1, 1, 0, 1, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }
        });
        System.out.println(barn.maxSquare()); // 24
    }
}
