package views;

/*
 *  constructs a prototype Lane View
 *
 */

import models.Bowler;
import models.LaneEvent;
import models.Party;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import common.Lane;
import common.Util;

public class LaneView implements ActionListener, Observer {

    JFrame frame;
    Container cpanel;
    Vector<Bowler> bowlers;
    //int cur;
    //Iterator bowlIt;
    JPanel[][] balls;
    JLabel[][] ballLabel;
    JPanel[][] scores;
    JLabel[][] scoreLabel;
    JPanel[][] ballGrid;
    JPanel[] pins;
    JButton maintenance;
    Lane lane;
    //    private int roll;
    private boolean initDone;// = true;

    public LaneView(Lane lane, int laneNum) {

        this.lane = lane;
        initDone = true;
        frame = new JFrame("Lane " + laneNum + ":");
        cpanel = frame.getContentPane();
        cpanel.setLayout(new BorderLayout());

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
            }
        });

        cpanel.add(new JPanel());

    }

    public JFrame getFrame(){
        return frame;
    }
    
    private JPanel makeFrame(Party party) {

        initDone = false;
        bowlers = party.getMembers();
        int numBowlers = bowlers.size();

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 1));

        balls = new JPanel[numBowlers][23];
        ballLabel = new JLabel[numBowlers][23];
        scores = new JPanel[numBowlers][10];
        scoreLabel = new JLabel[numBowlers][10];
        ballGrid = new JPanel[numBowlers][10];
        pins = new JPanel[numBowlers];

        for (int i = 0; i != numBowlers; i++) {
            for (int j = 0; j != 23; j++) {
                ballLabel[i][j] = new JLabel(" ");
                balls[i][j] = new JPanel();
                balls[i][j].setBorder(
                        BorderFactory.createLineBorder(Color.BLACK));
                balls[i][j].add(ballLabel[i][j]);
            }
        }

        for (int i = 0; i != numBowlers; i++) {
            for (int j = 0; j != 9; j++) {
                ballGrid[i][j] = new JPanel();
                ballGrid[i][j].setLayout(new GridLayout(0, 3));
                ballGrid[i][j].add(new JLabel("  "), BorderLayout.EAST);
                ballGrid[i][j].add(balls[i][2 * j], BorderLayout.EAST);
                ballGrid[i][j].add(balls[i][2 * j + 1], BorderLayout.EAST);
            }
            int j = 9;
            ballGrid[i][j] = new JPanel();
            ballGrid[i][j].setLayout(new GridLayout(0, 3));
            ballGrid[i][j].add(balls[i][2 * j]);
            ballGrid[i][j].add(balls[i][2 * j + 1]);
            ballGrid[i][j].add(balls[i][2 * j + 2]);
        }

        for (int i = 0; i != numBowlers; i++) {
            pins[i] = new JPanel();
            pins[i].setBorder(BorderFactory.createTitledBorder(bowlers.get(i).getNickName()));
            pins[i].setLayout(new GridLayout(0, 10));
            for (int k = 0; k != 10; k++) {
                scores[i][k] = new JPanel();
                scoreLabel[i][k] = new JLabel("  ", SwingConstants.CENTER);
                scores[i][k].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                scores[i][k].setLayout(new GridLayout(0, 1));
                scores[i][k].add(ballGrid[i][k], BorderLayout.EAST);
                scores[i][k].add(scoreLabel[i][k], BorderLayout.SOUTH);
                pins[i].add(scores[i][k], BorderLayout.EAST);
            }
            panel.add(pins[i]);
        }

        initDone = true;
        return panel;
    }

    @Override
    public void update(Observable lane, Object le) {
        if (((LaneEvent) le).isPartyAssigned) {
            int numBowlers = ((LaneEvent) le).getParty().getMembers().size();
            while (!initDone) {
                //System.out.println("chillin' here.");
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.err.println("LaneView Error occurred " + e);
                }
            }

            if (((LaneEvent) le).getFrameInfo().getFrameNum() == 1
                    && ((LaneEvent) le).getFrameInfo().getBall() == 0
                    && ((LaneEvent) le).getFrameInfo().getIndex() == 0) {
                System.out.println("Making the frame.");
                cpanel.removeAll();
                cpanel.add(makeFrame(((LaneEvent) le).getParty()), "Center");

                // Button Panel
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout());


                maintenance = Util.createButton("Maintenance Call", this);
                JPanel maintenancePanel = new JPanel();
                maintenancePanel.setLayout(new FlowLayout());
                maintenancePanel.add(maintenance);

                buttonPanel.add(maintenancePanel);

                cpanel.add(buttonPanel, "South");

                frame.pack();
            }

            int[][] lescores = ((LaneEvent) le).getScoreData().getCumulScore();
            for (int k = 0; k < numBowlers; k++) {
                for (int i = 0; i <= ((LaneEvent) le).getFrameInfo().getFrameNum() - 1; i++) {
                    if (lescores[k][i] != 0)
                        scoreLabel[k][i].setText(
                                (new Integer(lescores[k][i])).toString());
                }
                for (int i = 0; i < 21; i++) {
                    if (((int[]) ((LaneEvent) le).getScoreData().getScore().get(bowlers.get(k)))[i] != -1)
                        if (((int[]) ((LaneEvent) le).getScoreData().getScore()
                                .get(bowlers.get(k)))[i] == 10 && (i % 2 == 0 || i == 19))
                            ballLabel[k][i].setText("X");
                        else if (i > 0 && ((int[]) ((LaneEvent) le).getScoreData().getScore().get(bowlers.get(k)))[i]
                                + ((int[]) ((LaneEvent) le).getScoreData().getScore()
                                .get(bowlers.get(k)))[i - 1] == 10 && i % 2 == 1)
                            ballLabel[k][i].setText("/");
                        else if (((int[]) ((LaneEvent) le).getScoreData().getScore().get(bowlers.get(k)))[i] == -2)
                            ballLabel[k][i].setText("F");
                        else
                            ballLabel[k][i].setText(
                                    (new Integer(((int[]) ((LaneEvent) le).getScoreData().getScore()
                                            .get(bowlers.get(k)))[i])).toString());
                }
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(maintenance))
            lane.pauseGame();
    }
}