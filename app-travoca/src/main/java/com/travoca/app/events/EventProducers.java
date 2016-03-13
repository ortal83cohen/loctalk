package com.travoca.app.events;

import com.squareup.otto.Produce;
import com.travoca.app.App;

import android.content.Context;

/**
 * @author ortal
 * @date 2015-07-12
 */
public class EventProducers {


    private final Context mContext;

    public EventProducers(Context application) {
        mContext = application;
    }

    @Produce
    public NetworkStateChangeEvent produceNetworkStateChangeEvent() {
        return new NetworkStateChangeEvent(App.provide(mContext).netUtils().isOnline());
    }
}
