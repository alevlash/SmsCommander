package com.alevlash.smscommander.commandparser;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {

    public boolean isSmsCommand(String message) {
        return message.toLowerCase().startsWith(CommandConstants.SMS_COMMAND);
    }

    public ParsedCommand getCommand(String message) {
        int beginIndex = CommandConstants.SMS_COMMAND.length();
        int endIndex = message.indexOf(CommandConstants.PARAMS_START);

        String command = message.substring(beginIndex, endIndex == -1 ? message.length() : endIndex);
        Map<String, String> parametersMap = new HashMap<>();

        if (endIndex != -1 && endIndex < message.length() - 1) {
            String parametersString = message.substring(endIndex + 1);
            String[] params = parametersString.split(CommandConstants.PARAMS_DIVIDER);

            for (String param : params) {
                int index = param.indexOf(CommandConstants.PARAM_VALUE_DIVIDER);
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
