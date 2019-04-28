package com.alevlash.smscommander;

public class CommandParser {

    private static String SMS_COMMAND = ".do:";

    public boolean isSmsCommand(String message) {
        return message.toLowerCase().startsWith(SMS_COMMAND);
    }

    public String getCommand(String message) {
        return message.substring(SMS_COMMAND.length());
    }

}
