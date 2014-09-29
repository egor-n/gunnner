package com.gunnner.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.gunnner.R;

/**
 * @author Egor N.
 */
public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#181818")));
        getActionBar().setIcon(android.R.color.transparent);
        getActionBar().setLogo(R.drawable.logo);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setTitle("");
    }
}
