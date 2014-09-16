package com.gunnner.ui.main;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;

import com.crashlytics.android.Crashlytics;
import com.gunnner.BuildConfig;
import com.gunnner.R;
import com.gunnner.data.PlayerController;
import com.gunnner.data.Settings;
import com.gunnner.data.helpers.Utils;
import com.gunnner.data.models.Shot;
import com.gunnner.ui.BaseActivity;
import com.gunnner.ui.SettingsFragment;
import com.gunnner.ui.drawer.NavigationDrawerFragment;
import com.gunnner.ui.profile.ProfileFragment;
import com.gunnner.ui.shots.SearchController;
import com.gunnner.ui.shots.ShotsController;
import com.gunnner.ui.shots.ShotsFragment;
import com.gunnner.ui.widgets.InputDialog;

import java.util.ArrayList;

public class MainActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        ShotsFragment.OnShotClickedListener, InputDialog.CustomDialogCallback {
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.restore(this);
        if (!BuildConfig.DEBUG) {
            Crashlytics.start(this);
        }
        setContentView(R.layout.activity_main);

        SearchController controller = SearchController.getInstance();
        controller.search("pixate", new ShotsController.OnShotsLoadedListener() {
            @Override
            public void onShotsLoaded(ArrayList<Shot> shots) {

            }

            @Override
            public void onShotsError() {

            }
        });

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

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
                if (PlayerController.isLoggedIn(this)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,
                                    ProfileFragment.newInstance(PlayerController.getName()))
                            .commit();
                } else {
                    showInputDialog(-1);
                }
                break;
            case 2:  // my shots
                if (PlayerController.isLoggedIn(this)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,
                                    ShotsFragment.newInstance(ShotsFragment.MY_SHOTS))
                            .commit();
                } else {
                    showInputDialog(ShotsFragment.MY_SHOTS);
                }
                break;
            case 3:  // following
                if (PlayerController.isLoggedIn(this)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,
                                    ShotsFragment.newInstance(ShotsFragment.FOLLOWING))
                            .commit();
                } else {
                    showInputDialog(ShotsFragment.FOLLOWING);
                }
                break;
            case 4:  // likes
                if (PlayerController.isLoggedIn(this)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,
                                    ShotsFragment.newInstance(ShotsFragment.LIKES))
                            .commit();
                } else {
                    showInputDialog(ShotsFragment.LIKES);
                }
                break;
            case 5:  // settings
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SettingsFragment())
                        .commit();
                break;
        }
    }

    private void showInputDialog(int type) {
        dialog = new InputDialog(this, type, this);
        dialog.show();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
        }
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
        Utils.openShot(this, shot);
    }

    @Override
    public void onConfirm(String username, int type) {
        PlayerController.setName(MainActivity.this, username);
        Fragment fragment;
        if (type == -1) {
            fragment = ProfileFragment.newInstance(username);
        } else {
            fragment = ShotsFragment.newInstance(type);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onCancel() {

    }
}
