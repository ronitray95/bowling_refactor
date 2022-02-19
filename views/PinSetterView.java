package views;

/*
 * PinSetterView/.java
 *
 * Version:
 *   $Id$
 *
 * Revision:
 *   $Log$
 */

import models.PinsetterEvent;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * constructs a prototype PinSetter GUI
 */
public class PinSetterView implements Observer {

    private final Vector<JLabel> pinVect = new Vector<>();
    private final JPanel secondRoll;
    private final JFrame frame;

    /**
     * Constructs a Pin Setter GUI displaying which roll it is with
     * yellow boxes along the top (1 box for first roll, 2 boxes for second)
     * and displays the pins as numbers in this format:
     * <p>
     * 7   8   9   10
     * 4   5   6
     * 2   3
     * 1
     */

    public PinSetterView(int laneNum) {
        frame = new JFrame("Lane " + laneNum + ":");
        Container cpanel = frame.getContentPane();
        JPanel pins = new JPanel();
        pins.setLayout(new GridLayout(4, 7));
        //********************Top of GUI indicates first or second roll
        JPanel top = new JPanel();
        JPanel firstRoll = new JPanel();
        firstRoll.setBackground(Color.yellow);

        secondRoll = new JPanel();
        secondRoll.setBackground(Color.black);
        top.add(firstRoll, BorderLayout.WEST);
        top.add(secondRoll, BorderLayout.EAST);
        //******************************************************************

        //**********************Grid of the pins**************************
        JPanel[] jPanels = new JPanel[10];
        JLabel[] jLabels = new JLabel[10];

        for (int i = 0; i < 10; i++) {
            jPanels[i] = new JPanel();
            jLabels[i] = new JLabel(String.valueOf(i + 1));
            jPanels[i].add(jLabels[i]);
            //This Vector will keep references to the pin labels to show which ones have fallen.
            pinVect.add(jLabels[i]);
        }

        //******************************Fourth Row**************

        pins.add(jPanels[6]);
        pins.add(new JPanel());
        pins.add(jPanels[7]);
        pins.add(new JPanel());
        pins.add(jPanels[8]);
        pins.add(new JPanel());
        pins.add(jPanels[9]);

        //*****************************Third Row***********

        pins.add(new JPanel());
        pins.add(jPanels[3]);
        pins.add(new JPanel());
        pins.add(jPanels[4]);
        pins.add(new JPanel());
        pins.add(jPanels[5]);

        //*****************************Second Row**************

        pins.add(new JPanel());
        pins.add(new JPanel());
        pins.add(new JPanel());
        pins.add(jPanels[1]);
        pins.add(new JPanel());
        pins.add(jPanels[2]);
        pins.add(new JPanel());
        pins.add(new JPanel());

        //******************************First Row*****************

        pins.add(new JPanel());
        pins.add(new JPanel());
        pins.add(new JPanel());
        pins.add(jPanels[0]);
        pins.add(new JPanel());
        pins.add(new JPanel());
        pins.add(new JPanel());
        //*********************************************************

        top.setBackground(Color.black);
        cpanel.add(top, BorderLayout.NORTH);
        pins.setBackground(Color.black);
        pins.setForeground(Color.yellow);
        cpanel.add(pins, BorderLayout.CENTER);
        frame.pack();


//	frame.show();
    }

    /*public static void main(String[] args) {
        PinSetterView pg = new PinSetterView(1);
    }*/

    /**
     * This method receives a pinsetter event.  The event is the current
     * state of the PinSetter and the method changes how the GUI looks
     * accordingly.  When pins are "knocked down" the corresponding label
     * is grayed out.  When it is the second roll, it is indicated by the
     * appearance of a second yellow box at the top.
     *
     * @param pinsetterEvent The state of the pinsetter is sent in this event.
     */
    @Override
    public void update(Observable pinsetterObservable, Object pinsetterEvent) {
        PinsetterEvent pe = (PinsetterEvent)pinsetterEvent;
        if (!(pe.isFoulCommited())) {
            JLabel tempPin;// = new JLabel();
            for (int c = 0; c < 10; c++) {
                boolean pin = pe.pinKnockedDown(c);
                tempPin = pinVect.get(c);
                if (pin)
                    tempPin.setForeground(Color.lightGray);
            }
        }
        if (pe.getThrowNumber() == 1)
            secondRoll.setBackground(Color.yellow);
        if (pe.pinsDownOnThisThrow() == -1) {
            for (int i = 0; i != 10; i++)
                pinVect.get(i).setForeground(Color.black);
            secondRoll.setBackground(Color.black);
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }

}