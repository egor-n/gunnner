package com.egorn.dribbble.ui.shots;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.egorn.dribbble.R;
import com.egorn.dribbble.data.models.Shot;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShotsFragment extends Fragment implements AbsListView.OnItemClickListener, ShotsController.OnShotsLoadedListener {
    private static final String REFERENCE = "reference";

    @InjectView(R.id.shots_list) AbsListView mListView;

    private String mReference;
    private OnShotClickedListener mListener;
    private ListAdapter mAdapter;
    private ArrayList<Shot> shots = new ArrayList<Shot>();

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

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ShotsController controller = ShotsController.getInstance(mReference, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            mListener.onShotClicked(shots.get(position).get_id());
        }
    }

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override public void onShotsLoaded(ArrayList<Shot> shots) {
        if (isAdded()) {
            this.shots = shots;
            mAdapter = new ShotsAdapter(shots, getActivity());
            mListView.setAdapter(mAdapter);
        }
    }

    @Override public void onShotsError() {

    }

    public interface OnShotClickedListener {
        public void onShotClicked(int shotId);
    }
}
