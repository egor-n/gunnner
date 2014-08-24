package com.gunnner.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.TextView;

import com.gunnner.R;
import com.gunnner.data.PlayerController;
import com.gunnner.data.Settings;
import com.gunnner.data.helpers.Utils;
import com.gunnner.ui.widgets.CheckableRelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SettingsFragment extends Fragment {
    @InjectView(R.id.logged_in_as) TextView mLoggedInAs;
    @InjectView(R.id.reduce_data_usage) CheckableRelativeLayout mReduceDataUsage;
    @InjectView(R.id.log_out) View mLogOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Utils.setInsets(getActivity(), view);

        mReduceDataUsage.setChecked(Settings.reduceDataUsage());

        if (!PlayerController.isLoggedIn(getActivity())) {
            mLogOut.setVisibility(View.GONE);
        } else {
            mLoggedInAs.setText(getString(R.string.logged_in_as) + " " + PlayerController.getName());
        }
    }

    @OnClick(R.id.log_out)
    void logOut() {
        PlayerController.logOut(getActivity());
        mLogOut.setVisibility(View.GONE);
    }

    @OnClick(R.id.reduce_data_usage)
    void onReduceDataUsageClicked(View v) {
        Checkable view = (Checkable) v;
        view.setChecked(!view.isChecked());
        Settings.setReduceDataUsage(getActivity(), view.isChecked());
    }

    @OnClick(R.id.clear_cache)
    void clearCache() {
        Utils.clearImageCache(getActivity());
    }

    @OnClick(R.id.developer)
    void openDeveloper() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(getString(R.string.developer_link)));
        startActivity(i);
    }

    @OnClick(R.id.designer)
    void openDesigner() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(getString(R.string.designer_link)));
        startActivity(i);
    }
}
