package com.alevlash.smscommander.action;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.alevlash.smscommander.R;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmAction implements Action {
    @Override
    public void execute(ActionRequest actionRequest) {
        final AudioManager audioManager = (AudioManager)actionRequest.getContext().getSystemService(Context.AUDIO_SERVICE);
        final int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        final MediaPlayer mediaPlayer = MediaPlayer.create(actionRequest.getContext(), R.raw.siren_noise);

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        mediaPlayer.setLooping(true);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
                    mediaPlayer.stop();
                } finally {
                    mediaPlayer.release();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000 * 60);

    }
}
