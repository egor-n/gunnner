package com.egorn.dribbble.ui.shots;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.egorn.dribbble.R;
import com.egorn.dribbble.Utils;
import com.egorn.dribbble.data.InfiniteScrollListener;
import com.egorn.dribbble.data.PlayerController;
import com.egorn.dribbble.data.models.Shot;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShotsFragment extends Fragment implements AbsListView.OnItemClickListener,
        ShotsController.OnShotsLoadedListener {
    public static final int FOLLOWING = 1;
    public static final int LIKES = 2;
    public static final int MY_SHOTS = 3;

    private static final String REFERENCE = "reference";
    private static final String TYPE = "type";

    @InjectView(R.id.shots_list) AbsListView mListView;
    @InjectView(R.id.progress_bar) ProgressBar mProgressBar;
    @InjectView(R.id.empty) TextView mEmptyView;

    private String mReference;
    private int mType;
    private ShotsAdapter mAdapter;
    private ArrayList<Shot> shots = new ArrayList<Shot>();

    private OnShotClickedListener mListener;
    private OnTabsHideListener tabsListener;

    private ShotsController controller;
    private PlayerController playerController;

    public static ShotsFragment newInstance(String reference) {
        ShotsFragment fragment = new ShotsFragment();
        Bundle args = new Bundle();
        args.putString(REFERENCE, reference);
        fragment.setArguments(args);
        return fragment;
    }

    public static ShotsFragment newInstance(int type) {
        ShotsFragment fragment = new ShotsFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mReference = getArguments().getString(REFERENCE);
            if (TextUtils.isEmpty(mReference)) {
                mType = getArguments().getInt(TYPE);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shots, container, false);
        ButterKnife.inject(this, rootView);
        setEmptyText(getString(R.string.no_shots));
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void hideTabs(boolean hide) {
                if (tabsListener != null) {
                    if (hide) {
                        tabsListener.hideTabs(true);
                    } else {
                        tabsListener.hideTabs(false);
                    }
                }
            }

            @Override
            public void loadMore(int page, int totalItemsCount) {
                if (mType == FOLLOWING) {
                    playerController.loadMoreFollowingShots();
                } else if (mType == LIKES) {
                    playerController.loadMoreLikesShots();
                } else if (mType == MY_SHOTS) {
                    playerController.loadMorePlayerShots();
                } else {
                    controller.loadMore(mReference);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnShotClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnShotClickedListener");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            tabsListener = (OnTabsHideListener) getParentFragment();
        } catch (ClassCastException ignored) {
        }

        mProgressBar.setVisibility(View.VISIBLE);
        if (mType == FOLLOWING) {
            Utils.setInsets(getActivity(), mListView);
            playerController = PlayerController.getInstance(getActivity());
            playerController.getFollowingShots(this);
        } else if (mType == LIKES) {
            Utils.setInsets(getActivity(), mListView);
            playerController = PlayerController.getInstance(getActivity());
            playerController.getLikesShots(this);
        } else if (mType == MY_SHOTS) {
            Utils.setInsets(getActivity(), mListView);
            playerController = PlayerController.getInstance(getActivity());
            playerController.getPlayerShots(this);
        } else {
            Utils.setBottomRightInsets(getActivity(), mListView);
            addTopPadding();
            controller = ShotsController.getInstance(mReference, this);
        }
    }

    private void addTopPadding() {
        mListView.setPadding(
                mListView.getPaddingLeft(),
                mListView.getPaddingTop() + Utils.dpToPixels(getActivity(), 44f),
                mListView.getPaddingRight(),
                mListView.getPaddingBottom()
        );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            mListener.onShotClicked(shots.get(position));
        }
    }

    public void setEmptyText(CharSequence emptyText) {
        mEmptyView.setText(emptyText);
    }

    @Override
    public void onShotsLoaded(ArrayList<Shot> shots) {
        if (isAdded()) {
            this.shots = shots;
            mProgressBar.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = new ShotsAdapter(shots, getActivity());
                mListView.setAdapter(mAdapter);
            } else {
                mAdapter.setItems(shots);
            }

            if (shots.size() == 0) {
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onShotsError() {
        if (isAdded()) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public interface OnShotClickedListener {
        public void onShotClicked(Shot shot);
    }

    public interface OnTabsHideListener {
        public void hideTabs(boolean hide);
    }
}
