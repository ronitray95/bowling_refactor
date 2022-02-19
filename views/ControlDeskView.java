package views;
/* ControlDeskView.java
 *
 *  Version:
 *			$Id$
 *
 *  Revisions:
 * 		$Log$
 *
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.Utilities;

import common.ControlDesk;
import common.Lane;
import common.Util;
import models.ControlDeskEvent;

/**
 * Class for representation of the control desk
 */
public class ControlDeskView implements ActionListener, Observer {

    private final JButton addParty;
    private final JButton finished;
    private final JButton assign;
    private final JFrame win;
    private final JList<String> partyList;

    /**
     * The maximum  number of members in a party
     */
    private final int maxMembers;
    private final ControlDesk controlDesk;

    /**
     * Displays a GUI representation of the ControlDesk
     */
    public ControlDeskView(ControlDesk controlDesk, int maxMembers) {

        this.controlDesk = controlDesk;
        this.maxMembers = maxMembers;
        int numLanes = controlDesk.getNumLanes();

        win = new JFrame("Control Desk");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new BorderLayout());

        // Controls Panel
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(3, 1));
        controlsPanel.setBorder(new TitledBorder("Controls"));

        addParty = Util.createButton("Add Party", this);
        JPanel addPartyPanel = new JPanel();
        addPartyPanel.setLayout(new FlowLayout());
        //addParty.addActionListener(this);
        addPartyPanel.add(addParty);
        controlsPanel.add(addPartyPanel);

        assign = Util.createButton("Assign Lanes", this);
        JPanel assignPanel = new JPanel();
        assignPanel.setLayout(new FlowLayout());
        //assign.addActionListener(this);
        assignPanel.add(assign);
//		controlsPanel.add(assignPanel);

        finished = Util.createButton("Finished", this);
        JPanel finishedPanel = new JPanel();
        finishedPanel.setLayout(new FlowLayout());
        //finished.addActionListener(this);
        finishedPanel.add(finished);
        controlsPanel.add(finishedPanel);

        // Lane Status Panel
        JPanel laneStatusPanel = new JPanel();
        laneStatusPanel.setLayout(new GridLayout(numLanes, 1));
        laneStatusPanel.setBorder(new TitledBorder("Lane Status"));

        HashSet<Lane> lanes = controlDesk.getLanes();
        int laneCount = 0;
        for (Lane curLane : lanes) {
            LaneStatusView laneStat = new LaneStatusView(curLane, (laneCount + 1));
            curLane.addObserver(laneStat);
            curLane.getPinsetter().addObserver(laneStat);
            JPanel lanePanel = laneStat.showLane();
            lanePanel.setBorder(new TitledBorder("Lane" + ++laneCount));
            laneStatusPanel.add(lanePanel);
        }

        // Party Queue Panel
        JPanel partyPanel = new JPanel();
        partyPanel.setLayout(new FlowLayout());
        partyPanel.setBorder(new TitledBorder("Party Queue"));

        Vector<String> empty = new Vector<>();
        empty.add("(Empty)");

        partyList = new JList<>(empty);
        partyList.setFixedCellWidth(120);
        partyList.setVisibleRowCount(10);
        JScrollPane partyPane = new JScrollPane(partyList);
        partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel.add(partyPane);
        //		partyPanel.add(partyList);

        // Clean up main panel
        colPanel.add(controlsPanel, "East");
        colPanel.add(laneStatusPanel, "Center");
        colPanel.add(partyPanel, "West");

        win.getContentPane().add("Center", colPanel);

        win.pack();

        /* Close program when this window closes */
        win.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(((screenSize.width) / 2) - ((win.getSize().width) / 2), ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.setVisible(true);

    }

    /**
     * Handler for actionEvents
     *
     * @param e the ActionEvent that triggered the handler
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addParty)) {
            new AddPartyView(this, maxMembers);
            //AddPartyView addPartyWin = new AddPartyView(this, maxMembers);
        }
        if (e.getSource().equals(assign)) {
            controlDesk.assignLane();
        }
        if (e.getSource().equals(finished)) {
            win.setVisible(false);
            System.exit(0);
        }
    }

    /**
     * Receive a new party from andPartyView.
     *
     * @param addPartyView the AddPartyView that is providing a new party
     */

    public void updateAddParty(AddPartyView addPartyView) {
        controlDesk.addPartyQueue(addPartyView.getParty());
    }

    /*
     * Receive a broadcast from a ControlDesk
     *
     * @param ce the ControlDeskEvent that triggered the handler
     * /
    public void receiveControlDeskEvent(ControlDeskEvent ce) {
        partyList.setListData(ce.getPartyQueue());
    }*/

    @Override
    public void update(Observable lane, Object event) {
        partyList.setListData(((ControlDeskEvent) event).getPartyQueue());
    }
}