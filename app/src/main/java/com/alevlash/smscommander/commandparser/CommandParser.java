package com.alevlash.smscommander.commandparser;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {

    private static String SMS_COMMAND = ".do:";
    private static String PARAMS_START = "?";
    private static String PARAMS_DIVIDER = "&";
    private static String PARAM_VALUE_DIVIDER = "=";

    public boolean isSmsCommand(String message) {
        return message.toLowerCase().startsWith(SMS_COMMAND);
    }

    public ParsedCommand getCommand(String message) {
        int beginIndex = SMS_COMMAND.length();
        int endIndex = message.indexOf(PARAMS_START);

        String command = message.substring(beginIndex, endIndex == -1 ? message.length() : endIndex);
        Map<String, String> parametersMap = new HashMap<>();

        if (endIndex != -1 && endIndex < message.length() - 1) {
            String parametersString = message.substring(endIndex + 1);
            String[] params = parametersString.split(PARAMS_DIVIDER);

            for (String param : params) {
                int index = param.indexOf(PARAM_VALUE_DIVIDER);
                if (index == -1) {
                    parametersMap.put(param, null);
                } else {
                    String paramName = param.substring(0, index);
                    String paramValue = param.substring(index + 1);
                    parametersMap.put(paramName, paramValue);
                }
            }

        }

        return new ParsedCommand(command, parametersMap);
    }

}
