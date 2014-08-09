package com.egorn.dribbble.data.models;

import com.google.gson.annotations.SerializedName;

public class Shot {
    public static final String DEBUTS = "debuts";
    public static final String EVERYONE = "everyone";
    public static final String POPULAR = "popular";

    @SerializedName("id")
    private int _id;
    private String title;
    private String description;
    private String url;
    @SerializedName("short_url")
    private String shortUrl;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("image_teaser_url")
    private String imageTeaserUrl;
    private int width;
    private int height;
    @SerializedName("views_count")
    private int viewsCount;
    @SerializedName("likes_count")
    private int likesCount;
    @SerializedName("comments_count")
    private int commentsCount;
    @SerializedName("rebounds_count")
    private int reboundsCount;
    @SerializedName("rebound_source_id")
    private int reboundSourceId;
    @SerializedName("created_at")
    private String createdAt;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getReboundSourceId() {
        return reboundSourceId;
    }

    public int getReboundsCount() {
        return reboundsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getImageTeaserUrl() {
        return imageTeaserUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int get_id() {
        return _id;
    }

    public boolean isRebound() {
        return getReboundSourceId() != 0;
    }

    @Override
    public String toString() {
        return "Shot{" +
                "id=" + _id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageTeaserUrl='" + imageTeaserUrl + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", viewsCount=" + viewsCount +
                ", likesCount=" + likesCount +
                ", commentsCount=" + commentsCount +
                ", reboundsCount=" + reboundsCount +
                ", reboundSourceId=" + reboundSourceId +
                ", createdAt='" + createdAt + '\'' +
                ", player=" + player +
                '}';
    }
}