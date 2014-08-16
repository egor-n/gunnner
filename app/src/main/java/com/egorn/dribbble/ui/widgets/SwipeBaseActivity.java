package com.egorn.dribbble.ui.widgets;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * @author Egor N.
 */
public class SwipeBaseActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(Color.parseColor("#181818"));
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#181818")));
    }
}
