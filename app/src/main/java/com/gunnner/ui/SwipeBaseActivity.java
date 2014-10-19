package com.gunnner.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * @author Egor N.
 */
public class SwipeBaseActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpActionBar();
    }

    private void setUpActionBar() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#181818")));
        getSupportActionBar().setLogo(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
