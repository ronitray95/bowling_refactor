/*  $Id$
 *
 *  Revisions:
 *    $Log: LaneEvent.java,v $
 *    Revision 1.6  2003/02/16 22:59:34  ???
 *    added mechnanical problem flag
 *
 *    Revision 1.5  2003/02/02 23:55:31  ???
 *    Many many changes.
 *
 *    Revision 1.4  2003/02/02 22:44:26  ???
 *    More data.
 *
 *    Revision 1.3  2003/02/02 17:49:31  ???
 *    Modified.
 *
 *    Revision 1.2  2003/01/30 21:21:07  ???
 *    *** empty log message ***
 *
 *    Revision 1.1  2003/01/19 22:12:40  ???
 *    created laneevent and laneobserver
 *
 *
 */
package models;

public class LaneEvent {

    private final Party p;
    public boolean isPartyAssigned;
    // int frame;
    FrameInfo frameInfo;
    // int ball;
    Bowler bowler;
    ScoreData scoreData;
    // int index;
    // int frameNum;
    boolean mechProb;

    public LaneEvent(Party pty,FrameInfo frameInfoVal, Bowler theBowler, ScoreData scoreDataVal, boolean mechProblem, boolean isPartyAssignedVal) {
        p = pty;
        // index = theIndex;
        frameInfo = frameInfoVal;
        bowler = theBowler;
        scoreData = scoreDataVal;
        // frameNum = theFrameNum;
        // ball = theBall;
        mechProb = mechProblem;
        isPartyAssigned = isPartyAssignedVal;
    }

    public boolean isMechanicalProblem() {
        return mechProb;
    }

    public FrameInfo getFrameInfo(){
        return frameInfo;
    }

    // public int getFrameNum() {
    //     return frameNum;
    // }

    // public int getIndex() {
    //     return index;
    // }

    // // public int getFrame() {
    // //     return frame;
    // // }

    // public int getBall() {
    //     return ball;
    // }

    public ScoreData getScoreData() {
        return scoreData;
    }

    public Party getParty() {
        return p;
    }

    public Bowler getBowler() {
        return bowler;
    }

}