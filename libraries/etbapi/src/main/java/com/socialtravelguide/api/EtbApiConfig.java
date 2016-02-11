package com.socialtravelguide.api;

import com.socialtravelguide.api.utils.HttpLoggingInterceptor;

/**
 * @author alex
 * @date 2015-04-28
 */
public class EtbApiConfig {

    public static final String ETB_API_ENDPOINT_DEFAULT = "http://stormy-bastion-18585.herokuapp.com";

    private String mApiKey;
    private String mEndpoint = ETB_API_ENDPOINT_DEFAULT;




    private boolean mDebug;
    private int mCampaignId;
    private HttpLoggingInterceptor.Logger logger;

    public EtbApiConfig(String apiKey, int campaignId) {
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
