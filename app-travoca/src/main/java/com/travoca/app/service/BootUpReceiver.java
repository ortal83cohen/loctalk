package com.travoca.app.service;

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

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;


public class BootUpReceiver extends BroadcastReceiver
        implements GoogleApiClient.ConnectionCallbacks, ResultCallback<Status>,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String EXIT_REQUEST_AREA = "exit_request_area";

    private static final float GEOFENCE_RADIUS_IN_METERS = 50;

    private static final String TAG = "BootUpReceiver";

    private static final int JOB_ID = 1;

    protected GoogleApiClient mGoogleApiClient;

    Context mContext;

    private GeofencingRequest.Builder builder;

    private Location mLastLocation;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "BootUpReceiver start");

        mContext = context;

        mGoogleApiClient = new GoogleApiClient.Builder(context, this, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        JobScheduler jobScheduler = JobScheduler.getInstance(mContext);

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,
                new ComponentName(mContext, LocalRecordsJobService.class));
        builder
                .setOverrideDeadline(3600000 * 24)// max in hours
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//                .setPeriodic(100)
                .setPersisted(true);

        jobScheduler.schedule(builder.build());

        initGeofenceIntent();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended");
    }

    void initGeofenceIntent() {
        Intent geofenceIntent = new Intent(mContext, GeofenceTransitionsIntentService.class);
        PendingIntent pendingIntent = PendingIntent
                .getService(mContext, 0, geofenceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(mContext, mContext.getString(R.string.not_connected), Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        try {
            initGeofencingRequest();
            if (builder != null) {
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
            Cursor cursor = mContext.getContentResolver()
                    .query(DbContract.ServiceGpsResults.CONTENT_URI.buildUpon().
                                    build(), null,
                            DbContract.ServiceGpsResultsColumns.USED + " = '0'",
                            null,
                            null);
            if (cursor.getCount() == 0) {
                return;
            }
            builder = new GeofencingRequest.Builder();
            builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
            List<Geofence> geofenceList = new ArrayList<>();
            if (cursor != null) {
                geofenceList.add(new Geofence.Builder()
                        .setRequestId(EXIT_REQUEST_AREA)
                        .setCircularRegion(
                                mLastLocation.getLatitude(), mLastLocation.getLongitude(),
                                1000)
                        .setExpirationDuration(100 * 60 * 60 * 100) //100 hours
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT)
                        .build());
                while (cursor.moveToNext()) {
                    geofenceList.add(new Geofence.Builder()
                            .setRequestId(cursor.getString(
                                    cursor.getColumnIndex(
                                            DbContract.ServiceGpsResultsColumns.KEY_ID)))
                            .setCircularRegion(
                                    Double.valueOf(cursor.getString(cursor.getColumnIndex(
                                            DbContract.ServiceGpsResultsColumns.LAT))),
                                    Double.valueOf(cursor.getString(cursor.getColumnIndex(
                                            DbContract.ServiceGpsResultsColumns.LON))),
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