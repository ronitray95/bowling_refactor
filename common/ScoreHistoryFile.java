package common;
/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import models.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;
import java.util.*;

public class ScoreHistoryFile implements Observer {

	private static final String SCOREHISTORY_DAT = "SCOREHISTORY.DAT";

	public static void addScore(String nick, String score) throws IOException {
        String date = Util.getCurrentDateString();
        String data = nick + "\t" + date + "\t" + score + "\n";
        RandomAccessFile out = new RandomAccessFile(SCOREHISTORY_DAT, "rw");
        out.skipBytes((int) out.length());
        out.writeBytes(data);
        out.close();
    }

    public static Vector<Score> getScores(String nick) throws IOException {
        Vector<Score> scores = new Vector<>();
        BufferedReader in = new BufferedReader(new FileReader(SCOREHISTORY_DAT));
        String data;
        while ((data = in.readLine()) != null) {
            // File format is nick\tfname\te-mail
            String[] scoredata = data.split("\t");
            //"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
            if (nick.equals(scoredata[0])) {
                scores.add(new Score(scoredata[0], scoredata[1], scoredata[2]));
            }
		}
		return scores;
	}

    public void update(Observable planeObservable, Object laneEvent) {
        LaneEvent ev = (LaneEvent)laneEvent; 
        try {
            addScore(ev.getBowler().getNickName(), Integer.toString(ev.getScoreData().getCumulScore()[ev.getFrameInfo().getIndex()][9]));
            // ScoreHistoryFile.addScore(currentThrower.getNickName(), Integer.toString(cumulScores[bowlIndex][9]));
        } catch (Exception e) {
            System.err.println("Exception in addScore. " + e);
        }
    }
}