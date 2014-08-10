package com.egorn.dribbble.ui.shots;

import android.util.SparseArray;
import android.util.SparseIntArray;

import com.egorn.dribbble.data.api.Api;
import com.egorn.dribbble.data.api.CommentsResponse;
import com.egorn.dribbble.data.models.Comment;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Egor N.
 */
public class OpenedShotController {
    private static OpenedShotController instance;

    private OnCommentsLoadedListener callback;
    private SparseArray<ArrayList<Comment>> comments = new SparseArray<ArrayList<Comment>>();
    private SparseIntArray pages = new SparseIntArray();

    public static OpenedShotController getInstance(int shotId, OnCommentsLoadedListener callback) {
        if (instance == null) {
            instance = new OpenedShotController();
        }
        instance.init(shotId, callback);
        return instance;
    }

    private void init(int shotId, OnCommentsLoadedListener callback) {
        this.callback = callback;
        ArrayList<Comment> comments = this.comments.get(shotId);
        if (comments != null) {
            if (callback != null) {
                callback.onCommentsLoaded(true, comments);
            }
        } else {
            loadComments(shotId);
        }
    }

    public void loadMore(int shotId) {
        int page = 1;
        try {
            page = pages.get(shotId);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        page++;
        pages.put(shotId, page);
        loadComments(shotId);
    }

    private void loadComments(final int shotId) {
        int page = pages.get(shotId);
        if (page == 0) {
            page = 1;
        }
        pages.put(shotId, page);
        Api.dribbble().comments(shotId, page, new Callback<CommentsResponse>() {
            @Override
            public void success(CommentsResponse commentsResponse, Response response) {
                ArrayList<Comment> commentsList = comments.get(shotId);
                if (commentsList != null) {
                    commentsList.addAll(commentsResponse.getComments());
                } else {
                    comments.put(shotId, commentsResponse.getComments());
                }
                if (callback != null) {
                    callback.onCommentsLoaded(
                            commentsResponse.getPage() < commentsResponse.getPages(),
                            comments.get(shotId));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.onCommentsLoadingError();
                }
            }
        });
    }

    public interface OnCommentsLoadedListener {
        public void onCommentsLoaded(boolean shouldLoadMore, ArrayList<Comment> comments);

        public void onCommentsLoadingError();
    }
}
