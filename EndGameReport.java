/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import models.Bowler;
import models.Party;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class EndGameReport implements ActionListener, ListSelectionListener {

    private final JFrame win;
    private final JButton printButton;
    private final JButton finished;
    //private Vector myVector;
    private final Vector<String> retVal;
    private int result;
    private String selectedMember;

    public EndGameReport(String partyName, Party party) {

        result = 0;
        retVal = new Vector<>();
        win = new JFrame("End Game Report for " + partyName + "?");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 2));

        // Member Panel
        JPanel partyPanel = new JPanel();
        partyPanel.setLayout(new FlowLayout());
        partyPanel.setBorder(new TitledBorder("Party Members"));

        Vector<String> myVector = new Vector<>();
        for (Bowler bowler : party.getMembers()) {
            myVector.add(bowler.getNick());
        }
        JList<String> memberList = new JList<>(myVector);
        memberList.setFixedCellWidth(120);
        memberList.setVisibleRowCount(5);
        memberList.addListSelectionListener(this);
        JScrollPane partyPane = new JScrollPane(memberList);
        //        partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel.add(partyPane);

        partyPanel.add(memberList);

        // Button Panel
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));

//        Insets buttonMargin = new Insets(4, 4, 4, 4);

        printButton = Utilities.createButton("Print Report", this);//new JButton("Print Report");
//        printButton.setMargin(buttonMargin);
        JPanel printButtonPanel = new JPanel();
        printButtonPanel.setLayout(new FlowLayout());
//        printButton.addActionListener(this);
        printButtonPanel.add(printButton);

        finished = Utilities.createButton("Finished", this);
        JPanel finishedPanel = new JPanel();
        finishedPanel.setLayout(new FlowLayout());
        finishedPanel.add(finished);

        buttonPanel.add(printButton);
        buttonPanel.add(finished);

        // Clean up main panel
        colPanel.add(partyPanel);
        colPanel.add(buttonPanel);

        win.getContentPane().add("Center", colPanel);

        win.pack();

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.setVisible(true);

    }

    /*public static void main(String[] args) {
        Vector<Bowler> bowlers = new Vector<>();
        for (int i = 0; i < 4; i++) {
            bowlers.add(new Bowler("aaaaa", "aaaaa", "aaaaa"));
        }
        Party party = new Party(bowlers);
        String partyName = "wank";
        EndGameReport e = new EndGameReport(partyName, party);
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(printButton)) {
            //Add selected to the vector.
            retVal.add(selectedMember);
        }
        if (e.getSource().equals(finished)) {
            win.setVisible(false);
            result = 1;
        }

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        selectedMember = ((String) ((JList<?>) e.getSource()).getSelectedValue());
    }

    public Vector<String> getResult() {
        while (result == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println("Interrupted");
            }
        }
        return retVal;
    }

//    public void destroy() {
//        win.setVisible(false);
//    }
}