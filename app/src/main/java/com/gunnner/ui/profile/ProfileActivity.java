package com.gunnner.ui.profile;

import android.os.Bundle;

import com.gunnner.R;
import com.gunnner.data.helpers.Utils;
import com.gunnner.data.models.Shot;
import com.gunnner.ui.SwipeBaseActivity;
import com.gunnner.ui.shots.ShotsFragment;

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
