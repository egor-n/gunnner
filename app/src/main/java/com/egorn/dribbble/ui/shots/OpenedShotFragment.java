package com.egorn.dribbble.ui.shots;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.egorn.dribbble.R;
import com.egorn.dribbble.data.InfiniteScrollListener;
import com.egorn.dribbble.data.models.Comment;
import com.egorn.dribbble.data.models.Shot;
import com.egorn.dribbble.ui.widgets.ShotView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OpenedShotFragment extends Fragment implements
        OpenedShotController.OnCommentsLoadedListener {
    private static final String SHOT = "shot";
    private static final String SHOT_ID = "shot_id";

    ShotView mShotHeader;
    @InjectView(R.id.comments_lv) ListView mCommentsLv;

    private int mShotId;
    private Shot mShot;
    private ArrayList<Comment> mComments;
    private CommentsAdapter mAdapter;

    private OpenedShotController controller;

    public static OpenedShotFragment newInstance(Shot shot) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(SHOT, shot);

        OpenedShotFragment fragment = new OpenedShotFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mShot = getArguments().getParcelable(SHOT);
            if (mShot == null) {
                mShotId = getArguments().getInt(SHOT_ID);
            } else {
                mShotId = mShot.get_id();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shot, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = OpenedShotController.getInstance(mShotId, this);
        prepareHeader();
        prepareCommentsLv();
    }

    private void prepareHeader() {
        mShotHeader = ShotView.inflateBig(mCommentsLv);
        mShotHeader.setShot(mShot);
    }

    private void prepareCommentsLv() {
        mAdapter = new CommentsAdapter(getActivity(), new ArrayList<Comment>());

        mCommentsLv.addHeaderView(mShotHeader, null, false);
        mCommentsLv.setAdapter(mAdapter); // set empty adapter so it shows the header
    }

    @Override
    public void onCommentsLoaded(boolean shouldLoadMore, ArrayList<Comment> comments) {
        if (!isAdded()) {
            return;
        }

        if (mAdapter == null) {
            mAdapter = new CommentsAdapter(getActivity(), comments);
        } else {
            mAdapter.setComments(comments);
        }

        if (shouldLoadMore) {
            mCommentsLv.setOnScrollListener(new InfiniteScrollListener() {
                @Override
                public void loadMore(int page, int totalItemsCount) {
                    controller.loadMore(mShotId);
                }
            });
        } else {
            mCommentsLv.setOnScrollListener(null);
        }
    }

    @Override
    public void onCommentsLoadingError() {
        if (isAdded()) {
            Toast.makeText(
                    getActivity(),
                    "Error loading comments for shot with id = " + mShotId,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}