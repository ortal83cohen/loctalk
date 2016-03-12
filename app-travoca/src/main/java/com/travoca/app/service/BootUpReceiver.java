package com.travoca.app.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.travoca.app.R;
import com.travoca.app.provider.DbContract;

import java.util.ArrayList;
import java.util.List;


public class BootUpReceiver extends BroadcastReceiver implements GoogleApiClient.ConnectionCallbacks, ResultCallback<Status>, GoogleApiClient.OnConnectionFailedListener {
    private static final float GEOFENCE_RADIUS_IN_METERS = 50;
    private static final String TAG = "BootUpReceiver";
    protected GoogleApiClient mGoogleApiClient;
    Context mContext;
    private GeofencingRequest.Builder builder;

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
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(mContext, mContext.getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            initGeofencingRequest();
            if(builder!=null) {
                LocationServices.GeofencingApi.addGeofences(
                        mGoogleApiClient,
                        builder.build(),
                        pendingIntent
                ).setResultCallback(this);
            }
        } catch (SecurityException securityException) {
            Log.e("geofence", securityException.getMessage());
        }
    }

    private void initGeofencingRequest() {
        if (builder == null) {
            Cursor cursor = mContext.getContentResolver().query(DbContract.ServiceGps.CONTENT_URI.buildUpon().
                    build(), null, null, null, null);
            if(cursor.getCount()==0){
                return;
            }
            builder = new GeofencingRequest.Builder();
            builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
            List<Geofence> geofenceList = new ArrayList<>();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    geofenceList.add(new Geofence.Builder()
                            .setRequestId(cursor.getString(cursor.getColumnIndex(DbContract.ServiceGpsColumns.KEY_ID)))
                            .setCircularRegion(
                                    Double.valueOf(cursor.getString(cursor.getColumnIndex(DbContract.ServiceGpsColumns.LAT))),
                                    Double.valueOf(cursor.getString(cursor.getColumnIndex(DbContract.ServiceGpsColumns.LON))),
                                    GEOFENCE_RADIUS_IN_METERS
                            )
                            .setExpirationDuration(24 * 60 * 60 * 100) //24 hours
                            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
//                            | Geofence.GEOFENCE_TRANSITION_EXIT
                            )
                            .build());
                }
                cursor.close();
            }
            builder.addGeofences(geofenceList);
        }
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