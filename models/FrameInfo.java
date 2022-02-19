package models;

public class FrameInfo {
    int index;
    int frameNum;
    int ball;

    public FrameInfo(int indexVal, int frameNumVal, int ballVal) {
        index= indexVal;
        frameNum = frameNumVal;
        ball = ballVal;
    }

    public int getFrameNum() {
        return frameNum;
    }

    public int getIndex() {
        return index;
    }

    public int getBall() {
        return ball;
    }

}
