package com.travoca.app.analytics;

import android.app.Activity;
import android.content.Context;

import com.travoca.api.model.HotelRequest;
import com.travoca.api.model.Record;
import com.travoca.api.model.SearchRequest;

/**
 * @author ortal
 * @date 2016-01-04
 */
public class AnalyticsCalls {

    private static GoogleAnalyticsCalls googleAnalyticsCalls = null;

    private static AnalyticsCalls mInstance = null;

    public static AnalyticsCalls get() {
        if (mInstance == null) {
            mInstance = new AnalyticsCalls();
        }
        return mInstance;
    }


    public void register(Context applicationContext) {

//        googleAnalyticsCalls = new GoogleAnalyticsCalls();
//
//        googleAnalyticsCalls.register(applicationContext);
    }


    public void trackRetrofitFailure(String url, String response, String body) {
//        googleAnalyticsCalls.trackRetrofitFailure(url, response, body);
    }

    public void trackSearchResults(SearchRequest request) {
//        omniture.trackSearchResults(request);
//        googleAnalyticsCalls.trackSearchResults(request);
    }

    public void trackBookingFormPersonal() {
//        omniture.trackBookingFormPersonal();
//        googleAnalyticsCalls.trackBookingFormPersonal();
    }

    public void trackLanding() {
//        omniture.trackLanding();
//        googleAnalyticsCalls.trackLanding();
    }


    public void trackBookingFormPayment() {
//        omniture.trackBookingFormPayment();
//        googleAnalyticsCalls.trackBookingFormPayment();
    }


    public void trackHotelDetails(HotelRequest request, Record record, String currencyCode) {
//        omniture.trackHotelDetails(request, hotelSnippet, rate, currencyCode);
//        googleAnalyticsCalls.trackHotelDetails(request, hotelSnippet, rate, currencyCode);
    }


    public void pauseCollectingLifecycleData() {
//        omniture.pauseCollectingLifecycleData();
//        googleAnalyticsCalls.pauseCollectingLifecycleData();
    }

    public void collectLifecycleData(Activity activity) {
//        if (activity != null && omniture != null) {
//            omniture.collectLifecycleData(activity);
//        }
//        googleAnalyticsCalls.collectLifecycleData(activity);
    }


}
