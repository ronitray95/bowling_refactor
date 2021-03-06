package views;
/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import models.LaneEvent;
import models.PinsetterEvent;

import javax.swing.*;

import views.PinSetterView;
import common.Lane;
import views.LaneView;
import common.Pinsetter;
import common.Util;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class LaneStatusView implements ActionListener, Observer {

    private final JPanel jp;
    private final JLabel curBowler;
    //private final JLabel foul;
    private final JLabel pinsDown;
    private final JButton viewLane;
    private final JButton viewPinSetter;
    private final JButton maintenance;
    private final PinSetterView psv;
    private final LaneView lv;
    private final Lane lane;
    int laneNum;
    boolean laneShowing;
    boolean psShowing;

    public LaneStatusView(Lane lane, int laneNum) {

        this.lane = lane;
        this.laneNum = laneNum;
        laneShowing = false;
        psShowing = false;
        psv = new PinSetterView(laneNum);
        Pinsetter ps = lane.getPinsetter();
        ps.addObserver(psv);
        lv = new LaneView(lane, laneNum);
        lane.addObserver(lv);

        jp = new JPanel();
        jp.setLayout(new FlowLayout());
        JLabel cLabel = new JLabel("Now Bowling: ");
        curBowler = new JLabel("(no one)");
        //JLabel fLabel = new JLabel("Foul: ");
        //foul = new JLabel(" ");
        JLabel pdLabel = new JLabel("Pins Down: ");
        pinsDown = new JLabel("0");

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        viewLane = Util.createButton("View Lane", this);
        JPanel viewLanePanel = new JPanel();
        viewLanePanel.setLayout(new FlowLayout());
        viewLanePanel.add(viewLane);

        viewPinSetter = Util.createButton("Pinsetter", this);
        JPanel viewPinSetterPanel = new JPanel();
        viewPinSetterPanel.setLayout(new FlowLayout());
        viewPinSetterPanel.add(viewPinSetter);

        maintenance = Util.createButton("Resume", this);
        maintenance.setBackground(Color.GREEN);
        JPanel maintenancePanel = new JPanel();
        maintenancePanel.setLayout(new FlowLayout());
        maintenancePanel.add(maintenance);

        viewLane.setEnabled(false);
        viewPinSetter.setEnabled(false);

        buttonPanel.add(viewLanePanel);
        buttonPanel.add(viewPinSetterPanel);
        buttonPanel.add(maintenancePanel);

        jp.add(cLabel);
        jp.add(curBowler);
//		jp.add( fLabel );
//		jp.add( foul );
        jp.add(pdLabel);
        jp.add(pinsDown);

        jp.add(buttonPanel);

    }

    public JPanel showLane() {
        return jp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (lane.isPartyAssigned()) {
            if (e.getSource().equals(viewPinSetter)) {
                if (!psShowing) {
                    psv.show();
                    psShowing = true;
                } else {
                    psv.hide();
                    psShowing = false;
                }
            }
        }
        if (e.getSource().equals(viewLane)) {
            if (lane.isPartyAssigned()) {
                JFrame frame = lv.getFrame();
                if (!laneShowing) {
                    frame.setVisible(true);
                    // lv.show();
                    laneShowing = true;
                } else {
                    frame.setVisible(false);
                    // lv.hide();
                    laneShowing = false;
                }
            }
        }
        if (e.getSource().equals(maintenance)) {
            if (lane.isPartyAssigned()) {
                lane.unPauseGame();
                maintenance.setBackground(Color.GREEN);
            }
        }
    }

    @Override
    public void update(Observable lane, Object event) {
        if (event instanceof LaneEvent) {
            curBowler.setText(((LaneEvent) event).getBowler().getNickName());
            if (((LaneEvent) event).isMechanicalProblem()) {
                maintenance.setBackground(Color.RED);
            }
            if (!((LaneEvent) event).isPartyAssigned) {
                viewLane.setEnabled(false);
                viewPinSetter.setEnabled(false);
            } else {
                viewLane.setEnabled(true);
                viewPinSetter.setEnabled(true);
            }
        } else if (event instanceof PinsetterEvent) {
            pinsDown.setText((new Integer(((PinsetterEvent) event).totalPinsDown())).toString());
        }
    }

//     public void update(PinsetterEvent pe) {
//         pinsDown.setText((new Integer(pe.totalPinsDown())).toString());
// //		foul.setText( ( new Boolean(pe.isFoulCommited()) ).toString() );

//     }
}