package com.egorn.dribbble.data.api;

import com.google.gson.annotations.SerializedName;

/**
 * @author Egor N.
 */
public class ListResponse {
    protected int page;
    protected int pages;
    @SerializedName("per_page")
    protected int perPage;
    protected int total;

    public int getTotal() {
        return total;
    }
}
