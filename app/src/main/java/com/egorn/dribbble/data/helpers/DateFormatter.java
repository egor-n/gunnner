package com.egorn.dribbble.data.helpers;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Egor N.
 */
public class DateFormatter {
    private static DateFormat targetFormat;

    public static String formatDate(Context context, String dateString) {
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"); // 2012/06/30 22:24:50 -0400
        Date date;
        try {
            date = sourceFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return "parse error";
        }

        if (targetFormat == null) {
            targetFormat = android.text.format.DateFormat.getDateFormat(context);
        }
        return targetFormat.format(date);
    }
}
