package com.alevlash.smscommander.action.alarm;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class PlayerData {

    private final AudioManager _audioManager;
    private final MediaPlayer _mediaPlayer;
    private final int _originalVolume;

    public PlayerData(AudioManager _audioManager, MediaPlayer _mediaPlayer, int _originalVolume) {
        this._audioManager = _audioManager;
        this._mediaPlayer = _mediaPlayer;
        this._originalVolume = _originalVolume;
    }

    public AudioManager getAudioManager() {
        return _audioManager;
    }

    public MediaPlayer getMediaPlayer() {
        return _mediaPlayer;
    }

    public int getOriginalVolume() {
        return _originalVolume;
    }

}
