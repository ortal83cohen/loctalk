package com.stg.app.events;

import android.content.Context;

import com.stg.app.App;
import com.squareup.otto.Produce;

/**
 * @author alex
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
