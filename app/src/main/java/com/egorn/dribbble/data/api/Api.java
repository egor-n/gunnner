package com.egorn.dribbble.data.api;

import com.egorn.dribbble.data.models.Player;
import com.egorn.dribbble.data.models.Shot;

import retrofit.Callback;
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
            .setEndpoint("http://api.dribbble.com")
            .build();
    private static DribbbleService dribbbleService = restAdapter.create(DribbbleService.class);

    public static DribbbleService dribbble() {
        return dribbbleService;
    }

    public interface DribbbleService {
        @GET("/shots/{value}/")
        public void shots(@Path("value") String param, @Query("page") int page,
                          Callback<ShotsResponse> callback);

        @GET("/shots/{id}")
        public void shot(@Path("id") int shotId, Callback<Shot> callback);

        @GET("/shots/{id}/rebounds")
        public void rebounds(@Path("id") int shotId, Callback<ShotsResponse> callback);

        @GET("/shots/{id}/comments")
        public void comments(@Path("id") int shotId, @Query("page") int page,
                             Callback<CommentsResponse> callback);

        @GET("/players/{id}/shots")
        public void playerShots(@Path("id") int playerId, @Query("page") int page,
                                Callback<ShotsResponse> callback);

        @GET("/players/{id}/shots/following")
        public void followingShots(@Path("id") int playerId, @Query("page") int page,
                                   Callback<ShotsResponse> callback);

        @GET("/players/{id}/shots/likes")
        public void likesShots(@Path("id") int playerId, @Query("page") int page,
                               Callback<ShotsResponse> callback);

        @GET("/players/{id}")
        public void playerProfile(@Path("id") int playerId, Callback<Player> callback);

        @GET("/players/{id}/followers/")
        public void playerFollowers(@Path("id") int playerId, @Query("page") int page,
                                    Callback<PlayersResponse> callback);

        @GET("/players/{id}/following")
        public void playerFollowing(@Path("id") int playerId, @Query("page") int page,
                                    Callback<PlayersResponse> callback);

        @GET("/players/{id}/draftees")
        public void playerDraftees(@Path("id") int playerId, @Query("page") int page,
                                   Callback<PlayersResponse> callback);

        @GET("/players/{id}/shots")
        public void playerShots(@Path("id") String playerId, @Query("page") int page,
                                Callback<ShotsResponse> callback);

        @GET("/players/{id}/shots/following")
        public void followingShots(@Path("id") String playerId, @Query("page") int page,
                                   Callback<ShotsResponse> callback);

        @GET("/players/{id}/shots/likes")
        public void likesShots(@Path("id") String playerId, @Query("page") int page,
                               Callback<ShotsResponse> callback);

        @GET("/players/{id}")
        public void playerProfile(@Path("id") String playerId, Callback<Player> callback);

        @GET("/players/{id}/followers")
        public void playerFollowers(@Path("id") String playerId, @Query("page") int page,
                                    Callback<PlayersResponse> callback);

        @GET("/players/{id}/following")
        public void playerFollowing(@Path("id") String playerId, @Query("page") int page,
                                    Callback<PlayersResponse> callback);

        @GET("/players/{id}/draftees")
        public void playerDraftees(@Path("id") String playerId, @Query("page") int page,
                                   Callback<PlayersResponse> callback);
    }
}
