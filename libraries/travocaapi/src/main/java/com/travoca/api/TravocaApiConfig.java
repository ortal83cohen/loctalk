package com.travoca.api;

import com.travoca.api.utils.HttpLoggingInterceptor;

/**
 * @author ortal
 * @date 2015-04-28
 */
public class TravocaApiConfig {

    public static final String TRAVOCA_API_ENDPOINT_DEFAULT = "http://ec2-52-49-195-51.eu-west-1.compute.amazonaws.com/";

    private String mApiKey;
    private String mEndpoint = TRAVOCA_API_ENDPOINT_DEFAULT;
    private boolean mDebug;
    private HttpLoggingInterceptor.Logger logger;

    public TravocaApiConfig(String apiKey) {
        this.mApiKey = apiKey;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public String getEndpoint() {
        return mEndpoint;
    }

    public void setEndpoint(String endpoint) {
        mEndpoint = endpoint;
    }

    public boolean isDebug() {
        return mDebug;
    }

    public void setDebug(boolean debug) {
        mDebug = debug;
    }

    public HttpLoggingInterceptor.Logger getLogger() {
        return logger;
    }

    public void setLogger(HttpLoggingInterceptor.Logger logger) {
        this.logger = logger;
    }
}
