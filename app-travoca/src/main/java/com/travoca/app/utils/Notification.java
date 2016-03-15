package com.travoca.app.utils;

import com.travoca.app.R;
import com.travoca.app.activity.MainActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by ortal on 3/15/2016.
 */
public class Notification {
Context mContext;

    public Notification(Context context) {
        mContext = context;
    }

    public void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);
        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);
        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setSmallIcon(R.drawable.loadersmall07)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),
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
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }

}
