package com.alevlash.smscommander.connection;

import android.telephony.SmsManager;

public class SmsConnectionService implements ConnectionService {

    private final SmsManager _smsManager;
    private final String _destinationAddress;

    public SmsConnectionService(SmsManager smsManager, String destinationAddress) {
        _smsManager = smsManager;
        _destinationAddress = destinationAddress;
    }

    @Override
    public void sendResponse(String text) {
        _smsManager.sendTextMessage(_destinationAddress, null, "SmsCommander: " + text, null, null);
    }

}
