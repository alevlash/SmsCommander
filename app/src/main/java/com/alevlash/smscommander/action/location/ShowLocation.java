package com.alevlash.smscommander.action.location;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.alevlash.smscommander.action.Action;
import com.alevlash.smscommander.action.ActionRequest;
import com.alevlash.smscommander.commandparser.CommandConstants;

public class ShowLocation implements Action {

    @Override
    public void execute(ActionRequest actionRequest) {

        // Create a Uri from an intent string. Use the result to create an Intent.
        String locationString = actionRequest.getParametersMap().get(CommandConstants.LOCATION_PARAM);
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + locationString);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(actionRequest.getContext().getPackageManager()) != null) {
            actionRequest.getContext().startActivity(mapIntent);
        } else {
            Log.i("SmsCommander", "Could not find Google Maps");
        }

    }

}
