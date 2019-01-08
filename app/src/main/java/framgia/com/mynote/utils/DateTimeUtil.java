package framgia.com.mynote.utils;

import android.text.format.DateFormat;

import java.util.Date;

public class DateTimeUtil {
    public static final String PATTERN = "MM/dd/yyyy hh:mm";

    public static String convertLongToDate(long time) {
        return DateFormat.format(PATTERN, new Date(time)).toString();
    }
}
