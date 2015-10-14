package com.gunnner.ui.shots;

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

import com.gunnner.R;
import com.gunnner.data.InfiniteScrollListener;
import com.gunnner.data.UserController;
import com.gunnner.data.helpers.Utils;
import com.gunnner.data.models.Shot;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShotsFragment extends Fragment implements AbsListView.OnItemClickListener,
        ShotsController.OnShotsLoadedListener {
    public static final int FOLLOWING = 1;
    public static final int LIKES = 2;
    public static final int MY_SHOTS = 3;
    public static final int SEARCH = 4;

    private static final String REFERENCE = "reference";
    private static final String TYPE = "type";
    private static final String SHOTS = "shots";

    @InjectView(R.id.shots_list)
    AbsListView mListView;
    @InjectView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @InjectView(R.id.empty)
    TextView mEmptyView;

    private String mReference;
    private int mType;
    private ShotsAdapter mAdapter;
    private ArrayList<Shot> shots = new ArrayList<Shot>();

    private OnShotClickedListener mListener;

    private ShotsController controller;
    private UserController userController;

    public static Fragment newInstance(ArrayList<Shot> shots, int type) {
        ShotsFragment fragment = new ShotsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(SHOTS, shots);
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

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
            public void loadMore(int page, int totalItemsCount) {
                if (mType == FOLLOWING) {
                    userController.loadMoreFollowingShots();
                } else if (mType == LIKES) {
                    userController.loadMoreLikesShots();
                } else if (mType == MY_SHOTS) {
                    userController.loadMorePlayerShots();
                } else if (mType == SEARCH) {
                    ((ShotsActivity) getActivity()).loadMore(ShotsFragment.this);
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
        mProgressBar.setVisibility(View.VISIBLE);

        shots = getArguments().getParcelableArrayList(SHOTS);
        if (shots != null && !shots.isEmpty()) {
            onShotsLoaded(shots);
        }

        if (mType == FOLLOWING) {
            Utils.setInsets(getActivity(), mListView);
            userController = UserController.getInstance(getActivity());
            userController.getFollowingShots(this);
        } else if (mType == LIKES) {
            Utils.setInsets(getActivity(), mListView);
            userController = UserController.getInstance(getActivity());
            userController.getLikesShots(this);
        } else if (mType == MY_SHOTS) {
            Utils.setInsets(getActivity(), mListView);
            userController = UserController.getInstance(getActivity());
            userController.getPlayerShots(this);
        } else if (mType == SEARCH) {
            Utils.setInsets(getActivity(), mListView);
        } else {
            Utils.setBottomInsets(getActivity(), mListView);
            addTopPadding();
            controller = ShotsController.getInstance(mReference, this);
        }
    }

    private void addTopPadding() {
        mListView.setPadding(
                mListView.getPaddingLeft(),
                mListView.getPaddingTop() + Utils.dpToPixels(getActivity(), 48f), // + tabs height
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
                mAdapter = new ShotsAdapter(shots);
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
        void onShotClicked(Shot shot);
    }
}
