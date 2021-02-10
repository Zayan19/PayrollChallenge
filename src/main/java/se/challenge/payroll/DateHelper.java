package se.challenge.payroll;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static Date GetDateKey(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int newDay = day <= 15 ? 1 : 16;
        cal.set(Calendar.DAY_OF_MONTH, newDay);
        return new Date(cal.getTimeInMillis());
    }

    public static String GetEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day <= 15) {
            cal.set(Calendar.DAY_OF_MONTH, 15);
            return GetDateAsString(new Date(cal.getTimeInMillis()));
        }
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return GetDateAsString(new Date(cal.getTimeInMillis()));
    }

    public static String GetDateAsString(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return dateFormatter.format(calendar.getTime());
    }

}
