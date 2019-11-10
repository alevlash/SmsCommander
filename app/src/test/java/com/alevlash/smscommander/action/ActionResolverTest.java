package com.alevlash.smscommander.action;

import com.alevlash.smscommander.action.alarm.AlarmAction;
import com.alevlash.smscommander.action.location.LocateAction;
import com.alevlash.smscommander.action.location.ShowLocation;

import org.junit.Assert;
import org.junit.Test;

public class ActionResolverTest {

    private ActionResolver _actionResolver = new ActionResolver();

    @Test
    public void getAction_nullPassed_throwsException() {
        try {
            _actionResolver.getAction(null);
            Assert.fail("Exception expected");
        } catch (IllegalCommand e) {
            Assert.assertEquals("No command passed", e.getMessage());
        }
    }

    @Test
    public void getAction_emptyStringPassed_throwsException() {
        try {
            _actionResolver.getAction("");
            Assert.fail("Exception expected");
        } catch (IllegalCommand e) {
            Assert.assertEquals("Unknown command: ", e.getMessage());
        }
    }

    @Test
    public void getAction_blankStringPassed_throwsException() {
        try {
            _actionResolver.getAction(" ");
            Assert.fail("Exception expected");
        } catch (IllegalCommand e) {
            Assert.assertEquals("Unknown command:  ", e.getMessage());
        }
    }

    @Test
    public void getAction_invalidCommandPassed_throwsException() {
        try {
            _actionResolver.getAction("blabla");
            Assert.fail("Exception expected");
        } catch (IllegalCommand e) {
            Assert.assertEquals("Unknown command: blabla", e.getMessage());
        }
    }

    @Test
    public void getAction_alarmCommamdPassed_returnsAction() throws IllegalCommand {
        Action action = _actionResolver.getAction("alarm");
        Assert.assertTrue(action instanceof AlarmAction);
    }

    @Test
    public void getAction_locateCommamdPassedInUpperCase_returnsAction() throws IllegalCommand {
        Action action = _actionResolver.getAction("LOCATE");
        Assert.assertTrue(action instanceof LocateAction);
    }

    @Test
    public void getAction_showLocationCommamdPassedInMixedCases_returnsAction() throws IllegalCommand {
        Action action = _actionResolver.getAction("ShOwLoCaTiOn");
        Assert.assertTrue(action instanceof ShowLocation);
    }

}
