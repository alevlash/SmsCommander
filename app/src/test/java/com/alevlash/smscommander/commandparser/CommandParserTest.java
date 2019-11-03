package com.alevlash.smscommander.commandparser;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommandParserTest {

    private CommandParser _commandParser;

    @Before
    public void setup() {
        _commandParser = new CommandParser();
    }

    @Test
    public void isSmsCommand_smsCommandPassed_returnsTrue() {
        Assert.assertTrue(_commandParser.isSmsCommand(".do:Blabla"));
    }

    @Test
    public void isSmsCommand_notSmsCommandPassed_returnsFalse() {
        Assert.assertFalse(_commandParser.isSmsCommand("Blabla"));
    }

    @Test
    public void getCommand_commandWithoutParameters_returnsParsedCommand() {
        ParsedCommand parsedCommand = _commandParser.getCommand(".do:blabla");
        Assert.assertEquals("blabla", parsedCommand.getCommand());
        Assert.assertTrue(parsedCommand.getParametersMap().isEmpty());
    }

    @Test
    public void getCommand_commandWithParameter_returnsParsedCommand() {
        ParsedCommand parsedCommand = _commandParser.getCommand(".do:blabla?param=123,456");
        Assert.assertEquals("blabla", parsedCommand.getCommand());
        Assert.assertEquals(1, parsedCommand.getParametersMap().size());
        Assert.assertEquals("123,456", parsedCommand.getParametersMap().get("param"));
    }

    @Test
    public void getCommand_commandWithParameters_returnsParsedCommand() {
        ParsedCommand parsedCommand = _commandParser.getCommand(".do:blabla?param=123,456&param2=789");
        Assert.assertEquals("blabla", parsedCommand.getCommand());
        Assert.assertEquals(2, parsedCommand.getParametersMap().size());
        Assert.assertEquals("123,456", parsedCommand.getParametersMap().get("param"));
        Assert.assertEquals("789", parsedCommand.getParametersMap().get("param2"));
    }

    @Test
    public void getCommand_commandWithEmptyParameters_returnsParsedCommand() {
        ParsedCommand parsedCommand = _commandParser.getCommand(".do:blabla?");
        Assert.assertEquals("blabla", parsedCommand.getCommand());
        Assert.assertEquals(0, parsedCommand.getParametersMap().size());
    }

    @Test
    public void getCommand_commandWithEmptyParameterValue_returnsParsedCommand() {
        ParsedCommand parsedCommand = _commandParser.getCommand(".do:blabla?param");
        Assert.assertEquals("blabla", parsedCommand.getCommand());
        Assert.assertEquals(1, parsedCommand.getParametersMap().size());
        Assert.assertNull(parsedCommand.getParametersMap().get("param"));
    }

    @Test
    public void getCommand_commandWithEmptyParameterValueAndDivider_returnsParsedCommand() {
        ParsedCommand parsedCommand = _commandParser.getCommand(".do:blabla?param=");
        Assert.assertEquals("blabla", parsedCommand.getCommand());
        Assert.assertEquals(1, parsedCommand.getParametersMap().size());
        Assert.assertEquals("", parsedCommand.getParametersMap().get("param"));
    }

}
