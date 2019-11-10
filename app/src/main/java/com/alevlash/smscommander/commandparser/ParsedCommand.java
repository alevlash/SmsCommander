package com.alevlash.smscommander.commandparser;

import java.util.Map;

public class ParsedCommand {

    private final String _command;
    private final Map<String, String> _parametersMap;


    public ParsedCommand(String command, Map<String, String> parameterMap) {
        _command = command;
        _parametersMap = parameterMap;
    }

    public String getCommand() {
        return _command;
    }

    public Map<String, String> getParametersMap() {
        return _parametersMap;
    }

}
