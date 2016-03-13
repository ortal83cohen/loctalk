package com.travoca.app.service;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import com.travoca.app.R;
import com.travoca.app.activity.MainActivity;
import com.travoca.app.provider.DbContract;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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
            String geofenceTransitionDetails = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );
            // Send notification and log the transition details.
            sendNotification(geofenceTransitionDetails);
            Log.i(TAG, geofenceTransitionDetails);
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
                .query(DbContract.ServiceGps.CONTENT_URI.buildUpon().build(), null,
                        DbContract.ServiceGpsColumns.KEY_ID + "=" + geofence.getRequestId(), null,
                        null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                triggeringGeofencesIdsList.add(cursor.getString(
                        cursor.getColumnIndex(DbContract.ServiceGpsColumns.LOCATION_NAME)));
            }
            cursor.close();

        }
        ContentValues values = new ContentValues();
        values.put(DbContract.ServiceGpsColumns.USED, "1");
        getContentResolver().update(DbContract.ServiceGps.CONTENT_URI.buildUpon().build(), values,
                DbContract.ServiceGpsColumns.KEY_ID + "=" + geofence.getRequestId(), null);

        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }

    private void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);
        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);
        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.loadersmall07)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.loadersmall07))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText("you have record in your current location")
                .setContentIntent(notificationPendingIntent);

        //                    Intent intent = new Intent(context, RecordDetailsActivity.class);
//                    intent.putExtra(RecordDetailsActivity.EXTRA_DATA, record);
//                    intent.putExtra(RecordDetailsActivity.EXTRA_REQUEST, new RecordListRequest());
//                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//                    addNotification(context, "record name " + record.title, record.description + "/" +
//                            record.locationName, pendingIntent);
//    private void addNotification(Context context, String title, String body, PendingIntent pendingIntent) {
//        Toast.makeText(context, title, Toast.LENGTH_LONG);
//
//        Notification.Builder notificationBuilder = new Notification.Builder(context)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setSmallIcon(R.drawable.loadersmall01);
//
//        // create the pending intent and add to the notification
//        notificationBuilder.setContentIntent(pendingIntent);
//        Notification notification = notificationBuilder.build();
//        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
//        NotificationManager notificationManager = (NotificationManager)
//                getSystemService(NOTIFICATION_SERVICE);
//        // send the notification
//        notificationManager.notify(NOTIFICATION_ID, notification);
//    }

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
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
