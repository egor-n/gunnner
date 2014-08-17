package com.egorn.dribbble.ui.profile;

import android.util.SparseArray;

import com.egorn.dribbble.data.api.Api;
import com.egorn.dribbble.data.models.Player;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Egor N.
 */
public class ProfileController {
    private static ProfileController instance;

    private SparseArray<Player> players = new SparseArray<Player>();
    private SparseArray<OnPlayerReceivedListener> callbacks = new SparseArray<OnPlayerReceivedListener>();

    public static ProfileController getInstance(int playerId, OnPlayerReceivedListener callback) {
        if (instance == null) {
            instance = new ProfileController();
        }
        instance.init(playerId, callback);
        return instance;
    }

    private void init(int playerId, OnPlayerReceivedListener callback) {
        callbacks.put(playerId, callback);

        if (players.get(playerId) != null) {
            if (callbacks.get(playerId) != null) {
                callbacks.get(playerId).onReceived(players.get(playerId));
            }
        } else {
            loadProfile(playerId);
        }
    }

    private void loadProfile(final int playerId) {
        Api.dribbble().playerProfile(playerId, new Callback<Player>() {
            @Override
            public void success(Player player, Response response) {
                if (callbacks.get(playerId) != null) {
                    callbacks.get(playerId).onReceived(player);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    public interface OnPlayerReceivedListener {
        public void onReceived(Player player);
    }
}
