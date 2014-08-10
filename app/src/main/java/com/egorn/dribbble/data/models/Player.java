package com.egorn.dribbble.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Player implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
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

    protected Player(Parcel in) {
        _id = in.readInt();
        name = in.readString();
        username = in.readString();
        url = in.readString();
        avatarUrl = in.readString();
        location = in.readString();
        twitterScreenName = in.readString();
        shotsCount = in.readInt();
        drafteesCount = in.readInt();
        followersCount = in.readInt();
        followingCount = in.readInt();
        commentsCount = in.readInt();
        commentsReceivedCount = in.readInt();
        likesCount = in.readInt();
        likesReceivedCount = in.readInt();
        reboundsCount = in.readInt();
        reboundsReceivedCount = in.readInt();
        createdAt = in.readString();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(url);
        dest.writeString(avatarUrl);
        dest.writeString(location);
        dest.writeString(twitterScreenName);
        dest.writeInt(shotsCount);
        dest.writeInt(drafteesCount);
        dest.writeInt(followersCount);
        dest.writeInt(followingCount);
        dest.writeInt(commentsCount);
        dest.writeInt(commentsReceivedCount);
        dest.writeInt(likesCount);
        dest.writeInt(likesReceivedCount);
        dest.writeInt(reboundsCount);
        dest.writeInt(reboundsReceivedCount);
        dest.writeString(createdAt);
    }
}
