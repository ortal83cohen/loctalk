package com.travoca.app.events;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.travoca.app.utils.AppLog;

/**
 * @author ortal
 * @date 2015-07-06
 */
public class Events {
    private static final Bus sBus = new Bus();
    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    public static void post(Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            sBus.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                   // android.os.Debug.waitForDebugger();
                }
            });
        }
        AppLog.v("Posting event: " + event);
        sBus.post(event);
    }

    public static void register(Object object) {
        AppLog.v("Registering: " + object);
        try {
            sBus.register(object);
        } catch (IllegalArgumentException ignored) {
        }
    }

    public static void unregister(Object object) {
        AppLog.v("Unregistering: " + object);
        sBus.unregister(object);
    }
}
