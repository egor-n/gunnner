package com.gunnner.ui.shots;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gunnner.R;
import com.gunnner.data.helpers.Utils;
import com.gunnner.data.models.Comment;

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
        Utils.getImageLoader(context).displayImage(
                comment.getUser().getAvatarUrl(), holder.playerAvatar,
                Utils.getDisplayImageOptions());
        holder.playerName.setText(comment.getUser().getName());
        holder.commentBody.setText(Html.fromHtml(comment.getBody()));

        holder.playerAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openProfile(context, comment.getUser().get_id());
            }
        });
        holder.playerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openProfile(context, comment.getUser().get_id());
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
