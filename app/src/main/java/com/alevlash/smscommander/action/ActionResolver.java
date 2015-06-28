package com.alevlash.smscommander.action;

public class ActionResolver {

    public Action getAction(String command) {
        if ("alarm".equalsIgnoreCase(command)) {
            return new AlarmAction();
        }
        throw new IllegalArgumentException("Unknown command: " + command);
    }

}
