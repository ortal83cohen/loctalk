package com.travoca.app.travocaapi;

import com.travoca.api.utils.HttpLoggingInterceptor;
import com.travoca.app.BuildConfig;
import com.travoca.app.utils.AppLog;

/**
 * @author ortal
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
