package com.alevlash.smscommander.action.location;

import android.location.Location;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocateActionTest {

    private LocateAction _action = new LocateAction();

    @Test
    public void getResponseText_givenLocation_returnsRespnseText() {
        Location location = Mockito.spy(new Location(""));
        Mockito.doReturn(123.4).when(location).getLatitude();
        Mockito.doReturn(567.8).when(location).getLongitude();

        String responseText = _action.getResponseText(location);

        Assert.assertEquals(".do:showlocation?location=123.400000,567.800000", responseText);
    }

}
