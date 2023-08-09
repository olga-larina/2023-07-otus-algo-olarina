package ru.otus;

public class HarryPotterSquare {

    /**
     * y, x - целые числа int
     * 01. y < x
     * 02. y == x
     * 03. y == 24 - x
     * 04. y <= 29 - x
     * 05. y == x / 2
     * 06. x < 10 || y < 10
     * 07. x > 15 && y > 15
     * 08. x * y == 0
     * 09. y < x - 10 || y > x + 10
     * 10. y >= x / 2 && y < x
     * 11. y == 1 || x == 1 || y == 23 || x == 23
     * 12. x*x + y*y <= 20*20
     * 13. y >= 20 - x && y <= 28 - x
     * 14. x * y <= 20 * 5
     * 15. Math.abs(x - y) >= 10 && Math.abs(x - y) <= 20
     * 16. Math.abs(x - y) < 10 && x + y >= 15 && x + y <= 33
     * 17. y >= Math.sin((double) x / 3) * 8 + 16
     * 18. x * y < x + y
     * 19. x * y == 0 || x == 24 || y == 24
     * 20. (x + y) % 2 == 0
     * 21. x % (y + 1) == 0
     * 22. (x + y) % 3 == 0
     * 23. y % 3 == 0 && x % 2 == 0
     * 24. y == x || y == 24 - x
     * 25. y % 6 == 0 || x % 6 == 0
     */
    public static void main(String[] args) {
        for (int y = 0; y < 25; y++) {
            System.out.printf("%2d ", y);
            for (int x = 0; x < 25; x++) {
                System.out.print(spell25(y, x) ? "# " : ". ");
            }
            System.out.println();
        }
    }

    private static boolean spell01(int y, int x) {
        return y < x;
    }

    private static boolean spell02(int y, int x) {
        return y == x;
    }

    private static boolean spell03(int y, int x) {
        return y == 24 - x;
    }

    private static boolean spell04(int y, int x) {
        return y <= 29 - x;
    }

    private static boolean spell05(int y, int x) {
        return y == x / 2;
    }

    private static boolean spell06(int y, int x) {
        return x < 10 || y < 10;
    }

    private static boolean spell07(int y, int x) {
        return x > 15 && y > 15;
    }

    private static boolean spell08(int y, int x) {
        return x * y == 0;
    }

    private static boolean spell09(int y, int x) {
        return y < x - 10 || y > x + 10; // Math.abs(x - y) > 10
    }

    private static boolean spell10(int y, int x) {
        return y >= x / 2 && y < x; // Math.floor (x / (y + 1)) == 1
    }

    private static boolean spell11(int y, int x) {
        return y == 1 || x == 1 || y == 23 || x == 23;
    }

    private static boolean spell12(int y, int x) {
        return x*x + y*y <= 20*20;
    }

    private static boolean spell13(int y, int x) {
        return y >= 20 - x && y <= 28 - x;
    }

    private static boolean spell14(int y, int x) {
        return x * y <= 20 * 5;
    }

    private static boolean spell15(int y, int x) {
        return Math.abs(x - y) >= 10 && Math.abs(x - y) <= 20;
    }

    private static boolean spell16(int y, int x) {
        return Math.abs(x - y) < 10 && x + y >= 15 && x + y <= 33; // Math.abs(x - 12) + Math.abs(y - 12) < 10
    }

    private static boolean spell17(int y, int x) {
        return y >= Math.sin((double) x / 3) * 8 + 16;
    }

    private static boolean spell18(int y, int x) {
        return x * y < x + y;
    }

    private static boolean spell19(int y, int x) {
        return x * y == 0 || x == 24 || y == 24;
    }

    private static boolean spell20(int y, int x) {
        return (x + y) % 2 == 0;
    }

    private static boolean spell21(int y, int x) {
        return x % (y + 1) == 0;
    }

    private static boolean spell22(int y, int x) {
        return (x + y) % 3 == 0;
    }

    private static boolean spell23(int y, int x) {
        return y % 3 == 0 && x % 2 == 0;
    }

    private static boolean spell24(int y, int x) {
        return y == x || y == 24 - x;
    }

    private static boolean spell25(int y, int x) {
        return y % 6 == 0 || x % 6 == 0;
    }
}
