package com.egorn.dribbble.ui.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egorn.dribbble.R;
import com.egorn.dribbble.Utils;
import com.egorn.dribbble.data.helpers.DateFormatter;
import com.egorn.dribbble.data.models.Player;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileFragment extends Fragment implements ProfileController.OnPlayerReceivedListener {
    public static final String PLAYER_ID = "player_id";

    @InjectView(R.id.player_image) ImageView mPlayerImage;
    @InjectView(R.id.player_name) TextView mPlayerName;
    @InjectView(R.id.player_location) TextView mPlayerLocation;
    @InjectView(R.id.player_twitter) TextView mPlayerTwitter;
    @InjectView(R.id.shots_value) TextView mShotsValue;
    @InjectView(R.id.likes_value) TextView mLikesValue;
    @InjectView(R.id.followers_value) TextView mFollowersValue;
    @InjectView(R.id.following_value) TextView mFollowingValue;
    @InjectView(R.id.rebounds_value) TextView mReboundsValue;
    @InjectView(R.id.member_since) TextView mMemberSince;

    public static ProfileFragment newInstance(int playerId) {
        Bundle args = new Bundle();
        args.putInt(PLAYER_ID, playerId);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
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
        Utils.setInsets(getActivity(), view);
        ProfileController.getInstance(getArguments().getInt(PLAYER_ID), this);
    }

    @Override
    public void onReceived(Player player) {
        if (!isAdded() || player == null) {
            return;
        }

        ((ProfileActivity) getActivity()).setTitle(player.getUsername());

        Picasso.with(getActivity()).load(player.getAvatarUrl()).into(mPlayerImage);
        mPlayerName.setText(player.getName());
        mPlayerLocation.setText(player.getLocation());
        mPlayerTwitter.setText(player.getTwitterScreenName());
        mShotsValue.setText(player.getShotsCount() + "");
        mLikesValue.setText(player.getLikesCount() + "");
        mFollowersValue.setText(player.getFollowersCount() + "");
        mFollowingValue.setText(player.getFollowingCount() + "");
        mReboundsValue.setText(player.getReboundsCount() + "");
        mMemberSince.setText(Html.fromHtml("<font color=\"#ea4c89\">Member since:</font> " + DateFormatter.formatDate(getActivity(), player.getCreatedAt())));
    }
}
