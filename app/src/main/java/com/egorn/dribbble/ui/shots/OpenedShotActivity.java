package com.egorn.dribbble.ui.shots;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.egorn.dribbble.R;
import com.egorn.dribbble.data.models.Shot;

public class OpenedShotActivity extends FragmentActivity {
    public static final String SHOT = "shot";
    public static final String SHOT_ID = "shot_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);
        if (savedInstanceState == null) {
            if (getIntent().getExtras().containsKey(SHOT)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, OpenedShotFragment.newInstance((Shot) getIntent().getParcelableExtra(SHOT)))
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, OpenedShotFragment.newInstance(getIntent().getIntExtra(SHOT_ID, 0)))
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
