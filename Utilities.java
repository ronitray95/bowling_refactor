import java.util.*;

public class Utilities {
    public static String getCurrentDateString() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        
        String dateString = "" + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + " " + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_WEEK) + "/" + (cal.get(Calendar.YEAR) + 1900);
        return dateString;
    }
}