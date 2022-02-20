package common;
import models.*;

import java.util.*;

public class LaneGameFinished {
    public static boolean handle(Party party, Bowler thisBowler, int[][] finalScores, int gameNumber){
        EndGamePrompt egp = new EndGamePrompt(party.getMembers().get(0).getNickName() + "'s Party");
        int result = egp.getResult();
        egp.destroy();
        egp = null;
        if(result == 2){
        Vector<String> printVector;
        EndGameReport egr = new EndGameReport(party.getMembers().get(0).getNickName() + "'s Party", party);
        printVector = egr.getResult();
        Iterator<Bowler> scoreIt = party.getMembers().iterator();

        int myIndex = 0;
        while (scoreIt.hasNext()) {
            thisBowler = scoreIt.next();
            ScoreReport sr = new ScoreReport(thisBowler, finalScores[myIndex++], gameNumber);
            sr.sendEmail(thisBowler.getEmail());
                for (String s : printVector) {
                if (Objects.equals(thisBowler.getNickName(), s)) {
                    System.out.println("Printing " + thisBowler.getNickName());
                      sr.sendPrintout();
                    }
                }
            }
        return false;
        } else {
            return true;
        }
    }
}