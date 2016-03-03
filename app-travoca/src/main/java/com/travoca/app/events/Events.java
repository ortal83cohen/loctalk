package com.travoca.app.events;

import com.squareup.otto.Bus;
import com.travoca.app.utils.AppLog;

/**
 * @author ortal
 * @date 2015-07-06
 */
public class Events {
    private static final Bus sBus = new Bus();

    public static void post(Object event) {
        AppLog.v("Posting event: " + event);
        sBus.post(event);
    }

    public static void register(Object object) {
        AppLog.v("Registering: " + object);
        try {
            sBus.register(object);
        }catch (IllegalArgumentException ignored){}
    }

    public static void unregister(Object object) {
        AppLog.v("Unregistering: " + object);
        sBus.unregister(object);
    }
}
