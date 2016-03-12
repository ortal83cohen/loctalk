package com.travoca.app.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.travoca.app.BuildConfig;

import java.util.List;

/**
 * @author ortal
 * @date 2015-07-13
 */
public class DbContract {
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final String PATH_FAVORITES = "favorites";
    private static final String PATH_SERVICE_GPS = "service_gps";
    private static final String PATH_SERVICE_GPS_LAST_DATE = "service_gps/last_date";
    private static final String PATH_SEARCH_HISTORY = "search_history";

    interface Tables {
        String TABLE_SERVICE_GPS = "service_gps";
        String TABLE_FAVORITES = "favorites";
        String TABLE_SEARCH_HISTORY = "search_history";
    }

    public interface ServiceGpsColumns {
        String KEY_ID = "_id";
        String USED = "used";
        String LOCATION_NAME = "location_name";
        String LAT = "lat";
        String LON = "lon";
        String CREATE_AT = "created_at";
    }

    public interface FavoritesColumns {
        String KEY_ID = "_id";
        String TITLE = "title";
        String TEXT = "text";
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
        String CREATE_AT = "created_at";
    }

    public static class Favorites implements FavoritesColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/int";

        public static Uri buildRecordUri(String recordId, String city, String country) {
            return CONTENT_URI.buildUpon().appendPath(recordId).appendPath(city).appendPath(country).build();
        }

        public static String getRecordId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class ServiceGps implements ServiceGpsColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SERVICE_GPS).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/int";

        public static Uri buildRecordUri(String id,String used, String name, String lat,String lon,String created) {
            return CONTENT_URI.buildUpon().appendPath(id).appendPath(used).appendPath(name).appendPath(lat).appendPath(lon).appendPath(created).build();
        }

        public static List<String> getList(Uri uri) {
            return uri.getPathSegments();
        }
    }

    public static class SearchHistory implements SearchHistoryColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SEARCH_HISTORY).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/int";

        public static Uri buildSearchHistoryUri(String location, String lat, String lon, String northeastLat, String northeastLon, String southwestLat, String southwestLon,
                                                String types) {
            return CONTENT_URI.buildUpon().appendPath(location).appendPath(lat).appendPath(lon).appendPath(northeastLat).appendPath(northeastLon).appendPath(southwestLat).appendPath(southwestLon).appendPath(types).build();
        }

        public static List<String> getSearchHistory(Uri uri) {
            return uri.getPathSegments();
        }
    }


}
