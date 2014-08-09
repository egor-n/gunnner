package com.egorn.dribbble.ui.shots;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.egorn.dribbble.R;
import com.egorn.dribbble.data.helpers.DateFormatter;
import com.egorn.dribbble.data.models.Shot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Egor N.
 */
public class ShotsAdapter extends BaseAdapter {
    ArrayList<Shot> shots;
    Context context;

    public ShotsAdapter(ArrayList<Shot> shots, Context context) {
        this.shots = shots;
        this.context = context;
    }

    @Override public int getCount() {
        return shots.size();
    }

    @Override public Object getItem(int i) {
        return shots.get(i);
    }

    @Override public long getItemId(int i) {
        return shots.get(i).get_id();
    }

    @Override public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.row_shot, null);
            view.setTag(new ViewHolder(view));
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        Shot shot = shots.get(i);

        Picasso.with(context).load(shot.getImageUrl())
                .fit()
                .placeholder(R.drawable.placeholder)
                .into(holder.image);

        holder.title.setText(shot.getTitle());
        holder.player.setText(Html.fromHtml("by " + "<font color=\"#f52b79\">" + shot.getPlayer().getName() + "</font>"));
        holder.time.setText(DateFormatter.formatDate(context, shot.getCreatedAt()));
        holder.views.setText(shot.getViewsCount() + "");
        holder.likes.setText(shot.getLikesCount() + "");
        holder.comments.setText(shot.getCommentsCount() + "");
        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.shot_image) ImageView image;
        @InjectView(R.id.title) TextView title;
        @InjectView(R.id.player) TextView player;
        @InjectView(R.id.time) TextView time;
        @InjectView(R.id.views) TextView views;
        @InjectView(R.id.likes) TextView likes;
        @InjectView(R.id.comments) TextView comments;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
