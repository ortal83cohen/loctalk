package com.travoca.app;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;
import com.travoca.app.events.EventProducers;
import com.travoca.app.events.Events;
import com.travoca.app.utils.AppLog;

import android.app.Application;
import android.content.Context;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.SilentLogger;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class TravocaApplication extends Application implements App.Provider {

    private ObjectGraph mObjectGraph;

    public static TravocaApplication get(Context context) {
        return (TravocaApplication) context.getApplicationContext();
    }

    public static ObjectGraph provide(Context context) {
        return get(context).getObjectGraph();
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        mRefWatcher = LeakCanary.install(this);
        Fabric.with((new Fabric.Builder(this)).kits(new Crashlytics()).logger(new SilentLogger())
                .build());
        mObjectGraph = new ObjectGraph(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("OpenSans_Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        Events.register(new EventProducers(this));

        if (BuildConfig.DEBUG) {
//            Picasso.with(this).setIndicatorsEnabled(true);
            Picasso.with(this).setLoggingEnabled(true);
        }

        AppLog.d("Device class: " + mObjectGraph.getDeviceClass());

    }

    @Override
    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }


}
