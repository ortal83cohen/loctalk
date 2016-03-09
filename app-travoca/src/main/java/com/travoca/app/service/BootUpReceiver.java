package com.travoca.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        /***** For start Service  ****/
        Intent myIntent = new Intent(context, LocationService.class);
        context.startService(myIntent);
    }

}