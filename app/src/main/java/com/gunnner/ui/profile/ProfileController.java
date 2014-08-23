package com.gunnner.ui.profile;

import android.util.SparseArray;
import android.util.SparseIntArray;

import com.gunnner.data.api.Api;
import com.gunnner.data.api.ShotsResponse;
import com.gunnner.data.models.Player;
import com.gunnner.data.models.Shot;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Egor N.
 */
public class ProfileController {
    private static ProfileController instance;

    private Player userProfile;
    private OnPlayerDataListener userCallback;
    private SparseArray<Player> players = new SparseArray<Player>();
    private SparseArray<ArrayList<Shot>> shots = new SparseArray<ArrayList<Shot>>();
    private SparseArray<OnPlayerDataListener> callbacks = new SparseArray<OnPlayerDataListener>();
    private SparseIntArray pages = new SparseIntArray();

    public static ProfileController getInstance(int playerId, OnPlayerDataListener callback) {
        if (instance == null) {
            instance = new ProfileController();
        }
        instance.init(playerId, callback);
        return instance;
    }

    public static ProfileController getInstance(String playerName, OnPlayerDataListener callback) {
        if (instance == null) {
            instance = new ProfileController();
        }
        instance.init(playerName, callback);
        return instance;
    }

    private void init(String playerName, OnPlayerDataListener callback) {
        userCallback = callback;

        if (userProfile != null) {
            if (userCallback != null) {
                userCallback.onPlayerReceived(userProfile);
            }
        } else {
            loadProfile(playerName);
        }
    }

    private void init(int playerId, OnPlayerDataListener callback) {
        callbacks.put(playerId, callback);

        if (players.get(playerId) != null) {
            if (callbacks.get(playerId) != null) {
                callbacks.get(playerId).onPlayerReceived(players.get(playerId));
            }
        } else {
            loadProfile(playerId);
        }

        ArrayList<Shot> shots = this.shots.get(playerId);
        if (shots != null) {
            if (callback != null) {
                callback.onShotsReceived(true, shots);
            }
        } else {
            loadShots(playerId);
        }
    }

    public void loadMore(int playerId) {
        int page = 1;
        try {
            page = pages.get(playerId);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        page++;
        pages.put(playerId, page);
        loadShots(playerId);
    }

    private void loadProfile(final String playerId) {
        Api.dribbble().playerProfile(playerId, new Callback<Player>() {
            @Override
            public void success(Player player, Response response) {
                if (userCallback != null) {
                    userCallback.onPlayerReceived(player);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                if (userCallback != null) {
                    userCallback.onPlayerError();
                }
            }
        });
    }

    private void loadProfile(final int playerId) {
        Api.dribbble().playerProfile(playerId, new Callback<Player>() {
            @Override
            public void success(Player player, Response response) {
                if (callbacks.get(playerId) != null) {
                    callbacks.get(playerId).onPlayerReceived(player);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callbacks.get(playerId) != null) {
                    callbacks.get(playerId).onPlayerError();
                }
                error.printStackTrace();
            }
        });
    }

    private void loadShots(final int playerId) {
        int page = pages.get(playerId);
        if (page == 0) {
            page = 1;
        }
        pages.put(playerId, page);

        Api.dribbble().playerShots(playerId, page, new Callback<ShotsResponse>() {
            @Override
            public void success(ShotsResponse shotsResponse, Response response) {
                ArrayList<Shot> shotsList = shots.get(playerId);
                if (shotsList != null) {
                    shotsList.addAll(shotsResponse.getShots());
                } else {
                    shots.put(playerId, shotsResponse.getShots());
                }
                if (callbacks.get(playerId) != null) {
                    callbacks.get(playerId).onShotsReceived(
                            shotsResponse.getPage() < shotsResponse.getPages(),
                            shots.get(playerId));
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public interface OnPlayerDataListener {
        public void onPlayerReceived(Player player);

        public void onPlayerError();

        public void onShotsReceived(boolean shouldLoadMore, ArrayList<Shot> shots);
    }
}
