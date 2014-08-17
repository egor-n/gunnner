package com.egorn.dribbble;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;

import com.egorn.dribbble.ui.profile.ProfileActivity;

/**
 * @author Egor N.
 */
public class Utils {
    public static void setInsets(Activity activity, View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();

        view.setPadding(
                view.getPaddingLeft(),
                view.getPaddingTop() + config.getPixelInsetTop(true),
                view.getPaddingRight() + config.getPixelInsetRight(),
                view.getPaddingBottom()
        );
    }

    public static void setBottomInsets(Activity activity, View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();

        view.setPadding(
                view.getPaddingLeft(),
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom() + config.getPixelInsetBottom()
        );
    }

    public static int dpToPixels(Context context, float dpValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, metrics);
    }

    public static void openProfile(Context context, int playerId) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(ProfileActivity.PLAYER_ID, playerId);
        context.startActivity(intent);
    }

    public static int getScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
