package com.alevlash.smscommander.connection;

import android.telephony.SmsManager;

public class ConnectionServiceFactory {

    public ConnectionService getConnectionService(SmsManager smsManager, String destinationAddress) {
        return new SmsConnectionService(smsManager, destinationAddress);
    }

}
