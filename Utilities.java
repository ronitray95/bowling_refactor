import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class Utilities {

    public static String getCurrentDateString() {
        Calendar cal = Calendar.getInstance();
        return "" + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + " " + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_WEEK) + "/" + (cal.get(Calendar.YEAR) + 1900);
    }

    public static JButton createButton(String text, ActionListener actionListener) {
        JButton jb = new JButton(text);
        Insets buttonMargin = new Insets(4, 4, 4, 4);
        jb.setMargin(buttonMargin);
        if (actionListener != null)
            jb.addActionListener(actionListener);
        return jb;
    }
}