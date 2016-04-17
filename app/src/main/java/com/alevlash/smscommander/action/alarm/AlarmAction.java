package com.alevlash.smscommander.action.alarm;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.alevlash.smscommander.R;
import com.alevlash.smscommander.action.Action;
import com.alevlash.smscommander.action.ActionRequest;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmAction implements Action {

    private static int ALARM_LENGTH_IN_MINUTES = 1;

    @Override
    public void execute(final ActionRequest actionRequest) {
        final AudioManager audioManager = (AudioManager) actionRequest.getContext().getSystemService(Context.AUDIO_SERVICE);
        final int originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        final MediaPlayer mediaPlayer = MediaPlayer.create(actionRequest.getContext(), R.raw.siren_noise);
        final PlayerData playerData = new PlayerData(audioManager, mediaPlayer, originalVolume);

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        mediaPlayer.setLooping(true);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you

        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        final ButtonEventReceiver buttonEventReceiver = new ButtonEventReceiver(this, filter, playerData);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                actionRequest.getContext().getApplicationContext().unregisterReceiver(buttonEventReceiver);
                stopMediaPlayer(playerData);

            }
        };

        Timer timer = new Timer();
        timer.schedule(task, ALARM_LENGTH_IN_MINUTES * 60 * 1000);
        actionRequest.getContext().getApplicationContext().registerReceiver(buttonEventReceiver, filter);

    }

    void stopMediaPlayer(PlayerData playerData) {
        try {
            if (playerData.getMediaPlayer().isPlaying()) {
                try {
                    playerData.getMediaPlayer().stop();
                    playerData.getAudioManager().setStreamVolume(AudioManager.STREAM_MUSIC, playerData.getOriginalVolume(), 0);
                } finally {
                    playerData.getMediaPlayer().release();
                }
            }
        } catch (IllegalStateException e) {
            Log.i("AlarmAction", "Media player has been already released: " + e);
        }
    }

}
