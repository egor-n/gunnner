package com.gunnner.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gunnner.R;
import com.gunnner.data.InfiniteScrollListener;
import com.gunnner.data.helpers.Utils;
import com.gunnner.data.models.Shot;
import com.gunnner.data.models.User;
import com.gunnner.ui.shots.ShotsFragment;
import com.gunnner.ui.widgets.HeaderGridView;
import com.gunnner.ui.widgets.ProfileView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

public class ProfileFragment extends Fragment implements ProfileController.OnPlayerDataListener {
    public static final String PLAYER_ID = "player_id";
    public static final String PLAYER_NAME = "player_name";

    @InjectView(R.id.player_shots_list) HeaderGridView mPlayerShotsList;
    @InjectView(R.id.progress_bar) ProgressBar mProgressBar;
    @InjectView(R.id.player_error) TextView mPlayerError;

    private int playerId;
    private User mUser;
    private ProfileView mProfileView;
    private UserShotsAdapter mAdapter;
    private ArrayList<Shot> mShots = new ArrayList<Shot>();
    private ShotsFragment.OnShotClickedListener mListener;

    private ProfileController controller;

    public static ProfileFragment newInstance(int playerId) {
        Bundle args = new Bundle();
        args.putInt(PLAYER_ID, playerId);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ProfileFragment newInstance(String playerName) {
        Bundle args = new Bundle();
        args.putString(PLAYER_NAME, playerName);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            mListener = (ShotsFragment.OnShotClickedListener) getActivity();
        } catch (ClassCastException ignored) {
        }

        Utils.setInsets(getActivity(), mPlayerShotsList);
        playerId = getArguments().getInt(PLAYER_ID);
        String playerName = getArguments().getString(PLAYER_NAME);
        if (TextUtils.isEmpty(playerName)) {
            controller = ProfileController.getInstance(playerId, this);
        } else {
            controller = ProfileController.getInstance(playerName, this);
        }
    }

    @OnItemClick(R.id.player_shots_list)
    void onShotClicked(int position) {
        if (mListener != null) {
            mListener.onShotClicked(mShots.get(position - 2));
        }
    }

    @Override
    public void onPlayerReceived(User user) {
        if (!isAdded() || user == null) {
            return;
        }

        this.mUser = user;
        mProgressBar.setVisibility(View.GONE);
        prepareHeader();
    }

    @Override
    public void onPlayerError() {
        mProgressBar.setVisibility(View.GONE);
        mPlayerError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShotsReceived(boolean shouldLoadMore, ArrayList<Shot> shots) {
        if (!isAdded()) {
            return;
        }

        this.mShots = shots;

        if (mProfileView == null) {
            // User data wasn't loaded yet, so we have to wait for it to set adapter,
            // because you can't addHeaderView after setAdapter
            return;
        }

        if (mAdapter == null) {
            mAdapter = new UserShotsAdapter(shots, getActivity());
        } else {
            mAdapter.setItems(shots);
        }

        if (shouldLoadMore) {
            mPlayerShotsList.setOnScrollListener(new InfiniteScrollListener() {
                @Override
                public void loadMore(int page, int totalItemsCount) {
                    if (controller != null) {
                        controller.loadMore(playerId);
                    }
                }
            });
        } else {
            mPlayerShotsList.setOnScrollListener(null);
        }
    }

    private void prepareHeader() {
        mProfileView = ProfileView.inflate(mPlayerShotsList);
        mProfileView.setUser(mUser);

        mPlayerShotsList.addHeaderView(mProfileView, null, false);

        mAdapter = new UserShotsAdapter(mShots, getActivity());
        mPlayerShotsList.setAdapter(mAdapter); // set empty adapter so it shows the header
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.shot, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mUser == null) {
            return false;
        }

        String url = "https://dribbble.com/" + mUser.getUsername();

        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, mUser.getName() + "\n" + url);
            intent.setType("text/plain");
            startActivity(intent);
            return true;
        } else if (id == R.id.action_browser) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
