package ru.otus.barn;

/**
 * Предрасчёт высоты (длины) сарая, т.е. свободных ячеек вверх
 */
public class BarnHeight {

    /**
     * предрассчитанные высоты для текущей строки
     */
    private final int[] heights;

    public BarnHeight(int size) {
        this.heights = new int[size];
    }

    /**
     * Расчёт высот для текущей строки (с учётом того, что данные в heights хранятся накопленным итогом с первой строки).
     * Если ячейка пустая, то высоту можно увеличить. Иначе, высота обнуляется
     * @param r текущая строка
     */
    public void fillHeights(int[][] area, int r) {
        for (int c = 0; c < heights.length; c++) {
            if (area[r][c] == 1) {
                heights[c] = 0;
            } else {
                heights[c]++;
            }
        }
    }

    /**
     * Получить высоту для последней обработанной строки и столбца c
     * @param c текущий столбец
     * @return высота
     */
    public int height(int c) {
        return heights[c];
    }
}
