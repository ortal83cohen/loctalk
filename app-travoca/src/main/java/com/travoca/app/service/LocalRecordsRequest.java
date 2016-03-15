package com.travoca.app.service;

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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.util.Log;

import retrofit.Response;

/**
 * Created by ortal on 3/15/2016.
 */
public class LocalRecordsRequest {

    private static final String TAG = "LocalRecordsRequest";

    private static final int REQUEST_RADIUS = 1000;

    Context mContext;

    private RetrofitCallback<ResultsResponse> mResultsCallback
            = new RetrofitCallback<ResultsResponse>() {
        @Override
        protected void failure(ResponseBody response, boolean isOffline) {
            Events.post(new SearchResultsEvent(true, 0));
            Log.e(TAG, "failure: ");
        }

        @Override
        protected void success(ResultsResponse apiResponse,
                Response<ResultsResponse> response) {
            String lastUpdate = "";
//                android.os.Debug.waitForDebugger();
            for (Record record : apiResponse.records) {
                if (lastUpdate.compareTo(record.date) < 0) {
                    lastUpdate = record.date;
                }
                ContentValues values = new ContentValues();
                values.put(DbContract.ServiceGpsResultsColumns.KEY_ID, record.id);
                values.put(DbContract.ServiceGpsResultsColumns.LOCATION_NAME, record.title);
                values.put(DbContract.ServiceGpsResultsColumns.LON, record.lon);
                values.put(DbContract.ServiceGpsResultsColumns.LAT, record.lat);
                values.put(DbContract.ServiceGpsResultsColumns.CREATE_AT, record.date);
                mContext.getContentResolver()
                        .insert(DbContract.ServiceGpsResults.CONTENT_URI, values);

            }

            Log.e(TAG, "success: " + apiResponse.records.size());
        }

    };

    public LocalRecordsRequest(Context context) {
        mContext = context;
    }

    void makeRequests(Location lastLocation) {

        Log.e(TAG, "onLocationChanged: " + lastLocation.getLongitude() + "," + lastLocation
                .getLatitude());
        final TravocaApi travocaApi = TravocaApplication.provide(mContext).travocaApi();
        MemberStorage memberStorage = App.provide(mContext).memberStorage();
        SearchRequest searchRequest = new SearchRequest();

        android.os.Debug.waitForDebugger();
        Cursor cursor = mContext.getContentResolver()
                .query(DbContract.ServiceGpsRequests.CONTENT_URI.buildUpon()
                        .appendQueryParameter("limit", "1").build(), null, null, null, null);
        String lastUpdate = "";
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            lastUpdate = cursor
                    .getString(
                            cursor.getColumnIndex(DbContract.ServiceGpsResultsColumns.CREATE_AT));
        }

        searchRequest.setType(
                new ServiceGpsLocation("gps", lastLocation.getLatitude(),
                        lastLocation.getLongitude(),
                        lastUpdate));
        searchRequest.setLimit(50);

        User user = memberStorage.loadUser();
        String userId;
        if (user == null) {
            userId = "";
        } else {
            userId = user.id;
        }
        searchRequest.setUserId(userId);

        travocaApi.records(searchRequest).enqueue(mResultsCallback);

        ContentValues values = new ContentValues();
        values.put(DbContract.ServiceGpsRequestsColumns.RADIUS, REQUEST_RADIUS);
        values.put(DbContract.ServiceGpsRequestsColumns.LAT, lastLocation.getLatitude());
        values.put(DbContract.ServiceGpsRequestsColumns.LON, lastLocation.getLongitude());
        mContext.getContentResolver().insert(DbContract.ServiceGpsRequests.CONTENT_URI,
                values);

    }

}
