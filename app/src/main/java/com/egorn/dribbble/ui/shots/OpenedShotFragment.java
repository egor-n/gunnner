package com.egorn.dribbble.ui.shots;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.egorn.dribbble.R;
import com.egorn.dribbble.Utils;
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

    public static OpenedShotFragment newInstance(int shotId) {
        Bundle arguments = new Bundle();
        arguments.putInt(SHOT_ID, shotId);

        OpenedShotFragment fragment = new OpenedShotFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
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
        Utils.setInsets(getActivity(), mCommentsLv);
        if (mShot != null) {
            prepareHeader();
        }
        controller = OpenedShotController.getInstance(mShotId, this);
    }

    private void prepareHeader() {
        mShotHeader = ShotView.inflateBig(mCommentsLv);
        mShotHeader.setShot(mShot);

        if (mShot.getReboundSourceId() != 0) {
            mShotHeader.setOnReboundClickListener();
        }

        mShotHeader.setImageClickListener();

        mCommentsLv.addHeaderView(mShotHeader, null, false);

        mAdapter = new CommentsAdapter(getActivity(), new ArrayList<Comment>());
        mCommentsLv.setAdapter(mAdapter); // set empty adapter so it shows the header
    }

    @Override
    public void onShotLoaded(Shot shot) {
        if (isAdded()) {
            this.mShot = shot;
            if (mShotHeader == null) {
                prepareHeader();
            } else {
                updateHeader(shot);
            }
        }
    }

    private void updateHeader(Shot shot) {
        mShotHeader.setShot(shot);
    }

    @Override
    public void onCommentsLoaded(boolean shouldLoadMore, ArrayList<Comment> comments) {
        if (!isAdded()) {
            return;
        }

        if (mShotHeader == null) {
            // Shot wasn't loaded yet, so we have to wait for it to set adapter,
            // because you can't addHeaderView after setAdapter
            return;
        }

        this.mComments = comments;

        if (mAdapter == null) {
            mAdapter = new CommentsAdapter(getActivity(), comments);
        } else {
            mAdapter.setComments(comments);
        }

        if (shouldLoadMore) {
            mCommentsLv.setOnScrollListener(new InfiniteScrollListener() {
                @Override
                public void loadMore(int page, int totalItemsCount) {
                    if (controller != null) {
                        controller.loadMore(mShotId);
                    }
                }
            });
        } else {
            mCommentsLv.setOnScrollListener(null);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.shot, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String url = "https://dribbble.com/shots/" + mShotId;

        int id = item.getItemId();
        if (id == R.id.action_share) {
            if (mShot != null) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, mShot.getTitle() + "\n" + url);
                intent.setType("text/plain");
                startActivity(intent);
                return true;
            }
        } else if (id == R.id.action_browser) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}