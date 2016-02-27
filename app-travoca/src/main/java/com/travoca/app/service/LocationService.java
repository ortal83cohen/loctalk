package com.travoca.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;
import com.travoca.api.TravocaApi;
import com.travoca.api.model.Record;
import com.travoca.api.model.ResultsResponse;
import com.travoca.api.model.SearchRequest;
import com.travoca.app.R;
import com.travoca.app.TravocaApplication;
import com.travoca.app.activity.HomeActivity;
import com.travoca.app.activity.RecordDetailsActivity;
import com.travoca.app.events.Events;
import com.travoca.app.events.SearchResultsEvent;
import com.travoca.app.model.RecordListRequest;
import com.travoca.app.model.ViewPort;
import com.travoca.app.travocaapi.RetrofitCallback;

import retrofit.Response;

public class LocationService extends Service {
    private static final String TAG = "BOOMBOOMTESTGPS";
    private static final int NOTIFICATION_ID = 1;
    private static final double MINIMUM_DISTANCE = 0.05;
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 10000;
    private static final float LOCATION_DISTANCE = 1000f;
    private Context context;

    private class LocationListener implements android.location.LocationListener {
        private Location mLastLocation;
        Context context;

        public Location getLastLocation() {
            return mLastLocation;
        }

        public LocationListener(String provider, Context context) {
            this.context = context;
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        private RetrofitCallback<ResultsResponse> mResultsCallback = new RetrofitCallback<ResultsResponse>() {
            @Override
            protected void failure(ResponseBody response, boolean isOffline) {
                Events.post(new SearchResultsEvent(true, 0));
                Log.e(TAG, "failure: ");
            }

            @Override
            protected void success(ResultsResponse apiResponse, Response<ResultsResponse> response) {
                if (apiResponse.records.size() == 1) {
                    Record record = apiResponse.records.get(0);
                    Intent intent = new Intent(context, RecordDetailsActivity.class);
                    intent.putExtra(RecordDetailsActivity.EXTRA_DATA, record);
                    intent.putExtra(RecordDetailsActivity.EXTRA_REQUEST, new RecordListRequest());
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                    addNotification(context, "record name " + record.title, record.description + "/" +
                            record.locationName,pendingIntent);
                }
                Log.e(TAG, "success: " + apiResponse.records.size());
            }

        };

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            final TravocaApi travocaApi = TravocaApplication.provide(context).travocaApi();
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setType(new ViewPort("service", location.getLatitude() + MINIMUM_DISTANCE,
                    location.getLongitude() + MINIMUM_DISTANCE, location.getLatitude() - MINIMUM_DISTANCE,
                    location.getLongitude() - MINIMUM_DISTANCE));
            searchRequest.setLimit(1);
            travocaApi.records(searchRequest).enqueue(mResultsCallback);

            mLastLocation.set(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER, this),
            new LocationListener(LocationManager.NETWORK_PROVIDER, this)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        context = this;
//        Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();
        Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }

    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private void addNotification(Context context, String title, String body, PendingIntent pendingIntent) {
        Toast.makeText(context, title, Toast.LENGTH_LONG);

        Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.loadersmall01);

        // create the pending intent and add to the notification
        notificationBuilder.setContentIntent(pendingIntent);
        Notification notification = notificationBuilder.build();
        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // send the notification
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}