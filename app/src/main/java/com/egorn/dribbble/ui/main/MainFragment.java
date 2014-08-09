package com.egorn.dribbble.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egorn.dribbble.R;
import com.egorn.dribbble.ui.widgets.tabs.SlidingTabLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainFragment extends Fragment {
    private static final String CURRENT_TAB = "current_tab";

    @InjectView(R.id.sliding_tabs) SlidingTabLayout mSlidingTabs;
    @InjectView(R.id.view_pager) ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager.setAdapter(new MainPagerAdapter(getChildFragmentManager()));
        prepareTabs();
    }

    private void prepareTabs() {
        mSlidingTabs.setViewPager(mViewPager);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_TAB, mViewPager.getCurrentItem());
    }
}
