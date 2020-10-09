package com.ardigitalsolutions.urbandesitv.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.ardigitalsolutions.urbandesitv.R;
import com.ardigitalsolutions.urbandesitv.databinding.ActivityFullScreenBinding;
import com.ardigitalsolutions.urbandesitv.databinding.ActivityMainBinding;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class FullScreenActivity extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private ActivityFullScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        playerView = binding.fullscreenExoplayer;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        findViewById(R.id.exo_shrinkscreen).setOnClickListener((View v) -> onBackPressed());
    }

    @Override
    public void onStart() {
        super.onStart();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(this).build();
        TrackSelection.Factory videoFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoFactory);
        LoadControl loadControl = new DefaultLoadControl();
        player = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).setLoadControl(loadControl).build();
        playerView.setPlayer(player);
        String userAgent = Util.getUserAgent(this, "User Agent");
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(
                userAgent, null,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                1800000,
                true);
        String videoURL = "https://284rn5mbr7xv-hls-live.wmncdn.net/urbandesi/7875dbb3746cfea0baefb9342d38f19a.sdp/playlist.m3u8";
        HlsMediaSource mediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoURL));
        player.setPlayWhenReady(true);
        player.prepare(mediaSource);
        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case ExoPlayer.STATE_READY:
                        binding.fullscreenProgress.setVisibility(View.GONE);
                        break;
                    case ExoPlayer.STATE_BUFFERING:
                        binding.fullscreenProgress.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
            }

            @Override
            public void onPositionDiscontinuity(int reason) {
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            }

            @Override
            public void onSeekProcessed() {
            }
        });
        int currentWindow = 0;
        long playbackPosition = 0;
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, true, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseLivePreview();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeLivePreview();
    }

    private void resumeLivePreview() {
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }

    private void pauseLivePreview() {
        if (player != null) {
            if (playerView != null && playerView.getPlayer() != null) {
                playerView.getPlayer().release();
            }
        }
    }
}