package com.gunnner.data.api;

import com.gunnner.data.models.Comment;

import java.util.ArrayList;

/**
 * @author Egor N.
 */
public class CommentsResponse extends ListResponse {
    private ArrayList<Comment> comments;

    public ArrayList<Comment> getComments() {
        return comments;
    }
}
