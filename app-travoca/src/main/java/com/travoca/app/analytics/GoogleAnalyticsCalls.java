package com.travoca.app.analytics;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import com.travoca.api.model.Record;
import com.travoca.api.model.SearchRequest;
import com.travoca.app.R;
import com.travoca.app.model.CurrentLocation;
import com.travoca.app.model.Location;
import com.travoca.app.model.LocationWithTitle;

import android.app.Activity;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * @author ortal
 * @date 2016-01-04
 */
public class GoogleAnalyticsCalls extends AnalyticsCalls {

    private static final int KEY_LOCATION = 1;

    private static final int KEY_CURRENCY = 2;

    private static final int KEY_COUNTRY_CODE = 3;

    private static final int KEY_FROM = 4;

    private static final int KEY_TO = 5;

    private static final int KEY_HOTEL_NAME = 6;

    private static final int KEY_HOTEL_INDEX = 7;

    private static final int KEY_MIN_RATE_PRICE = 8;

    private static final int KEY_RATE_NAME = 9;

    private static final int KEY_URL = 10;

    private static final int KEY_RESPONSE = 11;

    private static final int KEY_BODY = 12;

    private Tracker mTracker;

    private SimpleDateFormat mDayFormatter = new SimpleDateFormat("dd-MM-yyyy",
            Locale.getDefault());

    synchronized public void register(Context applicationContext) {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(applicationContext);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics
                    .newTracker(applicationContext.getString(R.string.google_analytics_id));

        }
    }

    public void trackRetrofitFailure(String url, String response, String body) {
        body = body.equals("") ? "none" : body;
        mTracker.setScreenName("Retrofit Failure");
        mTracker.send(new HitBuilders.ScreenViewBuilder().setCustomDimension(KEY_RESPONSE, response)
                .setCustomDimension(KEY_URL, url).setCustomDimension(KEY_BODY, body).build());
    }

    public void trackSearchResults(SearchRequest request) {
        String location;
        if (request.getType() instanceof LocationWithTitle) {
            LocationWithTitle loc = (LocationWithTitle) (request).getType();
            location = loc.getTitle();
        } else if (request.getType() instanceof CurrentLocation) {
            CurrentLocation loc = (CurrentLocation) (request).getType();
            location = loc.getLatLng().toString();
        } else if (request.getType() instanceof Location) {
            Location loc = (Location) (request).getType();
            location = loc.getTitle();
        } else {
            location = request.getType().toString();
        }

        mTracker.setScreenName("Record List");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().setCustomDimension(KEY_CURRENCY, request.getCurrency()).
//                setCustomDimension(KEY_COUNTRY_CODE, request.getCustomerCountryCode()).setCustomDimension(KEY_LOCATION, location).
//                setCustomDimension(KEY_FROM, mDayFormatter.format(request.getDateRange().from.getTime())).setCustomDimension(KEY_TO, mDayFormatter.format(request.getDateRange().to.getTime())).build());
    }


    public void trackBookingFormPersonal() {
        mTracker.setScreenName("Personal Info");

        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void trackLanding() {
        mTracker.setScreenName("Home");

        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void trackFilterPage() {
        mTracker.setScreenName("Record List Filters");

        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void trackBookingFormPayment() {
        mTracker.setScreenName("Booking Form Payment");

        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    public void trackRecordDetails(SearchRequest request, Record record, String currencyCode) {
        mTracker.setScreenName("Record Details");
//        Product product = new Product()
//                .setId(String.valueOf(recordSnippet.getAccommodation().id))
//                .setName(recordSnippet.getAccommodation().name)
//                .setCategory(recordSnippet.getAccommodation().summary.country)
//                .setBrand(recordSnippet.getAccommodation().summary.city)
//                .setVariant(rate.name)
////                .setPosition(1)
//                ;
//        double price = 0;
//        if (rate != null) {
//            price = rate.displayPrice.get(currencyCode);
//        }
//
//        mTracker.send(new HitBuilders.ScreenViewBuilder().setCustomDimension(KEY_HOTEL_NAME, recordSnippet.getName()).
//                setCustomDimension(KEY_HOTEL_INDEX, String.valueOf(recordSnippet.getPosition())).setCustomDimension(KEY_MIN_RATE_PRICE, String.format("%.1f", price)).
//                setCustomDimension(KEY_CURRENCY, currencyCode).addImpression(product, "impression").build());
    }

    public void trackBookingFormAddress() {
        mTracker.setScreenName("Booking Form Address");

        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void pauseCollectingLifecycleData() {
        mTracker.setScreenName("pauseCollectingLifecycleData");

        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void collectLifecycleData(Activity activity) {
        mTracker.setScreenName("collectLifecycleData");

        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void trackBookingConfirmation() {
        mTracker.setScreenName("Booking Confirmation");

        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

//    public void event(String categoryId,String actionId,String labelId){
//        mTracker.send(new HitBuilders.EventBuilder()
//                .setCategory(categoryId)
//                .setAction(actionId)
//                .setLabel(labelId)
//                .build());
//    }
}

