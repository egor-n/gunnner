package com.gunnner.data;

import android.content.Context;

import com.gunnner.data.api.Api;
import com.gunnner.data.api.ShotsResponse;
import com.gunnner.data.models.Player;
import com.gunnner.data.models.Shot;
import com.gunnner.ui.shots.ShotsController;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Egor N.
 */
public class PlayerController {
    private static PlayerController instance;
    private static String name;
    private static Player player;

    private ArrayList<Shot> followingShots;
    private ArrayList<Shot> likesShots;
    private ArrayList<Shot> playerShots;
    private int followingPage = 0;
    private int likesPage = 0;
    private int playerPage = 0;
    private boolean shouldLoadMoreLikes = true;
    private boolean shouldLoadMoreFollowing = true;
    private boolean shouldLoadMorePlayerShots = true;
    private OnPlayerReceivedListener playerCallback;
    private ShotsController.OnShotsLoadedListener followingShotsCallback;
    private ShotsController.OnShotsLoadedListener likesShotsCallback;
    private ShotsController.OnShotsLoadedListener playerShotsCallback;

    public static PlayerController getInstance(Context context) {
        if (instance == null) {
            instance = new PlayerController();
        }
        return instance;
    }

    public static boolean isLoggedIn(Context context) {
        restore(context);
        return name != null;
    }

    public static void setName(Context context, String name) {
        if (name == null) {
            PlayerController.name = null;
            player = null;
            return;
        }

        if (name.equals(PlayerController.name)) {
            return;
        }

        PlayerController.name = name;
        if (player != null) {
            player.delete(context);
            player = null;
        }
    }

    public static void logOut(Context context) {
        Player.logOut(context);
        instance = null;
        player = null;
        name = null;
    }

    private static void restore(Context context) {
        if (instance == null) {
            instance = new PlayerController();
        }
        player = Player.restorePlayer(context);
        if (player != null) {
            name = player.getName();
        }
    }

    public void getPlayer(Context context, OnPlayerReceivedListener playerCallback) {
        if (player == null) {
            loadPlayer(context, playerCallback);
        } else {
            if (playerCallback != null) {
                playerCallback.onPlayerReceived(player);
            }
        }
    }

    public void getLikesShots(ShotsController.OnShotsLoadedListener likesShotsCallback) {
        this.likesShotsCallback = likesShotsCallback;
        if (likesShots == null) {
            loadMoreLikesShots();
        } else {
            if (likesShotsCallback != null) {
                likesShotsCallback.onShotsLoaded(likesShots);
            }
        }
    }

    public void getFollowingShots(ShotsController.OnShotsLoadedListener followingShotsCallback) {
        this.followingShotsCallback = followingShotsCallback;
        if (followingShots == null) {
            loadMoreFollowingShots();
        } else {
            if (followingShotsCallback != null) {
                followingShotsCallback.onShotsLoaded(followingShots);
            }
        }
    }

    public void getPlayerShots(ShotsController.OnShotsLoadedListener playerShotsCallback) {
        this.playerShotsCallback = playerShotsCallback;
        if (playerShots == null) {
            loadMorePlayerShots();
        } else {
            if (playerShotsCallback != null) {
                playerShotsCallback.onShotsLoaded(playerShots);
            }
        }
    }

    public void loadMoreLikesShots() {
        if (shouldLoadMoreLikes) {
            likesPage++;
            loadLikesShots();
        }
    }

    public void loadMoreFollowingShots() {
        if (shouldLoadMoreFollowing) {
            followingPage++;
            loadFollowingShots();
        }
    }

    public void loadMorePlayerShots() {
        if (shouldLoadMorePlayerShots) {
            playerPage++;
            loadPlayerShots();
        }
    }

    public void loadFollowingShots() {
        Api.dribbble().followingShots(name, followingPage, new Callback<ShotsResponse>() {
            @Override
            public void success(ShotsResponse shotsResponse, Response response) {
                shouldLoadMoreFollowing = shotsResponse.shouldLoadMore();
                if (followingShots == null) {
                    followingShots = shotsResponse.getShots();
                } else {
                    followingShots.addAll(shotsResponse.getShots());
                }
                if (followingShotsCallback != null) {
                    followingShotsCallback.onShotsLoaded(followingShots);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (followingShotsCallback != null) {
                    followingShotsCallback.onShotsError();
                }
            }
        });
    }

    public void loadLikesShots() {
        Api.dribbble().likesShots(name, likesPage, new Callback<ShotsResponse>() {
            @Override
            public void success(ShotsResponse shotsResponse, Response response) {
                shouldLoadMoreLikes = shotsResponse.shouldLoadMore();
                if (likesShots == null) {
                    likesShots = shotsResponse.getShots();
                } else {
                    likesShots.addAll(shotsResponse.getShots());
                }
                if (likesShotsCallback != null) {
                    likesShotsCallback.onShotsLoaded(likesShots);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (likesShotsCallback != null) {
                    likesShotsCallback.onShotsError();
                }
            }
        });
    }

    public void loadPlayerShots() {
        Api.dribbble().playerShots(name, playerPage, new Callback<ShotsResponse>() {
            @Override
            public void success(ShotsResponse shotsResponse, Response response) {
                shouldLoadMorePlayerShots = shotsResponse.shouldLoadMore();
                if (playerShots == null) {
                    playerShots = shotsResponse.getShots();
                } else {
                    playerShots.addAll(shotsResponse.getShots());
                }
                if (playerShotsCallback != null) {
                    playerShotsCallback.onShotsLoaded(playerShots);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (playerShotsCallback != null) {
                    playerShotsCallback.onShotsError();
                }
            }
        });
    }

    private void loadPlayer(final Context context, OnPlayerReceivedListener callback) {
        playerCallback = callback;
        Api.dribbble().playerProfile(name, new Callback<Player>() {
            @Override
            public void success(Player player, Response response) {
                if (player != null) {
                    if (context != null) {
                        player.save(context);
                    }
                    PlayerController.player = player;
                    if (playerCallback != null) {
                        playerCallback.onPlayerReceived(player);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (playerCallback != null) {
                    playerCallback.onPlayerError();
                }
            }
        });
    }

    public interface OnPlayerReceivedListener {
        public void onPlayerReceived(Player player);

        public void onPlayerError();
    }
}
