package com.alevlash.smscommander.action;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmAction implements Action {
    @Override
    public void execute(ActionRequest actionRequest) {
        final Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final Ringtone ringtone = RingtoneManager.getRingtone(actionRequest.getContext(), alert);
        final AudioManager audioManager = (AudioManager)actionRequest.getContext().getSystemService(Context.AUDIO_SERVICE);
        final int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);

        audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
        ringtone.play();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                audioManager.setStreamVolume(AudioManager.STREAM_RING, currentVolume, 0);
                ringtone.stop();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000 * 60);

    }
}
