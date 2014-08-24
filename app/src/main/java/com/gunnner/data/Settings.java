package com.gunnner.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Egor N.
 */
public class Settings {
    private static final String REDUCE_DATA_USAGE = "reduce_data_usage";
    private static boolean reduceDataUsage = false;

    public static boolean reduceDataUsage() {
        return reduceDataUsage;
    }

    public static void setReduceDataUsage(Context context, boolean b) {
        reduceDataUsage = b;
        save(context);
    }

    private static void save(Context context) {
        preferences(context).edit()
                .putBoolean(REDUCE_DATA_USAGE, reduceDataUsage)
                .commit();
    }

    public static void restore(Context context) {
        reduceDataUsage = preferences(context).getBoolean(REDUCE_DATA_USAGE, false);
    }

    private static SharedPreferences preferences(Context context) {
        return context.getSharedPreferences("general_settings", Context.MODE_PRIVATE);
    }
}
