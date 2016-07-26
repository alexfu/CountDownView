package com.alexfu.countdownview.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.*;
import android.util.Log;

import java.io.IOException;

public class TimerService extends Service implements MediaPlayer.OnErrorListener {
    private MediaPlayer mMediaPlayer;
    private Vibrator mShakeyThing;
    private long mCurrentMillis;
    private CountDownTimer mTimer;
    private Messenger mMessenger;
    private String mAlarmSound;

    @Override
    public void onCreate() {
        super.onCreate();
        mShakeyThing = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.hasExtra("alarm_sound"))
            mAlarmSound = intent.getStringExtra("alarm_sound");
        mMessenger = intent.getParcelableExtra("messenger");

        if(mTimer != null) {
            mTimer.cancel();
        }
        else {
            mCurrentMillis = intent.getLongExtra("millis", 0);
        }

        mTimer = new CountDownTimer(mCurrentMillis, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCurrentMillis = millisUntilFinished;
                postTime(mCurrentMillis);
            }

            @Override
            public void onFinish() {
                postTime(0);
                mTimer = null;
                playAlarm();
            }
        }.start();
        return START_NOT_STICKY;
    }

    private void postTime(long timeInMillis) {
        Message m = Message.obtain();
        m.obj = timeInMillis;
        try {
            mMessenger.send(m);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void playAlarm() {
        if(hasAlarmSound()) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnErrorListener(this);
            try {
                AssetFileDescriptor afd = getAssets().openFd(mAlarmSound);
                mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(mShakeyThing != null)
            mShakeyThing.vibrate(new long[] {0, 500, 300}, 0);
    }

    private void stopAlarm() {
        if(mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        if(mShakeyThing != null)
            mShakeyThing.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTimer != null) {
            mTimer.cancel();
        }
        stopAlarm();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        Log.e("TimerService", "Error occurred while playing audio.");
        mediaPlayer.stop();
        mediaPlayer.release();
        mMediaPlayer = null;
        return true;
    }

    private boolean hasAlarmSound() {
        return mAlarmSound != null;
    }
}
