package com.alevlash.smscommander;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.alevlash.smscommander.action.Action;
import com.alevlash.smscommander.action.ActionRequest;
import com.alevlash.smscommander.action.ActionResolver;
import com.alevlash.smscommander.action.IllegalCommand;
import com.alevlash.smscommander.commandparser.CommandParser;
import com.alevlash.smscommander.commandparser.ParsedCommand;
import com.alevlash.smscommander.connection.ConnectionServiceFactory;

public class SmsCommander extends BroadcastReceiver {

    final SmsManager _smsManager;
    final ActionResolver _actionResolver;
    final CommandParser _commandParser;

    public SmsCommander() {
        this(SmsManager.getDefault());
    }

    SmsCommander(SmsManager smsManager) {
        _smsManager = smsManager;
        _actionResolver = new ActionResolver();
        _commandParser = new CommandParser();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                assert pdusObj != null;
                for (Object pdu : pdusObj) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();
                    String name = getContactDisplayNameByNumber(phoneNumber, context);
                    Log.i("SmsCommand", "senderPhoneNum: " + phoneNumber + ", message: " + message + ", contact name: " + name);

//					Toast.makeText(context, "SmsCommand senderPhoneNum: " + phoneNumber + ", message: " + message, Toast.LENGTH_LONG).show();

                    if (name != null && _commandParser.isSmsCommand(message)) {
                        //     Toast.makeText(context, "senderPhoneNum: " + phoneNumber + ", message: " + message, Toast.LENGTH_LONG).show();
                        handleCommand(context, phoneNumber, message);
                    }

                }
            }

        } catch (Exception e) {
            Log.e("SmsCommander", "Exception smsCommander" + e);

        }

    }

    private void handleCommand(Context context, String phoneNumber, String message) {
        try {
            ParsedCommand parsedCommand = _commandParser.getCommand(message);
            Action action = _actionResolver.getAction(parsedCommand.getCommand());
            ActionRequest actionRequest = ActionRequest.newBuilder()
                    .setContext(context)
                    .setPhoneNumber(phoneNumber)
                    .setConnectionService(new ConnectionServiceFactory().getConnectionService(_smsManager, phoneNumber))
                    .setParameterMap(parsedCommand.getParametersMap())
                    .build();

            action.execute(actionRequest);
        } catch (IllegalCommand e) {
            _smsManager.sendTextMessage(phoneNumber, null, e.getMessage(), null, null);
        }
    }

    String getContactDisplayNameByNumber(String number, Context context) {
        Uri uri = getUri(number);
        String name = null;

        ContentResolver contentResolver = context.getContentResolver();
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor contactLookup = contentResolver.query(uri, projection, null, null, null);

        if (contactLookup != null) {
            try {
                if (contactLookup.moveToNext()) {
                    name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                }
            } finally {
                contactLookup.close();
            }
        }

        return name;
    }

    Uri getUri(String number) {
        return Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
    }

}
