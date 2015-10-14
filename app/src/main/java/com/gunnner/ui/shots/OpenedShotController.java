package com.gunnner.ui.shots;

import android.util.SparseArray;
import android.util.SparseIntArray;

import com.gunnner.data.api.Api;
import com.gunnner.data.models.Comment;
import com.gunnner.data.models.Shot;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Egor N.
 */
public class OpenedShotController {
    private static OpenedShotController instance;

    private SparseArray<OnCommentsLoadedListener> callbacks = new SparseArray<OnCommentsLoadedListener>();
    private SparseArray<ArrayList<Comment>> comments = new SparseArray<ArrayList<Comment>>();
    private SparseArray<Shot> shots = new SparseArray<Shot>();
    private SparseIntArray pages = new SparseIntArray();

    public static OpenedShotController getInstance(int shotId, OnCommentsLoadedListener callback) {
        if (instance == null) {
            instance = new OpenedShotController();
        }
        instance.init(shotId, callback);
        return instance;
    }

    private void init(int shotId, OnCommentsLoadedListener callback) {
        callbacks.put(shotId, callback);

        Shot shot = shots.get(shotId);
        if (shot != null) {
            if (callback != null) {
                callback.onShotLoaded(shot);
            }
        } else {
            loadShot(shotId);
        }

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
        Api.dribbble().comments(shotId, page, new Callback<ArrayList<Comment>>() {
            @Override
            public void success(ArrayList<Comment> newComments, Response response) {
                ArrayList<Comment> commentsList = comments.get(shotId);
                if (commentsList != null) {
                    commentsList.addAll(newComments);
                } else {
                    comments.put(shotId, newComments);
                }
                if (callbacks.get(shotId) != null) {
                    callbacks.get(shotId).onCommentsLoaded(newComments.size() > 0, comments.get(shotId)); // TODO
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void loadShot(final int shotId) {
        Api.dribbble().shot(shotId, new Callback<Shot>() {
            @Override
            public void success(Shot shot, Response response) {
                shots.put(shotId, shot);

                if (callbacks.get(shotId) != null) {
                    callbacks.get(shotId).onShotLoaded(shot);
                    callbacks.get(shotId).onCommentsLoaded(true, comments.get(shotId));
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public interface OnCommentsLoadedListener {
        void onShotLoaded(Shot shot);

        void onCommentsLoaded(boolean shouldLoadMore, ArrayList<Comment> comments);
    }
}
