package com.gunnner.data.api;

import com.gunnner.data.models.Shot;

import java.util.ArrayList;

/**
 * @author Egor N.
 */
public class ShotsResponse extends ListResponse {
    private ArrayList<Shot> shots;

    public ArrayList<Shot> getShots() {
        return shots;
    }
}
