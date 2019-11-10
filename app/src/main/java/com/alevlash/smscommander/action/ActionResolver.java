package com.alevlash.smscommander.action;

import com.alevlash.smscommander.action.alarm.AlarmAction;
import com.alevlash.smscommander.action.location.LocateAction;
import com.alevlash.smscommander.action.location.ShowLocation;

public class ActionResolver {

    public Action getAction(String command) throws IllegalCommand {
        ActionType actionType = getActionType(command);

        switch (actionType) {
            case ALARM:
                return new AlarmAction();
            case LOCATE:
                return new LocateAction();
            case SHOWLOCATION:
                return new ShowLocation();
            default:
                throw new IllegalCommand("Unsupported command: " + command);
        }

    }

    private ActionType getActionType(String command) throws IllegalCommand {
        ActionType actionType;
        try {
            actionType = ActionType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalCommand("Unknown command: " + command);
        } catch (NullPointerException e) {
            throw new IllegalCommand("No command passed");
        }
        return actionType;
    }

}
