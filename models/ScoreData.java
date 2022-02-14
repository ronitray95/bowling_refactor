package models;
import java.util.*;


public class ScoreData {
    int[][] cumlScore;
    HashMap<Bowler, int[]> score;
    int[] curScores;
    public ScoreData(int[][] theCumulScore, HashMap<Bowler, int[]> theScore, int[] theCurScores) {
        cumlScore = theCumulScore;
        score = theScore;
        curScores = theCurScores;
    }

    public int[][] getCumulScore(){
        return cumlScore;
    }

    public HashMap<Bowler, int[]> getScore(){
        return score;
    }

    public int[] getCurrentScores(){
        return curScores;
    }
}