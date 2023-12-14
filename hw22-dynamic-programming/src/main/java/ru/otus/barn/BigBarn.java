package ru.otus.barn;

/**
 * Большой сарай (с предрасчётом высоты (длины) и ширины)
 */
public class BigBarn {

    /**
     * участок, 1 - постройка, 0 - свободно
     */
    private final int[][] area;

    /**
     * для расчёта высоты
     */
    private final BarnHeight barnHeight;

    /**
     * для расчёта ширины
     */
    private final BarnWidth barnWidth;

    public BigBarn(int[][] area) {
        this.area = area;
        this.barnHeight = new BarnHeight(area[0].length);
        this.barnWidth = new BarnWidth(barnHeight, area[0].length);
    }

    /**
     * Поиск максимальной площади сарая простым перебором
     * @return максимальная площадь сарая
     */
    public int maxSquare() {
        int maxSquare = 0;
        for (int r = 0; r < area.length; r++) { // строки
            barnHeight.fillHeights(area, r); // заполняем высоты
            barnWidth.fillWidths(); // заполняем ширины
            for (int c = 0; c < area[r].length; c++) { // столбцы
                int square = barnWidth.width(c) * barnHeight.height(c);
                maxSquare = Math.max(maxSquare, square);
            }
        }
        return maxSquare;
    }

    public static void main(String[] args) {
        BigBarn barn = new BigBarn(new int[][] {
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
