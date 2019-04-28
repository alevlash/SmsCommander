package com.alevlash.smscommander.action.alarm;

import android.content.Intent;
import android.content.IntentFilter;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ButtonEventReceiverTest extends TestCase {

    private ButtonEventReceiver _buttonEventReceiver;

    @Mock
    private AlarmAction _alarmAction;
    @Mock
    private Intent _intent;
    @Mock
    private IntentFilter _intentFilter;

    private PlayerData _playerData;

    @Before
    public void setup() throws Exception {
        _playerData = new PlayerData(null, null, 5);
        _buttonEventReceiver = new ButtonEventReceiver(_alarmAction, _intentFilter, _playerData);
    }

    @Test
    public void onReceive_screenOnEvent_stopsPlayer() {
        assertOnReceive(Intent.ACTION_SCREEN_ON, true, 1);
    }

    @Test
    public void onReceive_notHandledAction_doesNothing() {
        assertOnReceive(Intent.ACTION_APP_ERROR, false, 0);
    }

    private void assertOnReceive(String action, boolean isActionInFilter, int expectedStopPlayeCalls) {

        Mockito.when(_intent.getAction()).thenReturn(action);
        Mockito.when(_intentFilter.hasAction(action)).thenReturn(isActionInFilter);

        Mockito.doNothing().when(_alarmAction).stopMediaPlayer(_playerData);

        _buttonEventReceiver.onReceive(null, _intent);

        Mockito.verify(_alarmAction, times(expectedStopPlayeCalls)).stopMediaPlayer(_playerData);
    }

}
