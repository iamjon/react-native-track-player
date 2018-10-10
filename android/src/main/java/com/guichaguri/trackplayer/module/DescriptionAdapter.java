package com.guichaguri.trackplayer.module;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;

class DescriptionAdapter implements
        PlayerNotificationManager.MediaDescriptionAdapter {

    @Override
    public String getCurrentContentTitle(Player player) {
        return "Kaki";
    }

    @Nullable
    @Override
    public String getCurrentContentText(Player player) {
        return getCurrentContentText(player);
    }

    @Nullable
    @Override
    public Bitmap getCurrentLargeIcon(Player player,
                                      PlayerNotificationManager.BitmapCallback callback) {
        return getCurrentLargeIcon(player, callback);
    }

    @Nullable
    @Override
    public PendingIntent createCurrentContentIntent(Player player) {
        return createCurrentContentIntent(player);
    }
}
