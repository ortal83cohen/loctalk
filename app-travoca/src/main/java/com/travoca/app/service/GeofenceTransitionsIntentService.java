package com.travoca.app.service;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import com.travoca.app.R;
import com.travoca.app.provider.DbContract;
import com.travoca.app.utils.Notification;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GeofenceTransitionsIntentService extends IntentService {

    protected static final String TAG = "GeofenceTransitionsIS";

    public GeofenceTransitionsIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {

            String errorMessage = getErrorString(
                    geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }
        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            // Get the transition details as a String.
            Geofence geofence = triggeringGeofences.get(0);
//        android.os.Debug.waitForDebugger();
            if (geofence.getRequestId() == BootUpReceiver.EXIT_REQUEST_AREA) {
                new LocalRecordsRequest(this).makeRequests(geofencingEvent.getTriggeringLocation());
                new Notification(this).sendNotification(
                        "EXIT !!!!!!!" + geofencingEvent.getTriggeringLocation());
            } else {
                String geofenceTransitionDetails = getGeofenceTransitionDetails(
                        this,
                        geofenceTransition,
                        triggeringGeofences
                );
                // Send notification and log the transition details.
                new Notification(this).sendNotification(geofenceTransitionDetails);
                Log.i(TAG, geofenceTransitionDetails);
            }
        } else {
            // Log the error.
            Log.e(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition));
        }
    }

    private String getErrorString(int errorCode) {
        Resources resources = getResources();
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return resources.getString(R.string.geofence_not_available);
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return resources.getString(R.string.geofence_too_many_geofences);
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return resources.getString(R.string.geofence_too_many_pending_intents);
            default:
                return resources.getString(R.string.unknown_geofence_error);
        }
    }

    private String getGeofenceTransitionDetails(
            Context context,
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList triggeringGeofencesIdsList = new ArrayList();

        Geofence geofence = triggeringGeofences.get(0);
//        android.os.Debug.waitForDebugger();
        Cursor cursor = getContentResolver()
                .query(DbContract.ServiceGpsResults.CONTENT_URI.buildUpon().build(), null,
                        DbContract.ServiceGpsResultsColumns.KEY_ID + "=" + geofence.getRequestId(),
                        null,
                        null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                triggeringGeofencesIdsList.add(cursor.getString(
                        cursor.getColumnIndex(DbContract.ServiceGpsResultsColumns.LOCATION_NAME)));
            }
            cursor.close();

        }
        ContentValues values = new ContentValues();
        values.put(DbContract.ServiceGpsResultsColumns.USED, "1");
        getContentResolver()
                .update(DbContract.ServiceGpsResults.CONTENT_URI.buildUpon().build(), values,
                        DbContract.ServiceGpsResultsColumns.KEY_ID + "=" + geofence.getRequestId(),
                        null);

        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }

    /**
     * Maps geofence transition types to their human-readable equivalents.
     *
     * @param transitionType A transition type constant defined in return                  A String
     *                       indicating the type of transition
     */
    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);
            default:
                return getString(R.string.unknown_geofence_transition);
        }
    }
}
