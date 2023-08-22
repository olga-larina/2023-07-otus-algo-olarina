package ru.otus;

import ru.otus.chess.*;
import ru.otus.popcnt.PopCount1Shift;
import ru.otus.popcnt.PopCount2Subtract;
import ru.otus.popcnt.PopCount3Cache;
import ru.otus.tester.StringListTester;
import ru.otus.tester.Tester;

import java.nio.file.Path;
import java.util.List;

public class Hw05Main {
    public static void main(String[] args) throws Exception {

        // 1.Король
        Tester<List<String>> kingTester = new StringListTester(
            List.of(
                new BiChessTask(new King(), new PopCount1Shift()),
                new BiChessTask(new King(), new PopCount2Subtract()),
                new BiChessTask(new King(), new PopCount3Cache())
            ),
            Path.of(Hw05Main.class.getClassLoader().getResource("1.Bitboard - Король").toURI())
        );
        kingTester.runTests();
        kingTester.stop();

        // 2.Конь
        Tester<List<String>> knightTester = new StringListTester(
            List.of(
                new BiChessTask(new Knight(), new PopCount1Shift()),
                new BiChessTask(new Knight(), new PopCount2Subtract()),
                new BiChessTask(new Knight(), new PopCount3Cache())
            ),
            Path.of(Hw05Main.class.getClassLoader().getResource("2.Bitboard - Конь").toURI())
        );
        knightTester.runTests();
        knightTester.stop();

        // 3.Ладья
        Tester<List<String>> rookTester = new StringListTester(
            List.of(
                new BiChessTask(new Rook(), new PopCount1Shift()),
                new BiChessTask(new Rook(), new PopCount2Subtract()),
                new BiChessTask(new Rook(), new PopCount3Cache())
            ),
            Path.of(Hw05Main.class.getClassLoader().getResource("3.Bitboard - Ладья").toURI())
        );
        rookTester.runTests();
        rookTester.stop();

        // 4.Слон
        Tester<List<String>> bishopTester = new StringListTester(
            List.of(
                new BiChessTask(new Bishop(), new PopCount1Shift()),
                new BiChessTask(new Bishop(), new PopCount2Subtract()),
                new BiChessTask(new Bishop(), new PopCount3Cache())
            ),
            Path.of(Hw05Main.class.getClassLoader().getResource("4.Bitboard - Слон").toURI())
        );
        bishopTester.runTests();
        bishopTester.stop();

        // 5.Ферзь
        Tester<List<String>> queenTester = new StringListTester(
            List.of(
                new BiChessTask(new Queen(), new PopCount1Shift()),
                new BiChessTask(new Queen(), new PopCount2Subtract()),
                new BiChessTask(new Queen(), new PopCount3Cache())
            ),
            Path.of(Hw05Main.class.getClassLoader().getResource("5.Bitboard - Ферзь").toURI())
        );
        queenTester.runTests();
        queenTester.stop();
    }
}