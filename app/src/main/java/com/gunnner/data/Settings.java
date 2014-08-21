package com.gunnner.data;

/**
 * @author Egor N.
 */
public class Settings {
    private static boolean lowTrafficEnabled = false;

    public static boolean isLowTrafficEnabled() {
        return lowTrafficEnabled;
    }

    public static void setLowTrafficEnabled(boolean b) {
        lowTrafficEnabled = b;
    }
}
