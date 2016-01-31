package com.alevlash.smscommander.action;

import android.media.AudioManager;
import android.media.MediaPlayer;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlarmActionTest extends TestCase {

    private AlarmAction _alarmAction;
    @Mock
    private AudioManager _audioManager;
    @Mock
    private MediaPlayer _mediaPlayer;

    @Before
    public void setup() throws Exception {
        _alarmAction = new AlarmAction();
    }

    @Test
    public void stopMediaPlayer() throws Exception {
        final int originalVolume = 5;

        _alarmAction.stopMediaPlayer(_audioManager, _mediaPlayer, originalVolume);

        Mockito.verify(_audioManager).setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
        Mockito.verify(_mediaPlayer).stop();
        Mockito.verify(_mediaPlayer).release();
    }
}