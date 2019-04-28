package com.alevlash.smscommander.action;

import com.alevlash.smscommander.action.alarm.AlarmAction;

public class ActionResolver {

    public Action getAction(String command) {
        if ("alarm".equalsIgnoreCase(command)) {
            return new AlarmAction();
        }
        throw new IllegalArgumentException("Unknown command: " + command);
    }

}
