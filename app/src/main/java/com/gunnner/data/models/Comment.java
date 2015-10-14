package com.gunnner.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author Egor N.
 */
public class Comment {
    @SerializedName("id")
    private int _id;
    @SerializedName("body")
    private String body;
    @SerializedName("likes_count")
    private int likesCount;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("user")
    private User user;

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

    public User getUser() {
        return user;
    }
}
