package com.gunnner.ui.shots;

import com.gunnner.data.api.Api;
import com.gunnner.data.api.ShotsResponse;
import com.gunnner.data.models.Shot;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Egor N.
 */
public class ShotsController {
    private static ShotsController instance;

    private HashMap<String, OnShotsLoadedListener> callbacks = new HashMap<String, OnShotsLoadedListener>();
    private HashMap<String, ArrayList<Shot>> shots = new HashMap<String, ArrayList<Shot>>();
    private HashMap<String, Integer> pages = new HashMap<String, Integer>();

    public static ShotsController getInstance(String reference, OnShotsLoadedListener callback) {
        if (instance == null) {
            instance = new ShotsController();
        }
        instance.init(reference, callback);
        return instance;
    }

    private void init(String reference, OnShotsLoadedListener callback) {
        callbacks.put(reference, callback);
        if (shots.containsKey(reference)) {
            if (callback != null) {
                callback.onShotsLoaded(shots.get(reference));
            }
        } else {
            loadShots(reference);
        }
    }

    public void loadMore(String reference) {
        int page = 1;
        try {
            page = pages.get(reference);
        } catch (NullPointerException ignored) {
        }
        page++;
        pages.put(reference, page);
        loadShots(reference);
    }

    private void loadShots(final String reference) {
        if (!pages.containsKey(reference)) {
            pages.put(reference, 1);
        }
        Api.dribbble().shots(reference, pages.get(reference), new Callback<ShotsResponse>() {
            @Override
            public void success(ShotsResponse shotsResponse, Response response) {
                if (shots.containsKey(reference)) {
                    shots.get(reference).addAll(shotsResponse.getShots());
                } else {
                    shots.put(reference, shotsResponse.getShots());
                }
                if (callbacks.get(reference) != null) {
                    callbacks.get(reference).onShotsLoaded(shots.get(reference));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callbacks.get(reference) != null) {
                    callbacks.get(reference).onShotsError();
                }
            }
        });
    }

    public interface OnShotsLoadedListener {
        public void onShotsLoaded(ArrayList<Shot> shots);

        public void onShotsError();
    }
}
