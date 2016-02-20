package com.travoca.api;

import com.travoca.api.utils.HttpLoggingInterceptor;

/**
 * @author ortal
 * @date 2015-04-28
 */
public class StgApiConfig {

    public static final String ETB_API_ENDPOINT_DEFAULT = "http://ortal83cohen.com/";

    private String mApiKey;
    private String mEndpoint = ETB_API_ENDPOINT_DEFAULT;




    private boolean mDebug;
    private int mCampaignId;
    private HttpLoggingInterceptor.Logger logger;

    public StgApiConfig(String apiKey, int campaignId) {
        this.mApiKey = apiKey;
        this.mCampaignId = campaignId;
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

    public int getCampaignId() {
        return mCampaignId;
    }

    public HttpLoggingInterceptor.Logger getLogger() {
        return logger;
    }

    public void setLogger(HttpLoggingInterceptor.Logger logger) {
        this.logger = logger;
    }
}
