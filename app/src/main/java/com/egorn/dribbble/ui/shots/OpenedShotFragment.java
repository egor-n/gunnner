package com.egorn.dribbble.ui.shots;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.egorn.dribbble.R;
import com.egorn.dribbble.data.models.Comment;
import com.egorn.dribbble.data.models.Shot;
import com.egorn.dribbble.ui.widgets.ShotView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OpenedShotFragment extends Fragment implements
        OpennedShotController.OnShotLoadedListener {
    private static final String SHOT_ID = "shot_id";

    ShotView mShotHeader;
    @InjectView(R.id.comments_lv) ListView mCommentsLv;

    private int mShotId;
    private Shot mShot;
    private ArrayList<Comment> mComments;

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

        if (getArguments() != null) {
            mShotId = getArguments().getInt(SHOT_ID);
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
        OpennedShotController controller = OpennedShotController.getInstance(mShotId, this);
    }

    @Override
    public void onShotLoaded(Shot shot) {
        if (!isAdded()) {
            return;
        }

        this.mShot = shot;
        mShotHeader = (ShotView) View.inflate(getActivity(), R.layout.shot_view, null);
        mShotHeader.setShot(shot);
        mShotHeader.hideCommentsBadge();
        mCommentsLv.addHeaderView(mShotHeader);
        mCommentsLv.setAdapter(new CommentsAdapter(getActivity(), new ArrayList<Comment>()));
    }

    @Override
    public void onShotLoadingError() {
        Toast.makeText(
                getActivity(),
                "Error loading shot with id = " + mShotId,
                Toast.LENGTH_SHORT
        ).show();
    }
}