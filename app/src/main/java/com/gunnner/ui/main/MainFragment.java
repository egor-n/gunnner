package com.gunnner.ui.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gunnner.R;
import com.gunnner.data.helpers.Utils;
import com.gunnner.ui.widgets.tabs.SlidingTabLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainFragment extends Fragment {
    private static final String CURRENT_TAB = "current_tab";

    @InjectView(R.id.sliding_tabs) SlidingTabLayout mSlidingTabs;
    @InjectView(R.id.view_pager) ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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
        prepareTabs();
        if (savedInstanceState != null) {
            mViewPager.setCurrentItem(savedInstanceState.getInt(CURRENT_TAB, 0));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);

        final SearchView searchView = new SearchView(getActivity());
        searchView.setFocusable(false);

        MenuItem search = menu.findItem(R.id.action_search);
        search.setActionView(searchView);
        search.setIcon(R.drawable.ic_search);
        search.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ((SearchListener) getActivity()).onSearchQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void prepareTabs() {
        Resources res = getResources();
        mSlidingTabs.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        mSlidingTabs.setDistributeEvenly(true);
        mSlidingTabs.setSelectedIndicatorColors(res.getColor(R.color.dribbble2),
                res.getColor(R.color.dribbble), res.getColor(R.color.dribbble3));
        mSlidingTabs.setViewPager(mViewPager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_TAB, mViewPager.getCurrentItem());
    }

    public interface SearchListener {
        public void onSearchQuery(String query);
    }
}
