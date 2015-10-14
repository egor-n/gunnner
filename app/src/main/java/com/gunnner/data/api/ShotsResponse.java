package com.gunnner.data.api;

import com.google.gson.annotations.SerializedName;
import com.gunnner.data.models.Shot;

import java.util.ArrayList;

/**
 * @author Egor N.
 */
public class ShotsResponse extends ListResponse {
    @SerializedName("shots")
    private ArrayList<Shot> shots;

    public ArrayList<Shot> getShots() {
        return shots;
    }
}
