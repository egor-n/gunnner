package com.egorn.dribbble.ui.shots;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.egorn.dribbble.R;

public class ShotImageActivity extends FragmentActivity {
    public static final String SHOT_IMAGE_URL = "shot_image_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_image);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,
                            ShotImageFragment.newInstance(getIntent().getStringExtra(SHOT_IMAGE_URL)))
                    .commit();
        }
    }
}
