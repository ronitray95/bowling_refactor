import java.util.Calendar;

public class Utilities {
    public static String getCurrentDateString() {
        Calendar cal = Calendar.getInstance();

        return "" + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + " " + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_WEEK) + "/" + (cal.get(Calendar.YEAR) + 1900);
    }
}