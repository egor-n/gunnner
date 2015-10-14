package com.gunnner.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class Shot implements Parcelable {
    public static final String POPULAR = "everyone";
    public static final String DEBUTS = "debuts";
    public static final String PLAYOFFS = "playoffs";
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Shot> CREATOR = new Parcelable.Creator<Shot>() {
        @Override
        public Shot createFromParcel(Parcel in) {
            return new Shot(in);
        }

        @Override
        public Shot[] newArray(int size) {
            return new Shot[size];
        }
    };
    @SerializedName("id")
    private int _id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("html_url")
    private String url;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
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
    private boolean hasRebounds = false;
    @SerializedName("images")
    private Images images;
    @SerializedName("user")
    private User user;
    @SerializedName("animated")
    private boolean animated;

    public Shot() {
    }

    public Shot(int _id, String title, int likesCount, int viewsCount, int commentsCount,
                Images images, boolean hasRebounds) {
        this._id = _id;
        this.title = title;
        this.likesCount = likesCount;
        this.viewsCount = viewsCount;
        this.commentsCount = commentsCount;
        this.images = images;
        this.hasRebounds = hasRebounds;
    }

    protected Shot(Parcel in) {
        _id = in.readInt();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
        viewsCount = in.readInt();
        likesCount = in.readInt();
        commentsCount = in.readInt();
        reboundsCount = in.readInt();
        reboundSourceId = in.readInt();
        createdAt = in.readString();
        images = (Images) in.readValue(Images.class.getClassLoader());
        user = (User) in.readValue(User.class.getClassLoader());
        animated = in.readInt() == 1;
    }

    public User getUser() {
        return user;
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
        return images.getTeaser();
    }

    public String getImageUrl() {
        if (images == null) return "";
        return TextUtils.isEmpty(images.getHidpi()) ? images.getNormal() : images.getHidpi();
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
        return getReboundSourceId() != 0 || hasRebounds;
    }

    public boolean isAnimated() {
        return animated;
    }

    @Override
    public String toString() {
        return "Shot{" +
                "id=" + _id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", viewsCount=" + viewsCount +
                ", likesCount=" + likesCount +
                ", commentsCount=" + commentsCount +
                ", reboundsCount=" + reboundsCount +
                ", reboundSourceId=" + reboundSourceId +
                ", createdAt='" + createdAt + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(viewsCount);
        dest.writeInt(likesCount);
        dest.writeInt(commentsCount);
        dest.writeInt(reboundsCount);
        dest.writeInt(reboundSourceId);
        dest.writeString(createdAt);
        dest.writeValue(images);
        dest.writeValue(user);
        dest.writeInt(animated ? 1 : 0);
    }

    public static class Images implements Parcelable {
        @SuppressWarnings("unused")
        public static final Parcelable.Creator<Images> CREATOR = new Parcelable.Creator<Images>() {
            @Override
            public Images createFromParcel(Parcel in) {
                return new Images(in);
            }

            @Override
            public Images[] newArray(int size) {
                return new Images[size];
            }
        };
        @SerializedName("hidpi")
        private String hidpi;
        @SerializedName("normal")
        private String normal;
        @SerializedName("teaser")
        private String teaser;

        public Images(String hidpi) {
            this.hidpi = hidpi;
        }

        protected Images(Parcel in) {
            hidpi = in.readString();
            normal = in.readString();
            teaser = in.readString();
        }

        public String getHidpi() {
            return hidpi;
        }

        public String getNormal() {
            return normal;
        }

        public String getTeaser() {
            return teaser;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(hidpi);
            dest.writeString(normal);
            dest.writeString(teaser);
        }
    }
}
