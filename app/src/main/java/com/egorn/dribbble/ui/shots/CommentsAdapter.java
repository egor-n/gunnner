package com.egorn.dribbble.ui.shots;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.egorn.dribbble.R;
import com.egorn.dribbble.data.helpers.Utils;
import com.egorn.dribbble.data.models.Comment;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Egor N.
 */
public class CommentsAdapter extends BaseAdapter {
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private Context context;

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (comments == null) {
            return 0;
        }
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return comments.get(i).get_id();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_comment, parent, false);
            view.setTag(new ViewHolder(view));
        }

        final Comment comment = comments.get(i);

        ViewHolder holder = (ViewHolder) view.getTag();
        Ion.with(holder.playerAvatar).load(comment.getPlayer().getAvatarUrl());
        holder.playerName.setText(comment.getPlayer().getName());
        holder.commentBody.setText(comment.getBody());

        holder.playerAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openProfile(context, comment.getPlayer().get_id());
            }
        });
        holder.playerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openProfile(context, comment.getPlayer().get_id());
            }
        });

        return view;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @InjectView(R.id.player_avatar) ImageView playerAvatar;
        @InjectView(R.id.player_name) TextView playerName;
        @InjectView(R.id.comment_body) TextView commentBody;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
