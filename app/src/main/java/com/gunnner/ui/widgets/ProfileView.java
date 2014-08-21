package com.gunnner.ui.widgets;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gunnner.R;
import com.gunnner.data.helpers.DateFormatter;
import com.gunnner.data.models.Player;
import com.gunnner.ui.profile.ProfileActivity;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Egor N.
 */
public class ProfileView extends RelativeLayout {
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

    private Player player;

    public ProfileView(Context context) {
        super(context);
        init(context);
    }

    public ProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProfileView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public static ProfileView inflate(ViewGroup parent) {
        return (ProfileView) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.profile_view, parent, false);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.profile_children, this, true);
        ButterKnife.inject(this);
    }

    public void setPlayer(Player player) {
        this.player = player;

        ((ProfileActivity) getContext()).setTitle(player.getUsername());

        Picasso.with(getContext()).load(player.getAvatarUrl()).into(mPlayerImage);
        mPlayerName.setText(player.getName());
        mPlayerLocation.setText(player.getLocation());
        mPlayerTwitter.setText(player.getTwitterScreenName());
        mShotsValue.setText(player.getShotsCount() + "");
        mLikesValue.setText(player.getLikesCount() + "");
        mFollowersValue.setText(player.getFollowersCount() + "");
        mFollowingValue.setText(player.getFollowingCount() + "");
        mReboundsValue.setText(player.getReboundsCount() + "");
        mMemberSince.setText(Html.fromHtml("<font color=\"#ea4c89\">Member since:</font> " +
                DateFormatter.formatDate(player.getCreatedAt())));
    }
}
