package com.egorn.dribbble.ui.shots;

import android.util.SparseArray;

import com.egorn.dribbble.data.api.Api;
import com.egorn.dribbble.data.models.Shot;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Egor N.
 */
public class OpennedShotController {
    private static OpennedShotController instance;

    private OnShotLoadedListener callback;
    private SparseArray<Shot> shots = new SparseArray<Shot>();

    public static OpennedShotController getInstance(int shotId, OnShotLoadedListener callback) {
        if (instance == null) {
            instance = new OpennedShotController();
        }
        instance.init(shotId, callback);
        return instance;
    }

    private void init(int shotId, OnShotLoadedListener callback) {
        this.callback = callback;
        Shot shot = shots.get(shotId);
        if (shot != null) {
            if (callback != null) {
                callback.onShotLoaded(shot);
            }
        } else {
            loadShot(shotId);
        }
    }

    private void loadShot(int shotId) {
        Api.dribbble().shot(shotId, new Callback<Shot>() {
            @Override public void success(Shot shot, Response response) {
                if (callback != null) {
                    callback.onShotLoaded(shot);
                }
            }

            @Override public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.onShotLoadingError();
                }
            }
        });
    }

    public interface OnShotLoadedListener {
        public void onShotLoaded(Shot shot);

        public void onShotLoadingError();
    }
}
