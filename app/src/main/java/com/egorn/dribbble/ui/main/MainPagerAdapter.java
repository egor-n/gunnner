package com.egorn.dribbble.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.egorn.dribbble.data.models.Shot;
import com.egorn.dribbble.ui.shots.ShotsFragment;

/**
 * @author Egor N.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ShotsFragment.newInstance(Shot.DEBUTS);
            case 1:
                return ShotsFragment.newInstance(Shot.EVERYONE);
            case 2:
                return ShotsFragment.newInstance(Shot.POPULAR);
        }
        return ShotsFragment.newInstance(Shot.POPULAR);
    }

    @Override public int getCount() {
        return 3;
    }

    @Override public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "DEBUTS";
            case 1:
                return "EVERYONE";
            case 2:
                return "POPULAR";
        }
        return "POPULAR";
    }
}
