package com.travoca.app.service;

import android.Manifest;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.squareup.okhttp.ResponseBody;
import com.travoca.api.TravocaApi;
import com.travoca.api.model.Record;
import com.travoca.api.model.ResultsResponse;
import com.travoca.api.model.SearchRequest;
import com.travoca.app.App;
import com.travoca.app.TravocaApplication;
import com.travoca.app.events.Events;
import com.travoca.app.events.SearchResultsEvent;
import com.travoca.app.member.MemberStorage;
import com.travoca.app.member.model.User;
import com.travoca.app.model.ServiceGpsLocation;
import com.travoca.app.provider.DbContract;
import com.travoca.app.travocaapi.RetrofitCallback;

import retrofit.Response;

public class LocationService extends Service {
    private static final String TAG = "BOOMBOOMTESTGPS";
    private static final int NOTIFICATION_ID = 1;
    private static final double MINIMUM_DISTANCE = 0.05;
    private static final int LOCATION_INTERVAL = 3000000;
    private static final float LOCATION_DISTANCE = 100000f;
    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER, this),
            new LocationListener(LocationManager.NETWORK_PROVIDER, this)
    };
    private LocationManager mLocationManager = null;
    private Context context;
    private MemberStorage mMemberStorage;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        context = this;
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
        mMemberStorage = App.provide(context).memberStorage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private class LocationListener implements android.location.LocationListener {
        Context context;
        private Location mLastLocation;
        private RetrofitCallback<ResultsResponse> mResultsCallback = new RetrofitCallback<ResultsResponse>() {
            @Override
            protected void failure(ResponseBody response, boolean isOffline) {
                Events.post(new SearchResultsEvent(true, 0));
                Log.e(TAG, "failure: ");
            }

            @Override
            protected void success(ResultsResponse apiResponse, Response<ResultsResponse> response) {
                String lastUpdate = "";
                for (Record record : apiResponse.records) {
                    if (lastUpdate.compareTo(record.date) < 0) {
                        lastUpdate = record.date;
                    }
                    ContentValues values = new ContentValues();
                    values.put(DbContract.ServiceGpsColumns.KEY_ID, record.id);
                    values.put(DbContract.ServiceGpsColumns.LOCATION_NAME, record.locationName);
                    values.put(DbContract.ServiceGpsColumns.LON, record.lon);
                    values.put(DbContract.ServiceGpsColumns.LAT, record.lat);
                    values.put(DbContract.ServiceGpsColumns.CREATE_AT, record.date);
                    context.getContentResolver().insert(DbContract.ServiceGps.CONTENT_URI, values);

                }

                Log.e(TAG, "success: " + apiResponse.records.size());
            }

        };

        public LocationListener(String provider, Context context) {
            this.context = context;
            mLastLocation = new Location(provider);
        }

        public Location getLastLocation() {
            return mLastLocation;
        }

        @Override
        public void onLocationChanged(Location location) {

            Log.e(TAG, "onLocationChanged: " + location);
            final TravocaApi travocaApi = TravocaApplication.provide(context).travocaApi();
            SearchRequest searchRequest = new SearchRequest();

//            android.os.Debug.waitForDebugger();
            Cursor cursor = getContentResolver().query(DbContract.ServiceGps.CONTENT_URI.buildUpon().appendQueryParameter("limit", "2").build(), null, null, null, null);
            String lastUpdate = "";
            if (cursor.getCount() != 0) { // // TODO: 3/12/2016 make sure it works
                cursor.moveToFirst();
                lastUpdate = cursor.getString(cursor.getColumnIndex(DbContract.ServiceGpsColumns.CREATE_AT));
            }

            searchRequest.setType(new ServiceGpsLocation("gps", location.getLatitude(), location.getLongitude(), lastUpdate));
            searchRequest.setLimit(50);

            User user = mMemberStorage.loadUser();
            String userId;
            if (user == null) {
                userId = "";
            } else {
                userId = user.id;
            }
            searchRequest.setUserId(userId);

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
}