package com.gunnner.ui.shots;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.gunnner.R;
import com.gunnner.data.helpers.Utils;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnTouch;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShotImageFragment extends Fragment {
    public static final String SHOT_IMAGE_URL = "shot_image_url";

    @InjectView(R.id.photo_view) PhotoView mPhotoView;
    @InjectView(R.id.progress_bar) ProgressBar mProgressBar;
    @InjectView(R.id.web_view) WebView mWebView;

    private String mImageUrl;

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
        mImageUrl = getArguments().getString(SHOT_IMAGE_URL);

        if (mImageUrl.endsWith(".gif")) {
            loadInWebView();
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

    @OnTouch(R.id.web_view)
    boolean onGifViewClicked(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_CANCEL ||
                event.getAction() == MotionEvent.ACTION_UP) {
            getActivity().finish();
        }
        return true;
    }

    private void loadWithPicasso() {
        mPhotoView.setVisibility(View.VISIBLE);
        mWebView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        Picasso.with(getActivity())
                .load(mImageUrl)
                .placeholder(R.drawable.placeholder)
                .into(mPhotoView);
    }

    private void loadInWebView() {
        mPhotoView.setVisibility(View.GONE);
        mWebView.setVisibility(View.VISIBLE);
        mWebView.loadData(
                "<html style=\"height: 100%; display: table; margin: auto\">" +
                        "<body style=\"display: table-cell; height: 100%; vertical-align: middle; padding:0; margin:0;\">" +
                        "<img style=\"width:100%;\" src=\"" + mImageUrl + "\">" +
                        "</body>" +
                        "</html>", "text/html", "utf-8");
        mWebView.setBackgroundColor(0x00000000);
        mWebView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
    }
}