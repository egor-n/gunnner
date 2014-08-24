package com.gunnner.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gunnner.R;
import com.gunnner.data.helpers.DateFormatter;
import com.gunnner.data.helpers.Utils;
import com.gunnner.data.models.Shot;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Egor N.
 */
public class PlayerShotsAdapter extends BaseAdapter {
    ArrayList<Shot> shots;
    Context context;
    DisplayImageOptions options;

    public PlayerShotsAdapter(ArrayList<Shot> shots, Context context) {
        this.shots = shots;
        this.context = context;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.placeholder_small)
                .showImageForEmptyUri(R.drawable.placeholder_small)
                .showImageOnFail(R.drawable.placeholder_small)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    @Override
    public int getCount() {
        return shots.size();
    }

    @Override
    public Object getItem(int i) {
        return shots.get(i);
    }

    @Override
    public long getItemId(int i) {
        return shots.get(i).get_id();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.player_shot_row, parent, false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Utils.getImageLoader(context).displayImage(
                shots.get(i).getImageTeaserUrl(), holder.image, options);
        holder.date.setText(DateFormatter.formatDate(shots.get(i).getCreatedAt()));
        return view;
    }

    public void setItems(ArrayList<Shot> shots) {
        this.shots = shots;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @InjectView(R.id.shot_image) ImageView image;
        @InjectView(R.id.shot_date) TextView date;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
