package com.alevlash.smscommander.action.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class ButtonEventReceiver extends BroadcastReceiver {

    private final AlarmAction _alarmAction;
    private final IntentFilter _filter;
    private final PlayerData _playerData;

    public ButtonEventReceiver(AlarmAction alarmAction, IntentFilter filter, PlayerData playerData) {
        _alarmAction = alarmAction;
        _filter = filter;
        _playerData = playerData;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (_filter.hasAction(intent.getAction())) {
            _alarmAction.stopMediaPlayer(_playerData);
        }
    }

}
