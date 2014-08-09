package com.egorn.dribbble.data.models;

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
}
