package com.egorn.dribbble.ui.shots;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.egorn.dribbble.data.models.Shot;
import com.egorn.dribbble.ui.widgets.ShotView;

import java.util.ArrayList;

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
        ShotView shotView = (ShotView) view;
        if (shotView == null) {
            shotView = ShotView.inflateSmall(parent);
        }

        shotView.setShot(shots.get(i));
        return shotView;
    }

    public void setItems(ArrayList<Shot> shots) {
        this.shots = shots;
        notifyDataSetChanged();
    }
}
