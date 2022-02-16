package models;

import java.util.HashMap;

public class ScoreData {
    private final int[][] cumlScore;
    private final HashMap<Bowler, int[]> score;
    private final int[] curScores;

    public ScoreData(int[][] theCumulScore, HashMap<Bowler, int[]> theScore, int[] theCurScores) {
        cumlScore = theCumulScore;
        score = theScore;
        curScores = theCurScores;
    }

    public int[][] getCumulScore() {
        return cumlScore;
    }

    public HashMap<Bowler, int[]> getScore() {
        return score;
    }

    public int[] getCurrentScores() {
        return curScores;
    }
}