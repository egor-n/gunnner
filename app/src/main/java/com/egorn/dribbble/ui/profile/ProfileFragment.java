package com.egorn.dribbble.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.egorn.dribbble.R;
import com.egorn.dribbble.data.InfiniteScrollListener;
import com.egorn.dribbble.data.helpers.Utils;
import com.egorn.dribbble.data.models.Player;
import com.egorn.dribbble.data.models.Shot;
import com.egorn.dribbble.ui.shots.ShotsFragment;
import com.egorn.dribbble.ui.widgets.HeaderGridView;
import com.egorn.dribbble.ui.widgets.ProfileView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

public class ProfileFragment extends Fragment implements ProfileController.OnPlayerDataListener {
    public static final String PLAYER_ID = "player_id";

    @InjectView(R.id.player_shots_list) HeaderGridView mPlayerShotsList;
    @InjectView(R.id.progress_bar) ProgressBar mProgressBar;

    private int playerId;
    private Player mPlayer;
    private ProfileView mProfileView;
    private PlayerShotsAdapter mAdapter;
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
        controller = ProfileController.getInstance(playerId, this);
    }

    @OnItemClick(R.id.player_shots_list)
    void onShotClicked(int position) {
        if (mListener != null) {
            mListener.onShotClicked(mShots.get(position - 2));  // no idea why -2, but it works this way (something related to header)
        }
    }

    @Override
    public void onPlayerReceived(Player player) {
        if (!isAdded() || player == null) {
            return;
        }

        this.mPlayer = player;
        mProgressBar.setVisibility(View.GONE);
        prepareHeader();
    }

    @Override
    public void onShotsReceived(boolean shouldLoadMore, ArrayList<Shot> shots) {
        if (!isAdded()) {
            return;
        }

        this.mShots = shots;

        if (mProfileView == null) {
            // Player data wasn't loaded yet, so we have to wait for it to set adapter,
            // because you can't addHeaderView after setAdapter
            return;
        }

        if (mAdapter == null) {
            mAdapter = new PlayerShotsAdapter(shots, getActivity());
        } else {
            mAdapter.setItems(shots);
        }

        if (shouldLoadMore) {
            mPlayerShotsList.setOnScrollListener(new InfiniteScrollListener() {
                @Override
                public void hideTabs(boolean show) {
                    // do nothing
                }

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
        mProfileView.setPlayer(mPlayer);

        mPlayerShotsList.addHeaderView(mProfileView, null, false);

        mAdapter = new PlayerShotsAdapter(mShots, getActivity());
        mPlayerShotsList.setAdapter(mAdapter); // set empty adapter so it shows the header
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.shot, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mPlayer == null) {
            return false;
        }

        String url = "https://dribbble.com/" + mPlayer.getUsername();

        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, mPlayer.getName() + "\n" + url);
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
