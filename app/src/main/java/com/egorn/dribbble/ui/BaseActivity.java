package com.egorn.dribbble.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * @author Egor N.
 */
public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(Color.parseColor("#181818"));
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#181818")));
    }
}
