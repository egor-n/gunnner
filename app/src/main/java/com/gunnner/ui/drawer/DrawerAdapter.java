package com.gunnner.ui.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gunnner.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Egor N.
 */
public class DrawerAdapter extends BaseAdapter {
    private static final String[] menu = new String[]{
            "Main",
            "My Profile",
            "My Shots",
            "Following",
            "Likes",
            "Settings",
            "About",
    };
    private static final int[] menuDrawables = new int[]{
            R.drawable.ic_menu_main,
            R.drawable.ic_menu_my_profile,
            R.drawable.ic_menu_shots,
            R.drawable.ic_menu_follow,
            R.drawable.ic_menu_likes,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_about,
    };
    private Context context;

    public DrawerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_drawer, parent, false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.title.setText(menu[i]);
        holder.title.setCompoundDrawablesWithIntrinsicBounds(menuDrawables[i], 0, 0, 0);
        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.title) TextView title;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
