package com.egorn.dribbble.ui.shots;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.egorn.dribbble.R;
import com.egorn.dribbble.data.helpers.Utils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShotImageFragment extends Fragment {
    public static final String SHOT_IMAGE_URL = "shot_image_url";

    @InjectView(R.id.photoView) PhotoView mPhotoView;
    @InjectView(R.id.gifView) GifImageView mGifView;
    @InjectView(R.id.progress_bar) ProgressBar mProgressBar;

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
        Utils.setInsets(getActivity(), mProgressBar);
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

    @OnClick(R.id.gifView)
    void onGifViewClicked() {
        getActivity().finish();
    }

    private void loadWithPicasso() {
        mPhotoView.setVisibility(View.VISIBLE);
        mGifView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        Picasso.with(getActivity())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(mPhotoView);
    }

    private void loadWithIon() {
        mPhotoView.setVisibility(View.GONE);
        mGifView.setVisibility(View.VISIBLE);

        Ion.with(getActivity())
                .load(imageUrl)
                .progressBar(mProgressBar)
                .write(getFile())
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {
                        if (!isAdded()) {
                            return;
                        }

                        mProgressBar.setVisibility(View.GONE);
                        if (e != null) {
                            Toast.makeText(
                                    getActivity(),
                                    "Something went wrong",
                                    Toast.LENGTH_SHORT
                            ).show();
                            e.printStackTrace();
                        }

                        try {
                            mGifView.setImageDrawable(new GifDrawable(result));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }

    private File getFile() {
        File file = new File(Environment.getExternalStorageDirectory().getPath()
                + "/gunnner/gifs" + System.currentTimeMillis());
        file.getParentFile().mkdirs();
        return file;
    }
}