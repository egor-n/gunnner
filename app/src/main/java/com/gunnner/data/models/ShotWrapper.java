package com.gunnner.data.models;

import com.google.gson.annotations.SerializedName;

public class ShotWrapper {
    @SerializedName("shot")
    private Shot shot;

    public Shot getShot() {
        return shot;
    }
}
