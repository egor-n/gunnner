package com.egorn.dribbble.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.egorn.dribbble.R;
import com.egorn.dribbble.data.helpers.DateFormatter;
import com.egorn.dribbble.data.models.Shot;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Egor N.
 */
public class ShotView extends FrameLayout {
    @InjectView(R.id.shot_image) ImageView mShotImage;
    @InjectView(R.id.rebound) ImageView mRebound;
    @InjectView(R.id.title) TextView mTitle;
    @InjectView(R.id.player) TextView mPlayer;
    @InjectView(R.id.views) TextView mViews;
    @InjectView(R.id.likes) TextView mLikes;
    @InjectView(R.id.comments) TextView mComments;
    @InjectView(R.id.time) TextView mTime;

    public ShotView(Context context) {
        super(context);
        init(context);
    }

    public ShotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShotView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public static ShotView inflate(ViewGroup parent) {
        return (ShotView) LayoutInflater.from(parent.getContext()).inflate(R.layout.shot_view, parent, false);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.shot_view_children, this, true);

        int[] attrs = new int[]{android.R.attr.selectableItemBackground};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        Drawable foreground = ta.getDrawable(0);
        ta.recycle();
        setForeground(foreground);

        ButterKnife.inject(this);
    }

    public void setShot(Shot shot) {
        Picasso.with(getContext()).load(shot.getImageUrl())
                .fit()
                .placeholder(R.drawable.placeholder)
                .into(mShotImage);

        if (shot.isRebound()) {
            mRebound.setVisibility(View.VISIBLE);
        }

        mTitle.setText(shot.getTitle());
        mPlayer.setText(Html.fromHtml("by " + "<font color=\"#f52b79\">" + shot.getPlayer().getName() + "</font>"));
        mTime.setText(DateFormatter.formatDate(getContext(), shot.getCreatedAt()));
        mViews.setText(shot.getViewsCount() + "");
        mLikes.setText(shot.getLikesCount() + "");
        mComments.setText(shot.getCommentsCount() + "");
    }

    public void hideCommentsBadge() {
        // can't View.GONE it, because some view are bound to it
        // can't simply setWidth(0), because it leaves margins set in the .xml
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mComments.getLayoutParams();
        lp.setMargins(0, lp.topMargin, lp.rightMargin, lp.bottomMargin);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            lp.setMarginStart(0);
        }
        mComments.setLayoutParams(lp);
        mComments.setWidth(0);
    }
}
