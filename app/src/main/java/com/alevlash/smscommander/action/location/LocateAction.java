package com.alevlash.smscommander.action.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.alevlash.smscommander.action.Action;
import com.alevlash.smscommander.action.ActionRequest;
import com.alevlash.smscommander.action.ActionType;
import com.alevlash.smscommander.commandparser.CommandConstants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocateAction implements Action {

    @Override
    public void execute(final ActionRequest actionRequest) {

        if (ActivityCompat.checkSelfPermission(actionRequest.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("SmsCommander", "No permission for " + Manifest.permission.ACCESS_COARSE_LOCATION);
            actionRequest.getConnectionService().sendResponse("No permission for " + Manifest.permission.ACCESS_COARSE_LOCATION);
            return;
        }

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(actionRequest.getContext());
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            actionRequest.getConnectionService().sendResponse(getResponseText(location));
                            Log.i("SmsCommander", "Last location returned: " + location.toString());
                        } else {
                            if (ActivityCompat.checkSelfPermission(actionRequest.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                Log.e("SmsCommander", "No permission for " + Manifest.permission.ACCESS_FINE_LOCATION);
                                actionRequest.getConnectionService().sendResponse("No permission for " + Manifest.permission.ACCESS_FINE_LOCATION);
                                return;
                            }
                            LocationManager locationManager = (LocationManager) actionRequest.getContext().getSystemService(Context.LOCATION_SERVICE);
                            Log.e("SmsCommander", "No location returned, trying again");
                            actionRequest.getConnectionService().sendResponse("No location returned, trying again");
                            assert locationManager != null;
                            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                                        @Override
                                        public void onLocationChanged(Location location) {
                                            actionRequest.getConnectionService().sendResponse(getResponseText(location));
                                            Log.i("SmsCommander", "GPS location returned: " + location.toString());
                                        }

                                        @Override
                                        public void onStatusChanged(String provider, int status, Bundle extras) {
                                        }

                                        @Override
                                        public void onProviderEnabled(String provider) {
                                        }

                                        @Override
                                        public void onProviderDisabled(String provider) {
                                        }
                                    },
                                    Looper.getMainLooper());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("SmsCommander", "Exception on getLastLocation: " + e);
                        actionRequest.getConnectionService().sendResponse(e.getMessage());
                    }
                });
    }

    String getResponseText(Location location) {
        return String.format(CommandConstants.SMS_COMMAND +
                        ActionType.SHOWLOCATION.name().toLowerCase() +
                        CommandConstants.PARAMS_START +
                        CommandConstants.LOCATION_PARAM +
                        CommandConstants.PARAM_VALUE_DIVIDER +
                        "%.6f,%.6f",
                location.getLatitude(), location.getLongitude());
    }

}
