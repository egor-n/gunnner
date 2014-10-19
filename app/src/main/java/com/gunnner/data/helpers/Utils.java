package com.gunnner.data.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gunnner.R;
import com.gunnner.data.models.Shot;
import com.gunnner.ui.profile.ProfileActivity;
import com.gunnner.ui.shots.OpenedShotActivity;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * @author Egor N.
 */
public class Utils {
    private static ImageLoader imageLoader;

    public static void setInsets(Activity activity, View view) {
        if (lowerThanKitKat()
                || !activity.getResources().getBoolean(R.bool.translucent_navigation)) {
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
        if (lowerThanKitKat()
                || !activity.getResources().getBoolean(R.bool.translucent_navigation)) {
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
        if (lowerThanKitKat()
                || !activity.getResources().getBoolean(R.bool.translucent_navigation)) {
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
        if (lowerThanKitKat()
                || !activity.getResources().getBoolean(R.bool.translucent_navigation)) {
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

    public static boolean lowerThanKitKat() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT;
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

    public static void openShot(Context context, Shot shot) {
        Intent intent = new Intent(context, OpenedShotActivity.class);
        intent.putExtra(OpenedShotActivity.SHOT, shot);
        context.startActivity(intent);
    }

    public static ImageLoader getImageLoader(Context context) {
        if (imageLoader == null) {
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(imageLoaderConfiguration(context));
        }

        if (!imageLoader.isInited()) {
            imageLoader.init(imageLoaderConfiguration(context));
        }

        return imageLoader;
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null &&
                activity.getCurrentFocus() != null &&
                activity.getCurrentFocus().getWindowToken() != null) {
            InputMethodManager inputManager = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static DisplayImageOptions getDisplayImageOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.placeholder)
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder)
                .cacheOnDisk(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .resetViewBeforeLoading(true)
                .build();
    }

    public static void clearImageCache(Context context) {
        getImageLoader(context).clearMemoryCache();
        getImageLoader(context).clearDiskCache();
    }

    private static ImageLoaderConfiguration imageLoaderConfiguration(Context context) {
        return new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(3)
                .threadPriority(Thread.MIN_PRIORITY)
                .memoryCache(new WeakMemoryCache())
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .diskCache(new LruDiscCache(context.getCacheDir(), new HashCodeFileNameGenerator(), 50 * 1024 * 1024))
                .build();
    }
}
