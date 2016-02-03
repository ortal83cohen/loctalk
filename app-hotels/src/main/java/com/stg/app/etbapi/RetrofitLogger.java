package com.stg.app.etbapi;

import com.socialtravelguide.api.utils.HttpLoggingInterceptor;
import com.stg.app.BuildConfig;
import com.stg.app.utils.AppLog;

/**
 * @author alex
 * @date 2015-10-02
 */
public class RetrofitLogger implements HttpLoggingInterceptor.Logger {

    public static HttpLoggingInterceptor create() {
        HttpLoggingInterceptor.Level level = BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC;
        return new HttpLoggingInterceptor(new RetrofitLogger(), level);
    }

    @Override
    public void log(String message) {
        AppLog.d(message);
    }
}
