package com.egorn.dribbble;

import android.app.Activity;
import android.view.View;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * @author Egor N.
 */
public class Utils {
    public static void setInsets(Activity activity, View view) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();

        view.setPadding(
                view.getPaddingLeft(),
                view.getPaddingTop() + config.getPixelInsetTop(true),
                view.getPaddingRight() + config.getPixelInsetRight(),
                view.getPaddingBottom() + config.getPixelInsetBottom()
        );
    }

    public static void setTopInsets(Activity activity, View view) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();

        view.setPadding(
                view.getPaddingLeft(),
                view.getPaddingTop() + config.getPixelInsetTop(true),
                view.getPaddingRight(),
                view.getPaddingBottom()
        );
    }

    public static void setTopRightInsets(Activity activity, View view) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();

        view.setPadding(
                view.getPaddingLeft(),
                view.getPaddingTop() + config.getPixelInsetTop(true),
                view.getPaddingRight() + config.getPixelInsetRight(),
                view.getPaddingBottom()
        );
    }

    public static void setBottomRightInsets(Activity activity, View view) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();

        view.setPadding(
                view.getPaddingLeft(),
                view.getPaddingTop(),
                view.getPaddingRight() + config.getPixelInsetRight(),
                view.getPaddingBottom() + config.getPixelInsetBottom()
        );
    }
}
