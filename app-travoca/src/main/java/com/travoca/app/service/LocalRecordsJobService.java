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

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

public class LocalRecordsJobService extends JobService
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private GoogleApiClient mGoogleApiClient;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Time time = new Time();   time.setToNow();
        new Notification(this).sendNotification(
               "job Service"+time.hour);
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

        new LocalRecordsRequest(this).makeRequests(lastLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
