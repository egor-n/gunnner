package com.egorn.dribbble.data.models;

import com.google.gson.annotations.SerializedName;

public class Player {
    @SerializedName("id")
    private int _id;
    private String name;
    private String username;
    private String url;
    @SerializedName("avatar_url")
    private String avatarUrl;
    private String location;
    @SerializedName("twitter_screen_name")
    private String twitterScreenName;
    //    @SerializedName("drafted_by_player_id")
//    private Object draftedByPlayerId;
    @SerializedName("shots_count")
    private int shotsCount;
    @SerializedName("draftees_count")
    private int drafteesCount;
    @SerializedName("followers_count")
    private int followersCount;
    @SerializedName("following_count")
    private int followingCount;
    @SerializedName("comments_count")
    private int commentsCount;
    @SerializedName("comments_received_count")
    private int commentsReceivedCount;
    @SerializedName("likes_count")
    private int likesCount;
    @SerializedName("likes_received_count")
    private int likesReceivedCount;
    @SerializedName("rebounds_count")
    private int reboundsCount;
    @SerializedName("rebounds_received_count")
    private int reboundsReceivedCount;
    @SerializedName("created_at")
    private String createdAt;

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getTwitterScreenName() {
        return twitterScreenName;
    }

    public int getShotsCount() {
        return shotsCount;
    }

    public int getDrafteesCount() {
        return drafteesCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public int getCommentsReceivedCount() {
        return commentsReceivedCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getLikesReceivedCount() {
        return likesReceivedCount;
    }

    public int getReboundsCount() {
        return reboundsCount;
    }

    public int getReboundsReceivedCount() {
        return reboundsReceivedCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + _id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", url='" + url + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", location='" + location + '\'' +
                ", twitterScreenName='" + twitterScreenName + '\'' +
//                ", draftedByPlayerId=" + draftedByPlayerId +
                ", shotsCount=" + shotsCount +
                ", drafteesCount=" + drafteesCount +
                ", followersCount=" + followersCount +
                ", followingCount=" + followingCount +
                ", commentsCount=" + commentsCount +
                ", commentsReceivedCount=" + commentsReceivedCount +
                ", likesCount=" + likesCount +
                ", likesReceivedCount=" + likesReceivedCount +
                ", reboundsCount=" + reboundsCount +
                ", reboundsReceivedCount=" + reboundsReceivedCount +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}