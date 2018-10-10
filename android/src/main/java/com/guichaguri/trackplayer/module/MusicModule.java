package com.guichaguri.trackplayer.module;


import android.content.Context;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;

import java.lang.reflect.InvocationTargetException;
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
        Log.e("PLAYER", "addJonathan");
        ReactContext context = getReactApplicationContext();
        HashMap track = tracks.toHashMap();
        track.get("id").toString();
        setTitle(track.get("title").toString());
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        RenderersFactory renderersFactory = new DefaultRenderersFactory(context);
        //SimpleExoPlayer exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        Looper looper = context.getCurrentActivity().getApplicationContext().getMainLooper();
        SimpleExoPlayer exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector );

        //exoPlayer.getApplicationLooper();

        DefaultDataSourceFactory dataSourceFactory = new  DefaultDataSourceFactory(context, "TrackPlayer");
        Uri audioSourceUri = Uri.parse(track.get("url").toString());
        MediaSource audioSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(audioSourceUri);
        exoPlayer.prepare(audioSource);

        try {
            playerNotificationManager.setPlayer(exoPlayer);
        } catch (Exception e) {
            /* This is a generic Exception handler which means it can handle
             * all the exceptions. This will execute if the exception is not
             * handled by previous catch blocks.
             */
            System.out.println("Exception occurred");
        }


        exoPlayer.setPlayWhenReady(true);
        Log.e("PLAYER", "addJonathan end");

    }

    @ReactMethod
    public void setupPlayer(ReadableMap data) {
        Log.e("PLAYER", "setupPlayer");
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
        Log.e("PLAYER", "setupPlayer end");
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
