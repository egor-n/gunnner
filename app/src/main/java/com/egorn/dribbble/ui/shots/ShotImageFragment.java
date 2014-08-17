package com.egorn.dribbble.ui.shots;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egorn.dribbble.R;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShotImageFragment extends Fragment {
    public static final String SHOT_IMAGE_URL = "shot_image_url";

    @InjectView(R.id.photoView)
    PhotoView mPhotoView;

    private String imageUrl;

    public static ShotImageFragment newInstance(String shotImageUrl) {
        ShotImageFragment fragment = new ShotImageFragment();
        Bundle args = new Bundle();
        args.putString(SHOT_IMAGE_URL, shotImageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shot_image, container, false);
        ButterKnife.inject(this, rootView);
        mPhotoView.setAllowParentInterceptOnEdge(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageUrl = getArguments().getString(SHOT_IMAGE_URL);

        if (imageUrl.endsWith(".gif")) {
            loadWithIon();
        } else {
            loadWithPicasso();
        }

        mPhotoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float v, float v2) {
                getActivity().finish();
            }
        });
    }

    private void loadWithPicasso() {
        Picasso.with(getActivity())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(mPhotoView);
    }

    private void loadWithIon() {
        Ion.with(mPhotoView)
                .placeholder(R.drawable.placeholder)
                .load(imageUrl);
    }
}