package com.gunnner.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.gunnner.R;
import com.gunnner.data.helpers.Utils;
import com.gunnner.ui.shots.ShotsFragment;
import com.gunnner.ui.widgets.tabs.SlidingTabLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainFragment extends Fragment implements ShotsFragment.OnTabsHideListener {
    private static final String CURRENT_TAB = "current_tab";

    @InjectView(R.id.sliding_tabs) SlidingTabLayout mSlidingTabs;
    @InjectView(R.id.view_pager) ViewPager mViewPager;

    private int mTabsTop = 0;
    private boolean tabsHidden = false;

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

        ((MainActivity) getActivity()).setTitle(getString(R.string.app_name));

        Utils.setTopRightInsets(getActivity(), view);
        mViewPager.setAdapter(new MainPagerAdapter(getChildFragmentManager()));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hideTabs(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING ||
                        state == ViewPager.SCROLL_STATE_SETTLING) {
                    hideTabs(false);
                }
            }
        });
        prepareTabs();
        if (savedInstanceState != null) {
            mViewPager.setCurrentItem(savedInstanceState.getInt(CURRENT_TAB, 0));
        }

        getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTabsTop = mSlidingTabs.getTop();
                getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    private void prepareTabs() {
        mSlidingTabs.setViewPager(mViewPager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_TAB, mViewPager.getCurrentItem());
    }

    @Override
    public void hideTabs(boolean hide) {
        if (hide) {
            if (tabsHidden) {
                mSlidingTabs.animate().y(mTabsTop - mSlidingTabs.getHeight()).start();
                tabsHidden = false;
            }
        } else {
            if (!tabsHidden) {
                mSlidingTabs.animate().y(mTabsTop).start();
                tabsHidden = true;
            }
        }
    }
}
