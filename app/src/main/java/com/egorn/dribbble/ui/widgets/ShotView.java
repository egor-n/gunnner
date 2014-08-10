package com.egorn.dribbble.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
    public static final int SMALL = 0;
    public static final int BIG = 1;
    @InjectView(R.id.shot_image) ImageView mShotImage;
    @InjectView(R.id.rebound) ImageView mRebound;
    @InjectView(R.id.title) TextView mTitle;
    @InjectView(R.id.player) TextView mPlayer;
    @InjectView(R.id.views) TextView mViews;
    @InjectView(R.id.likes) TextView mLikes;
    @InjectView(R.id.comments) TextView mComments;
    @InjectView(R.id.time) TextView mTime;
    private int style = 0;

    public ShotView(Context context) {
        super(context);
        init(context, null);
    }

    public ShotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShotView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public static ShotView inflateSmall(ViewGroup parent) {
        return (ShotView) LayoutInflater.from(parent.getContext()).inflate(R.layout.shot_view_small, parent, false);
    }

    public static ShotView inflateBig(ViewGroup parent) {
        return (ShotView) LayoutInflater.from(parent.getContext()).inflate(R.layout.shot_view_big, parent, false);
    }

    private void init(Context context, AttributeSet attrs) {
        parseAttributes(context, attrs);

        if (style == 0) {
            LayoutInflater.from(context).inflate(R.layout.shot_view_children_small, this, true);
        } else {
            LayoutInflater.from(context).inflate(R.layout.shot_view_children_big, this, true);
        }

        int[] attributess = new int[]{android.R.attr.selectableItemBackground};
        TypedArray ta = context.obtainStyledAttributes(attributess);
        Drawable foreground = ta.getDrawable(0);
        ta.recycle();
        setForeground(foreground);

        ButterKnife.inject(this);
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) {
            style = 0;
            return;
        }

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ShotView,
                0, 0);
        style = a.getInt(R.styleable.ShotView_style, 0);
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
        if (style == 0) {
            mComments.setText(shot.getCommentsCount() + "");
        } else {
            mComments.setText(shot.getCommentsCount() + " Responses");
        }
    }
}
