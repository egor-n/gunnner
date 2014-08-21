package com.gunnner.ui.shots;

import android.os.Bundle;

import com.gunnner.R;
import com.gunnner.data.models.Shot;
import com.gunnner.ui.SwipeBaseActivity;

public class OpenedShotActivity extends SwipeBaseActivity {
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
}
