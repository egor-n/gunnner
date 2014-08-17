package com.egorn.dribbble.ui.shots;

import android.os.Bundle;

import com.egorn.dribbble.R;
import com.egorn.dribbble.data.models.Shot;
import com.egorn.dribbble.ui.SwipeBaseActivity;

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
