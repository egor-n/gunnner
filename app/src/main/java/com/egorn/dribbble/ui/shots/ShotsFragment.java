package com.egorn.dribbble.ui.shots;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.egorn.dribbble.R;
import com.egorn.dribbble.data.InfiniteScrollListener;
import com.egorn.dribbble.data.models.Shot;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShotsFragment extends Fragment implements AbsListView.OnItemClickListener, ShotsController.OnShotsLoadedListener {
    private static final String REFERENCE = "reference";

    @InjectView(R.id.shots_list) AbsListView mListView;
    @InjectView(R.id.progress_bar) ProgressBar mProgressBar;
    @InjectView(android.R.id.empty) TextView mEpmtyView;

    private String mReference;
    private OnShotClickedListener mListener;
    private ShotsAdapter mAdapter;
    private ArrayList<Shot> shots = new ArrayList<Shot>();

    private ShotsController controller;

    public static ShotsFragment newInstance(String reference) {
        ShotsFragment fragment = new ShotsFragment();
        Bundle args = new Bundle();
        args.putString(REFERENCE, reference);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mReference = getArguments().getString(REFERENCE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shots, container, false);
        ButterKnife.inject(this, rootView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void loadMore(int page, int totalItemsCount) {
                controller.loadMore(mReference);
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
        controller = ShotsController.getInstance(mReference, this);
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
        (mEpmtyView).setText(emptyText);
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
        }
    }

    @Override
    public void onShotsError() {
        if (isAdded()) {
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(
                    getActivity(),
                    "No more shots",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public interface OnShotClickedListener {
        public void onShotClicked(Shot shot);
    }
}
