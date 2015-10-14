package com.gunnner.ui.shots;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gunnner.data.models.Shot;
import com.gunnner.ui.widgets.ShotView;

import java.util.ArrayList;

/**
 * @author Egor N.
 */
public class ShotsAdapter extends BaseAdapter {
    private ArrayList<Shot> shots;

    ShotsAdapter(ArrayList<Shot> shots) {
        this.shots = shots;
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
