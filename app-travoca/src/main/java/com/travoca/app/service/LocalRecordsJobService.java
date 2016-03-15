package com.travoca.app.service;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import com.travoca.app.utils.Notification;

import android.location.Location;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

public class LocalRecordsJobService extends JobService
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "LocalRecordsJobService";

    private GoogleApiClient mGoogleApiClient;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Time time = new Time();   time.setToNow();
        new Notification(this).sendNotification(
               "job Service "+time.hour);
        mGoogleApiClient = new GoogleApiClient.Builder(this, this, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .build();
        mGoogleApiClient.connect();

        jobFinished(jobParameters, false);
        return false;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        Log.i(TAG, "onConnected");
        new LocalRecordsRequest(this).makeRequests(lastLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "onConnectionSuspended");
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.e(TAG, "onStopJob"); return false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");
    }
}
