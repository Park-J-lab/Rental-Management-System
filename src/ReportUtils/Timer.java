package ReportUtils;

/*
* This class is used to generate the timestamp, and get the reformatted date of time
* */

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer {
    static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static long digitizeDate = System.currentTimeMillis();
    static Date date = new Date(digitizeDate);

    // return timestamp
    static String getTimer() { return String.valueOf(digitizeDate); }

    // return date "yyyy-MM-dd"
    static String getDate() {
        return dateFormatter.format(date);
    }

    // return time "yyyy-MM-dd HH:mm:ss"
    static String getTime() {
        Date date = new Date(digitizeDate);
        return timeFormatter.format(date);
    }
}