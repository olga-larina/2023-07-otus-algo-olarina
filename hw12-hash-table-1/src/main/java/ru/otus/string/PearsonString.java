package ru.otus.string;

import java.util.Objects;

/**
 * Реализация функции Пирсона для хэширования строк
 */
public class PearsonString {

    private static final short[] LOOKUP_TABLE = new short[] {
        98, 6, 85, 150, 36, 23, 112, 164, 135, 207, 169, 5, 26, 64, 165, 219,
        61, 20, 68, 89, 130, 63, 52, 102, 24, 229, 132, 245, 80, 216, 195, 115,
        90, 168, 156, 203, 177, 120, 2, 190, 188, 7, 100, 185, 174, 243, 162, 10,
        237, 18, 253, 225, 8, 208, 172, 244, 255, 126, 101, 79, 145, 235, 228, 121,
        123, 251, 67, 250, 161, 0, 107, 97, 241, 111, 181, 82, 249, 33, 69, 55,
        59, 153, 29, 9, 213, 167, 84, 93, 30, 46, 94, 75, 151, 114, 73, 222,
        197, 96, 210, 45, 16, 227, 248, 202, 51, 152, 252, 125, 81, 206, 215, 186,
        39, 158, 178, 187, 131, 136, 1, 49, 50, 17, 141, 91, 47, 129, 60, 99,
        154, 35, 86, 171, 105, 34, 38, 200, 147, 58, 77, 118, 173, 246, 76, 254,
        133, 232, 196, 144, 98, 124, 53, 4, 108, 74, 223, 234, 134, 230, 157, 139,
        189, 205, 199, 128, 176, 19, 211, 236, 127, 192, 231, 70, 233, 88, 146, 44,
        183, 201, 22, 83, 13, 214, 116, 109, 159, 32, 95, 226, 140, 220, 57, 12,
        221, 31, 209, 182, 143, 92, 149, 184, 148, 62, 113, 65, 37, 27, 106, 166,
        3, 14, 204, 72, 21, 41, 56, 66, 28, 193, 40, 217, 25, 54, 179, 117,
        238, 87, 240, 155, 180, 170, 242, 212, 191, 163, 78, 218, 137, 194, 175, 110,
        43, 119, 224, 71, 122, 142, 42, 160, 104, 48, 247, 103, 15, 11, 138, 239
    }; // случайно перемешанные числа 0-255

    private final String str;
    private final int hash;

    public PearsonString(String str) {
        this.str = str;
        this.hash = pearsonHash32(str);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PearsonString that = (PearsonString) o;
        return Objects.equals(str, that.str);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    // 8-битное значение
    private static short pearsonHash8(String value) {
        if (value == null) {
            return 0;
        }
        short result = 0;
        for (char c : value.toCharArray()) {
            result = LOOKUP_TABLE[c ^ result];
        }
        return result;
    }

    /**
     * 32-битное значение
     * Например, value = "12"
     * j = 0; hash = 0; h = 90 (1011010) => hash = 90 (1011010)
     * j = 1; hash = 90 (1011010); h = 166 (10100110) -> hash = 42586 (10100110 01011010)
     * j = 2; hash = 42586 (10100110 01011010); h = 72 (1001000) -> hash = 4761178 (1001000 10100110 01011010)
     * j = 3; hash = 4761178 (1001000 10100110 01011010); h = 101 (1100101) -> hash = 1699259994 (1100101 01001000 10100110 01011010)
     */
    private static int pearsonHash32(String value) {
        int hash = 0;
        int h;
        for (int j = 0; j < 4; j++) {
            h = LOOKUP_TABLE[(value.charAt(0) + j) % 256];
            for (int i = 1; i < value.length(); i++) {
                h = LOOKUP_TABLE[h ^ value.charAt(1)];
            }
            // j сдвигается влево на 3 бита: 0 - 8 - 64 - 128; h сдвигается влево в зависимости от шага (какой байт вычисляем)
            // т.е. h сдвигается вправо на нужный байт j и записывается в начало hash
            hash = hash | (h << (j << 3));
        }
        return hash;
    }

//    public static void main(String[] args) {
//        String abc = "12";
//        System.out.println(pearsonHash8(abc));
//        System.out.println(pearsonHash32(abc));
//        System.out.println(Integer.MAX_VALUE);
//    }
}
