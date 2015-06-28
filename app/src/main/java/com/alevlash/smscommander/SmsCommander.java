package com.alevlash.smscommander;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.alevlash.smscommander.action.Action;
import com.alevlash.smscommander.action.ActionRequest;
import com.alevlash.smscommander.action.ActionResolver;

public class SmsCommander extends BroadcastReceiver {


    final SmsManager sms = SmsManager.getDefault();
    final ActionResolver actionResolver = new ActionResolver();
    final CommandParser commandParser = new CommandParser();

    @Override
    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();
                    Log.i("SmsCommand", "senderPhoneNum: " + phoneNumber + "; message: " + message);

					Toast.makeText(context, "SmsCommand senderPhoneNum: " + phoneNumber + ", message: " + message, Toast.LENGTH_LONG).show();
                    if (commandParser.isSmsCommand(message)) {
                    //     Toast.makeText(context, "senderPhoneNum: " + phoneNumber + ", message: " + message, Toast.LENGTH_LONG).show();
                        handleCommand(context, phoneNumber, message);
                    }

                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }

    }

    private void handleCommand(Context context, String phoneNumber, String message) {
        try {
            Action action = actionResolver.getAction(commandParser.getCommand(message));

            ActionRequest actionRequest = ActionRequest.newBuilder()
                    .setContext(context)
                    .setPhoneNumber(phoneNumber)
                    .build();
            action.execute(actionRequest);
        } catch (IllegalArgumentException e) {
            sms.sendTextMessage(phoneNumber, null, e.getMessage(), null, null);
        }
    }

}
