package com.guichaguri.trackplayer.module;


import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.guichaguri.trackplayer.service.Utils;

import java.util.HashMap;


/**
 * @author Guichaguri
 */
public class MusicModule extends ReactContextBaseJavaModule {

    private PlayerNotificationManager playerNotificationManager;
    private String title;

    public MusicModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "TrackPlayerModule";
    }

    @Override
    public void initialize() {
        ReactContext context = getReactApplicationContext();
    }



    /* ****************************** API ****************************** */


    @ReactMethod
    public void addJonathan(ReadableMap tracks) {
        ReactContext context = getReactApplicationContext();
        HashMap track = tracks.toHashMap();
        track.get("id").toString();
        TrackSelector trackSelector = new DefaultTrackSelector();
        SimpleExoPlayer exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        DefaultDataSourceFactory dataSourceFactory = new  DefaultDataSourceFactory(context, "TrackPlayer");
        Uri audioSourceUri = Uri.parse(track.get("url").toString());
        MediaSource audioSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(audioSourceUri);
        exoPlayer.prepare(audioSource);
        playerNotificationManager.setPlayer(exoPlayer);
        exoPlayer.setPlayWhenReady(true);

    }

    @ReactMethod
    public void setupPlayer() {
        Log.e(Utils.LOG, "setupPlayer");
            playerNotificationManager = new PlayerNotificationManager( getReactApplicationContext(),"11",11, new DescriptionAdapter());
            // omit skip previous and next actions
            playerNotificationManager.setUseNavigationActions(false);
            // omit fast forward action by setting the increment to zero
            playerNotificationManager.setFastForwardIncrementMs(0);
            // omit rewind action by setting the increment to zero
            playerNotificationManager.setRewindIncrementMs(0);
            // omit the stop action
            //playerNotificationManager.setStopAction(null);
        playerNotificationManager.setBadgeIconType(0);
        playerNotificationManager.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        playerNotificationManager.setOngoing(true);
        Log.e(Utils.LOG, "setupPlayer end");
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
