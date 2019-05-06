package com.alevlash.smscommander.action;

import com.alevlash.smscommander.action.alarm.AlarmAction;
import com.alevlash.smscommander.action.location.LocateAction;

public class ActionResolver {

    public Action getAction(String command) {
        if ("alarm".equalsIgnoreCase(command)) {
            return new AlarmAction();
        } else if ("locate".equalsIgnoreCase(command)) {
            return new LocateAction();
        }
        throw new IllegalArgumentException("Unknown command: " + command);
    }

}
