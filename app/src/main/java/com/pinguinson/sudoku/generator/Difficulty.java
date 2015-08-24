package com.pinguinson.sudoku.generator;

/**
 * Created by pinguinson on 8/24/15.
 */
public enum Difficulty {
    //     lvl  itMin       itMax             gMin gMax
    BEGINNER(1, 50, 200, 48, 56),
    EASY(2, 200, 1_000, 36, 48),
    MEDIUM(3, 1_000, 10_000, 32, 36),
    HARD(4, 10_000, 100_000, 28, 32),
    EVIL(5, 100_000, Integer.MAX_VALUE, 17, 28);

    private final int level;
    private final int iterationsLowerBound;
    private final int iterationsHigherBound;
    private final int givenLowerBound;
    private final int givenHigherBound;

    Difficulty(int level, int iterationsLowerBound, int iterationsHigherBound, int givenLowerBound, int givenHigherBound) {
        this.level = level;
        this.iterationsLowerBound = iterationsLowerBound;
        this.iterationsHigherBound = iterationsHigherBound;
        this.givenLowerBound = givenLowerBound;
        this.givenHigherBound = givenHigherBound;
    }

    public boolean fitsIterations(int iterations) {
        return (iterationsLowerBound <= iterations && iterations < iterationsHigherBound);
    }

    public boolean fitsGiven(int given) {
        return (givenLowerBound <= given && given < givenHigherBound);
    }

    public final int getLevel() {
        return level;
    }

    public int getIterationsLowerBound() {
        return iterationsLowerBound;
    }

    public int getIterationsHigherBound() {
        return iterationsHigherBound;
    }

    public int getGivenLowerBound() {
        return givenLowerBound;
    }

    public int getGivenHigherBound() {
        return givenHigherBound;
    }
}
