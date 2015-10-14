package com.gunnner.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gunnner.data.models.Shot;
import com.gunnner.ui.shots.ShotsFragment;

/**
 * @author Egor N.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ShotsFragment.newInstance(Shot.POPULAR);
            case 1:
                return ShotsFragment.newInstance(Shot.DEBUTS);
            case 2:
                return ShotsFragment.newInstance(Shot.PLAYOFFS);
        }
        return ShotsFragment.newInstance(Shot.DEBUTS);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "POPULAR";
            case 1:
                return "DEBUTS";
            case 2:
                return "PLAYOFFS";
        }
        return "POPULAR";
    }
}
