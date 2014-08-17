package com.egorn.dribbble.ui.profile;

import android.os.Bundle;

import com.egorn.dribbble.R;
import com.egorn.dribbble.Utils;
import com.egorn.dribbble.data.models.Shot;
import com.egorn.dribbble.ui.SwipeBaseActivity;
import com.egorn.dribbble.ui.shots.ShotsFragment;

public class ProfileActivity extends SwipeBaseActivity implements ShotsFragment.OnShotClickedListener {
    public static final String PLAYER_ID = "player_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,
                            ProfileFragment.newInstance(getIntent().getIntExtra(PLAYER_ID, 0)))
                    .commit();
        }
    }

    public void setTitle(String text) {
        getActionBar().setTitle(text);
    }

    @Override
    public void onShotClicked(Shot shot) {
        Utils.openShot(this, shot);
    }
}
