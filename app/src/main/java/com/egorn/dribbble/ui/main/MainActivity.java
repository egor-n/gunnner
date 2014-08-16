package com.egorn.dribbble.ui.main;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;

import com.crashlytics.android.Crashlytics;
import com.egorn.dribbble.BuildConfig;
import com.egorn.dribbble.R;
import com.egorn.dribbble.data.PlayerController;
import com.egorn.dribbble.data.models.Shot;
import com.egorn.dribbble.ui.BaseActivity;
import com.egorn.dribbble.ui.drawer.NavigationDrawerFragment;
import com.egorn.dribbble.ui.shots.OpenedShotActivity;
import com.egorn.dribbble.ui.shots.ShotsFragment;
import com.egorn.dribbble.ui.widgets.InputDialog;

public class MainActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        ShotsFragment.OnShotClickedListener {
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Crashlytics.start(this);
        }
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:  // main
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new MainFragment())
                        .commit();
                break;
            case 1:  // my profile
                break;
            case 2:  // my shots
                if (PlayerController.isLoggedIn(this)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, ShotsFragment.newInstance(ShotsFragment.MY_SHOTS))
                            .commit();
                } else {
                    showInputDialog(ShotsFragment.newInstance(ShotsFragment.MY_SHOTS));
                }
                break;
            case 3:  // following
                if (PlayerController.isLoggedIn(this)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, ShotsFragment.newInstance(ShotsFragment.FOLLOWING))
                            .commit();
                } else {
                    showInputDialog(ShotsFragment.newInstance(ShotsFragment.FOLLOWING));
                }
                break;
            case 4:  // likes
                if (PlayerController.isLoggedIn(this)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, ShotsFragment.newInstance(ShotsFragment.LIKES))
                            .commit();
                } else {
                    showInputDialog(ShotsFragment.newInstance(ShotsFragment.LIKES));
                }
                break;
            case 5:  // settings
                break;
            case 6:  // about
                break;
        }
    }

    private void showInputDialog(final android.support.v4.app.Fragment fragment) {
        new InputDialog(this, new InputDialog.CustomDialogCallback() {
            @Override
            public void onConfirm(String username) {
                PlayerController.setName(MainActivity.this, username);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }

            @Override
            public void onCancel() {

            }
        }).show();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onShotClicked(Shot shot) {
        Intent intent = new Intent(this, OpenedShotActivity.class);
        intent.putExtra(OpenedShotActivity.SHOT, shot);
        startActivity(intent);
    }
}
