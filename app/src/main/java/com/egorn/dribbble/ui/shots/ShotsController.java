package com.egorn.dribbble.ui.shots;

import com.egorn.dribbble.data.api.Api;
import com.egorn.dribbble.data.api.ShotsResponse;
import com.egorn.dribbble.data.models.Shot;

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

    private String reference;
    private HashMap<String, OnShotsLoadedListener> callbacks = new HashMap<String, OnShotsLoadedListener>();
    private HashMap<String, ArrayList<Shot>> shots = new HashMap<String, ArrayList<Shot>>();

    public static ShotsController getInstance(String reference, OnShotsLoadedListener callback) {
        if (instance == null) {
            instance = new ShotsController();
        }
        instance.init(reference, callback);
        return instance;
    }

    private void init(String reference, OnShotsLoadedListener callback) {
        this.reference = reference;
        callbacks.put(reference, callback);
        if (shots.containsKey(reference)) {
            if (callback != null) {
                callback.onShotsLoaded(shots.get(reference));
            }
        } else {
            loadShots(reference);
        }
    }

    private void loadShots(final String reference) {
        Api.dribbble().shots(reference, new Callback<ShotsResponse>() {
            @Override public void success(ShotsResponse shotsResponse, Response response) {
                shots.put(reference, shotsResponse.getShots());
                if (callbacks.get(reference) != null) {
                    callbacks.get(reference).onShotsLoaded(shotsResponse.getShots());
                }
            }

            @Override public void failure(RetrofitError error) {
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
