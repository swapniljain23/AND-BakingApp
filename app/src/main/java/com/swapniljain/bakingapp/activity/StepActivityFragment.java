package com.swapniljain.bakingapp.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.net.Uri;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import com.swapniljain.bakingapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class StepActivityFragment extends Fragment {

    public static String STEP_VIDEO_URL_EXTRA = "step_video_url_extra";
    public static String STEP_DESCRIPTION_EXTRA = "step_description";

    public static String PLAYBACK_POSITION_EXTRA = "playback_position";
    public static String CURRENT_WINDOW_EXTRA = "current_window";
    public static String PLAY_WHEN_READY_EXTRA = "lets_play";

    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private TextView tvStepDescription;

    private long mPlaybackPosition;
    private int mCurrentWindow;
    private boolean mPlayWhenReady = true;
    private String mVideoUrl;
    private String mStepDescription;

    public StepActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step,container, false);

        mPlayerView = view.findViewById(R.id.player_view);
        tvStepDescription = view.findViewById(R.id.tv_step_instructions);

        mVideoUrl = getArguments().getString(STEP_VIDEO_URL_EXTRA);
        mStepDescription = getArguments().getString(STEP_DESCRIPTION_EXTRA);
        initializeVideoPlayer(savedInstanceState);
        tvStepDescription.setText(mStepDescription);

        if (mVideoUrl.equals("")) {
            mPlayerView.setVisibility(View.GONE);
        }
        return view;
    }

    public void initializeVideoPlayer(Bundle savedInstanceState) {
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());
        mPlayerView.setPlayer(mExoPlayer);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(new
                DefaultHttpDataSourceFactory("bakingApp"))
                .createMediaSource(Uri.parse(mVideoUrl));
        mExoPlayer.prepare(mediaSource, false, false);

        if (savedInstanceState == null) {
            mExoPlayer.setPlayWhenReady(true);
        } else if (savedInstanceState.containsKey(PLAYBACK_POSITION_EXTRA)) {
            mExoPlayer.setPlayWhenReady(savedInstanceState.getBoolean(PLAY_WHEN_READY_EXTRA));
            mExoPlayer.seekTo(savedInstanceState.getInt(CURRENT_WINDOW_EXTRA),
                    savedInstanceState.getLong(PLAYBACK_POSITION_EXTRA));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlaybackPosition = mExoPlayer.getCurrentPosition();
        mCurrentWindow = mExoPlayer.getCurrentWindowIndex();
        mPlayWhenReady = mExoPlayer.getPlayWhenReady();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYBACK_POSITION_EXTRA, mPlaybackPosition);
        outState.putInt(CURRENT_WINDOW_EXTRA, mCurrentWindow);
        outState.putBoolean(PLAY_WHEN_READY_EXTRA, mPlayWhenReady);
    }

    private void releasePlayer() {
        if(mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void hideSystemUI() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
