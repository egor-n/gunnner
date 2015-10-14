package com.gunnner.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.gunnner.data.api.Api;
import com.gunnner.data.models.Shot;
import com.gunnner.data.models.ShotWrapper;
import com.gunnner.data.models.User;
import com.gunnner.ui.shots.ShotsController;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Egor N.
 */
public class UserController {
    private static UserController instance;
    private static String name;
    private static User user;

    private ArrayList<Shot> followingShots;
    private ArrayList<Shot> likesShots;
    private ArrayList<Shot> userShots;
    private int followingPage = 0;
    private int likesPage = 0;
    private int playerPage = 0;
    private boolean shouldLoadMoreLikes = true;
    private boolean shouldLoadMoreFollowing = true;
    private boolean shouldLoadMorePlayerShots = true;
    private OnPlayerReceivedListener playerCallback;
    private ShotsController.OnShotsLoadedListener followingShotsCallback;
    private ShotsController.OnShotsLoadedListener likesShotsCallback;
    private ShotsController.OnShotsLoadedListener userShotsCallback;

    public static UserController getInstance(Context context) {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public static boolean isLoggedIn(Context context) {
        restore(context);
        return !TextUtils.isEmpty(name);
    }

    public static void setName(Context context, String name) {
        if (name.equals(UserController.name)) {
            return;
        }

        UserController.name = name;
        user = null;

        save(context);
    }

    public static String getName() {
        return name;
    }

    public static void logOut(Context context) {
        instance = null;
        user = null;
        name = null;

        save(context);
    }

    private static void restore(Context context) {
        if (instance == null) {
            instance = new UserController();
        }

        name = getPreferences(context).getString("name", "");
    }

    private static void save(Context context) {
        getPreferences(context).edit().putString("name", name).apply();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("user", Context.MODE_PRIVATE);
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
        this.userShotsCallback = playerShotsCallback;
        if (userShots == null) {
            loadMorePlayerShots();
        } else {
            if (playerShotsCallback != null) {
                playerShotsCallback.onShotsLoaded(userShots);
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
        Api.dribbble().followingShots(name, followingPage, new Callback<ArrayList<ShotWrapper>>() {
            @Override
            public void success(ArrayList<ShotWrapper> shots, Response response) {
                shouldLoadMoreFollowing = shots.size() > 0; // TODO
                if (followingShots == null) {
                    followingShots = new ArrayList<>();
                }
                for (ShotWrapper wrapper : shots) {
                    followingShots.add(wrapper.getShot());
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
        Api.dribbble().likesShots(name, likesPage, new Callback<ArrayList<ShotWrapper>>() {
            @Override
            public void success(ArrayList<ShotWrapper> shots, Response response) {
                shouldLoadMoreLikes = shots.size() > 0; // TODO
                if (likesShots == null) {
                    likesShots = new ArrayList<>();
                }
                for (ShotWrapper wrapper : shots) {
                    likesShots.add(wrapper.getShot());
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
        Api.dribbble().userShots(name, playerPage, new Callback<ArrayList<Shot>>() {
            @Override
            public void success(ArrayList<Shot> shots, Response response) {
                shouldLoadMorePlayerShots = shots.size() > 0; // TODO
                if (userShots == null) {
                    userShots = shots;
                } else {
                    userShots.addAll(shots);
                }
                if (userShotsCallback != null) {
                    userShotsCallback.onShotsLoaded(userShots);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (userShotsCallback != null) {
                    userShotsCallback.onShotsError();
                }
            }
        });
    }

    public interface OnPlayerReceivedListener {
        void onPlayerReceived(User user);

        void onPlayerError();
    }
}
