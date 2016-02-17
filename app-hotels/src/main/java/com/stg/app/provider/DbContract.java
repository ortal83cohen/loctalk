package com.stg.app.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.stg.app.BuildConfig;

import java.util.List;

/**
 * @author ortal
 * @date 2015-07-13
 */
public class DbContract {
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final String PATH_LIKED_HOTELS = "favorites";
    private static final String PATH_SEARCH_HISTORY = "search_history";

    interface Tables {
        String TABLE_LIKED_HOTELS = "liked_hotels";
        String TABLE_SEARCH_HISTORY = "search_history";
    }

    public interface LikedHotelsColumns {
        String KEY_ID = "_id";
        String CITY = "city";
        String COUNTRY = "country";
    }


    public interface SearchHistoryColumns {
        String LOCATION_NAME = "location_name";
        String LAT = "lat";
        String LON = "lon";
        String NORTHEAST_LAT = "northeast_lat";
        String NORTHEAST_LON = "northeast_lon";
        String SOUTHWEST_LAT = "southwest_lat";
        String SOUTHWEST_LON = "southwest_lon";
        String TYPES = "types";
        String NUMBER_GUESTS = "number_guests";
        String NUMBER_ROOMS = "number_rooms";
        String FROM_DATE = "from_date";
        String TO_DATE = "to_date";
        String CREATE_AT = "created_at";
    }

    public static class LikedHotels implements LikedHotelsColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LIKED_HOTELS).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/int";

        public static Uri buildHotelUri(String hotelId, String city, String country) {
            return CONTENT_URI.buildUpon().appendPath(hotelId).appendPath(city).appendPath(country).build();
        }

        public static String getHotelId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class SearchHistory implements SearchHistoryColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SEARCH_HISTORY).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/int";

        public static Uri buildSearchHistoryUri(String location, String lat, String lon, String northeastLat, String northeastLon, String southwestLat, String southwestLon,
                                                String types, String numberGuests, String numberRooms, String fromDate, String toDate) {
            return CONTENT_URI.buildUpon().appendPath(location).appendPath(lat).appendPath(lon).appendPath(northeastLat).appendPath(northeastLon).appendPath(southwestLat).appendPath(southwestLon).appendPath(types).appendPath(numberGuests).appendPath(numberRooms).appendPath(fromDate).appendPath(toDate).build();
        }

        public static List<String> getSearchHistory(Uri uri) {
            return uri.getPathSegments();
        }
    }


}
