package com.stg.app.events;

import com.stg.app.hoteldetails.HotelSnippet;

/**
 * @author alex
 * @date 2015-11-19
 */
public class HotelDetailsResultsEvent {
    private final HotelSnippet mHotelDetails;
    private final boolean mError;

    public HotelDetailsResultsEvent(HotelSnippet hotelSnippetDetails) {
        mHotelDetails = hotelSnippetDetails;
        mError = false;
    }

    public HotelDetailsResultsEvent(boolean error) {
        mError = true;
        mHotelDetails = null;
    }


    public HotelSnippet getHotelDetails() {
        return mHotelDetails;
    }

    public boolean isError() {
        return mError;
    }
}
