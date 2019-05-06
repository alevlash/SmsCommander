package com.alevlash.smscommander.action.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.alevlash.smscommander.action.Action;
import com.alevlash.smscommander.action.ActionRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocateAction implements Action {

    @Override
    public void execute(final ActionRequest actionRequest) {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(actionRequest.getContext());

        if (ActivityCompat.checkSelfPermission(actionRequest.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            actionRequest.getConnectionService().sendResponse(location.toString());
                            Log.i("SmsCommander", "Location returned: " + location.toString());
                        } else {
                            Log.e("SmsCommander", "No location returned");
                            actionRequest.getConnectionService().sendResponse("No location returned");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e("SmsCommander", "Exception on getLastLocation: " + e);
                        actionRequest.getConnectionService().sendResponse(e.getMessage());
                    }
                });
    }

}
