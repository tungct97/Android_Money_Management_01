package com.framgia.moneymanagement.screen.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.framgia.moneymanagement.R;

public class AlarmService extends Service {
    private MediaPlayer mMediaPlayer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMediaPlayer = MediaPlayer.create(this, R.raw.baothuc);
        mMediaPlayer.start();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
