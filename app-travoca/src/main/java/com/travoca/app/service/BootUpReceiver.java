package com.travoca.app.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.travoca.app.provider.DbContract;

import java.util.ArrayList;
import java.util.List;


public class BootUpReceiver extends BroadcastReceiver implements GoogleApiClient.ConnectionCallbacks, ResultCallback<Status>, GoogleApiClient.OnConnectionFailedListener {
    private static final float GEOFENCE_RADIUS_IN_METERS = 100;
    private static final String TAG = "BootUpReceiver";
    protected GoogleApiClient mGoogleApiClient;
    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "BootUpReceiver start");

        mContext = context;

        Intent myIntent = new Intent(context, LocationService.class);
        context.startService(myIntent);

        mGoogleApiClient = new GoogleApiClient.Builder(context, this, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        initGeofenceIntent();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended");
    }

    void initGeofenceIntent() {
        Intent geofenceIntent = new Intent(mContext, GeofenceTransitionsIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, geofenceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        if (!mGoogleApiClient.isConnected()) {
//            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
//            return;
//        }

        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    // The GeofenceRequest object.
                    getGeofencingRequest(),
                    // A pending intent that that is reused when calling removeGeofences(). This
                    // pending intent is used to generate an intent when a matched geofence
                    // transition is observed.
                    pendingIntent
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            Log.e("geofence", securityException.getMessage());
        }
    }

    private GeofencingRequest getGeofencingRequest() {

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
        // is already inside that geofence.
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        List<Geofence> geofenceList = new ArrayList<>();
        // Add the geofences to be monitored by geofencing service.

        Cursor cursor = mContext.getContentResolver().query(DbContract.ServiceGps.CONTENT_URI.buildUpon().
                build(), null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                geofenceList.add(new Geofence.Builder()
                        // Set the request ID of the geofence. This is a string to identify this
                        // geofence.
                        .setRequestId(cursor.getString(cursor.getColumnIndex(DbContract.ServiceGpsColumns.KEY_ID)))

                                // Set the circular region of this geofence.
                        .setCircularRegion(
                                Double.valueOf(cursor.getString(cursor.getColumnIndex(DbContract.ServiceGpsColumns.LAT))),
                                Double.valueOf(cursor.getString(cursor.getColumnIndex(DbContract.ServiceGpsColumns.LON))),
                                GEOFENCE_RADIUS_IN_METERS
                        )

                                // Set the expiration duration of the geofence. This geofence gets automatically
                                // removed after this period of time.
                        .setExpirationDuration(24 * 60 * 60 * 100)

                                // Set the transition types of interest. Alerts are only generated for these
                                // transition. We track entry and exit transitions in this sample.
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
//                            | Geofence.GEOFENCE_TRANSITION_EXIT
                        )

                                // Create the geofence.
                        .build());
            }

            cursor.close();
        }

        builder.addGeofences(geofenceList);

        // Return a GeofencingRequest.
        return builder.build();
    }

    @Override
    public void onResult(Status status) {
        Log.i(TAG, "onResult");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");
    }
}