package com.travoca.app.provider;

import com.google.android.gms.location.places.Place;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

/**
 * @author ortal
 * @date 2015-12-01
 */
public class SearchHistory {

    public static void insert(Place place, Context context) {
        ContentValues values = new ContentValues();
        String placeTypes = TextUtils.join(",", place.getPlaceTypes());
        values.put(DbContract.SearchHistoryColumns.LOCATION_NAME, place.getAddress().toString());
        values.put(DbContract.SearchHistoryColumns.LAT, place.getLatLng().latitude);
        values.put(DbContract.SearchHistoryColumns.LON, place.getLatLng().longitude);
        values.put(DbContract.SearchHistoryColumns.NORTHEAST_LAT,
                place.getViewport() == null ? null : place.getViewport().northeast.latitude);
        values.put(DbContract.SearchHistoryColumns.NORTHEAST_LON,
                place.getViewport() == null ? null : place.getViewport().northeast.longitude);
        values.put(DbContract.SearchHistoryColumns.SOUTHWEST_LAT,
                place.getViewport() == null ? null : place.getViewport().southwest.latitude);
        values.put(DbContract.SearchHistoryColumns.SOUTHWEST_LON,
                place.getViewport() == null ? null : place.getViewport().southwest.longitude);
        values.put(DbContract.SearchHistoryColumns.TYPES, placeTypes);

        context.getContentResolver().insert(DbContract.SearchHistory.CONTENT_URI, values);
    }
}
