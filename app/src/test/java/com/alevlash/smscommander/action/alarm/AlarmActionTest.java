package com.alevlash.smscommander.action.alarm;

import android.media.AudioManager;
import android.media.MediaPlayer;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class AlarmActionTest extends TestCase {

    private AlarmAction _alarmAction;
    @Mock
    private AudioManager _audioManager;
    @Mock
    private MediaPlayer _mediaPlayer;

    private PlayerData _playerData;

    @Before
    public void setup() throws Exception {
        _alarmAction = new AlarmAction();
        _playerData = new PlayerData(_audioManager, _mediaPlayer, 5);
    }

    @Test
    public void stopMediaPlayer_mediaIsPlaying_stopsPlayer() throws Exception {
        Mockito.when(_mediaPlayer.isPlaying()).thenReturn(true);

        _alarmAction.stopMediaPlayer(_playerData);

        Mockito.verify(_audioManager).setStreamVolume(AudioManager.STREAM_MUSIC, _playerData.getOriginalVolume(), 0);
        Mockito.verify(_mediaPlayer).stop();
        Mockito.verify(_mediaPlayer).release();
    }

    @Test
    public void stopMediaPlayer_mediaIsNotPlaying_doesNothing() throws Exception {
        Mockito.when(_mediaPlayer.isPlaying()).thenReturn(false);

        _alarmAction.stopMediaPlayer(_playerData);

        Mockito.verify(_audioManager, times(0)).setStreamVolume(AudioManager.STREAM_MUSIC, _playerData.getOriginalVolume(), 0);
        Mockito.verify(_mediaPlayer, times(0)).stop();
        Mockito.verify(_mediaPlayer, times(0)).release();
    }

}