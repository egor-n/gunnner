package com.gunnner.data.api;

import com.google.gson.JsonObject;
import com.gunnner.data.models.Comment;
import com.gunnner.data.models.Shot;
import com.gunnner.data.models.ShotWrapper;
import com.gunnner.data.models.User;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author Egor N.
 */
public class Api {
    private static RestAdapter restAdapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", "application/vnd.dribbble.v1.param+json");
                    request.addHeader("Authorization", "Bearer 4e3e676ce2881d166900f7f0ba4f1c0c599f3126ff426c78e61fd3fc233b2a32");
                }
            })
            .setEndpoint("https://api.dribbble.com/v1/")
            .build();
    private static DribbbleService dribbbleService = restAdapter.create(DribbbleService.class);

    public static DribbbleService dribbble() {
        return dribbbleService;
    }

    public interface DribbbleService {
        @GET("/shots")
        void shots(@Query("list") String param, @Query("page") int page,
                   Callback<ArrayList<Shot>> callback);

        @GET("/shots/{id}")
        void shot(@Path("id") int shotId, Callback<Shot> callback);

        @GET("/shots/{id}/rebounds")
        void rebounds(@Path("id") int shotId, Callback<ShotsResponse> callback);

        @GET("/shots/{id}/comments")
        void comments(@Path("id") int shotId, @Query("page") int page,
                      Callback<ArrayList<Comment>> callback);

        @GET("/players/{id}/shots")
        void userShots(@Path("id") int playerId, @Query("page") int page,
                       Callback<ArrayList<Shot>> callback);

        @GET("/users/{id}/shots/following")
        void followingShots(@Path("id") int userId, @Query("page") int page,
                            Callback<ArrayList<ShotWrapper>> callback);

        @GET("/users/{id}/shots/likes")
        void likesShots(@Path("id") int userId, @Query("page") int page,
                        Callback<ArrayList<ShotWrapper>> callback);

        @GET("/users/{id}")
        void userProfile(@Path("id") int userId, Callback<User> callback);

        @GET("/users/{id}/followers/")
        void userFollowers(@Path("id") int userId, @Query("page") int page,
                           Callback<UsersResponse> callback);

        @GET("/users/{id}/following")
        void userFollowing(@Path("id") int userId, @Query("page") int page,
                           Callback<UsersResponse> callback);

        @GET("/users/{id}/draftees")
        void userDraftees(@Path("id") int userId, @Query("page") int page,
                          Callback<UsersResponse> callback);

        @GET("/users/{id}/shots")
        void userShots(@Path("id") String userName, @Query("page") int page,
                       Callback<ArrayList<Shot>> callback);

        @GET("/users/{id}/following")
        void followingShots(@Path("id") String userName, @Query("page") int page,
                            Callback<ArrayList<ShotWrapper>> callback);

        @GET("/users/{id}/likes")
        void likesShots(@Path("id") String userName, @Query("page") int page,
                        Callback<ArrayList<ShotWrapper>> callback);

        @GET("/users/{id}")
        void userProfile(@Path("id") String userName, Callback<User> callback);

        @GET("/users/{id}/followers")
        void userFollowers(@Path("id") String userName, @Query("page") int page,
                           Callback<UsersResponse> callback);

        @GET("/users/{id}/following")
        void userFollowing(@Path("id") String userName, @Query("page") int page,
                           Callback<UsersResponse> callback);

        @GET("/users/{id}/draftees")
        void userDraftees(@Path("id") String userName, @Query("page") int page,
                          Callback<UsersResponse> callback);

        @GET("/search")
        JsonObject search(@Query("q") String query, @Query("page") int page);
    }
}
