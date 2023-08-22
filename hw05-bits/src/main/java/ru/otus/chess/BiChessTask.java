package ru.otus.chess;

import ru.otus.popcnt.PopCount;
import ru.otus.tester.Task;

import java.math.BigInteger;
import java.util.List;

public class BiChessTask implements Task<List<String>> {

    private final BiChess biChess;
    private final PopCount popCount;

    public BiChessTask(BiChess biChess, PopCount popCount) {
        this.biChess = biChess;
        this.popCount = popCount;
    }

    @Override
    public List<String> run(List<String> data) {
        int position = Integer.parseInt(data.get(0));
        BigInteger moveMask = biChess.moveMask(position);
        int popcnt = popCount.popcnt(moveMask);
        return List.of(Long.toString(popcnt), moveMask.toString());
    }

    @Override
    public String name() {
        return "BiChess: " + biChess.getClass().getSimpleName() + "; PopCount: " + popCount.getClass().getSimpleName();
    }

}
