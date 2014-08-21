package com.gunnner.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author Egor N.
 */
public class Comment {
    @SerializedName("id")
    private int _id;
    private String body;
    @SerializedName("likes_count")
    private int likesCount;
    @SerializedName("created_at")
    private String createdAt;
    private Player player;

    public int get_id() {
        return _id;
    }

    public String getBody() {
        return body;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Player getPlayer() {
        return player;
    }
}
