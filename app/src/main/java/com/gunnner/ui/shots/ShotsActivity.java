package com.gunnner.ui.shots;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gunnner.R;
import com.gunnner.data.helpers.Utils;
import com.gunnner.data.models.Shot;
import com.gunnner.ui.SwipeBaseActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Egor N.
 */
public class ShotsActivity extends SwipeBaseActivity implements
        ShotsController.OnShotsLoadedListener, ShotsFragment.OnShotClickedListener {
    @InjectView(R.id.progress_bar) ProgressBar mProgressBar;
    @InjectView(R.id.no_shots) TextView mNoShots;

    String mQuery = "";

    SearchController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shots);
        ButterKnife.inject(this);

        mQuery = getIntent().getStringExtra("query");
        getSupportActionBar().setTitle(mQuery);

        mController = SearchController.getInstance();

        if (savedInstanceState == null) {
            mProgressBar.setVisibility(View.VISIBLE);
            mController.init(mQuery, this);
        }
    }

    @Override
    public void onShotsLoaded(ArrayList<Shot> shots) {
        if (isFinishing()) {
            return;
        }
        mProgressBar.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ShotsFragment.newInstance(shots, ShotsFragment.SEARCH))
                .commit();
    }

    @Override
    public void onShotsError() {
        mProgressBar.setVisibility(View.GONE);
        mNoShots.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShotClicked(Shot shot) {
        Utils.openShot(this, shot);
    }

    public void loadMore(ShotsController.OnShotsLoadedListener listener) {
        mController.loadMore(mQuery, listener);
    }
}
