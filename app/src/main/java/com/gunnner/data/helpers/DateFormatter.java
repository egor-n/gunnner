package com.gunnner.data.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Egor N.
 */
public class DateFormatter {
    private static DateFormat targetFormat;

    public static String formatDate(String dateString) {
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'"); // 2015-10-10T09:26:55Z
        Date date;
        try {
            date = sourceFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return "parse error";
        }

        if (targetFormat == null) {
            targetFormat = DateFormat.getDateInstance();
        }
        return targetFormat.format(date);
    }
}
