package com.travoca.app.provider;

/**
 * @author ortal
 * @date 2015-07-12
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbDatabase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 70;

    // Database Name
    private static final String DATABASE_NAME = "TravocaDB";

    public DbDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Record table
        String CREATE_HOTEL_TABLE = "CREATE TABLE IF NOT EXISTS "
                + DbContract.Tables.TABLE_FAVORITES + " ( " +
                DbContract.FavoritesColumns.KEY_ID + " INTEGER ," +
                DbContract.FavoritesColumns.TITLE + " STRING , " +
                DbContract.FavoritesColumns.TEXT + " STRING , " +
                " PRIMARY KEY (" + DbContract.FavoritesColumns.KEY_ID + ") ) ";

        String CREATE_SEARCH_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS "
                + DbContract.Tables.TABLE_SEARCH_HISTORY + " ( " +
                DbContract.SearchHistoryColumns.LOCATION_NAME + " STRING ," +
                DbContract.SearchHistoryColumns.LAT + " STRING ," +
                DbContract.SearchHistoryColumns.LON + " STRING ," +
                DbContract.SearchHistoryColumns.NORTHEAST_LAT + " STRING," +
                DbContract.SearchHistoryColumns.NORTHEAST_LON + " STRING," +
                DbContract.SearchHistoryColumns.SOUTHWEST_LAT + " STRING," +
                DbContract.SearchHistoryColumns.SOUTHWEST_LON + " STRING," +
                DbContract.SearchHistoryColumns.TYPES + " STRING," +
                DbContract.SearchHistoryColumns.CREATE_AT
                + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                " PRIMARY KEY (" + DbContract.SearchHistoryColumns.LOCATION_NAME + ")  )";

        String CREATE_SERVICE_GPS_RESULTS_TABLE = "CREATE TABLE IF NOT EXISTS "
                + DbContract.Tables.TABLE_SERVICE_GPS_RESULTS + " ( " +
                DbContract.ServiceGpsResultsColumns.KEY_ID + " INTEGER ," +
                DbContract.ServiceGpsResultsColumns.USED + " INTEGER DEFAULT '0' ," +
                DbContract.ServiceGpsResultsColumns.LOCATION_NAME + " STRING ," +
                DbContract.ServiceGpsResultsColumns.LAT + " STRING ," +
                DbContract.ServiceGpsResultsColumns.LON + " STRING ," +
                DbContract.ServiceGpsResultsColumns.CREATE_AT + " TIMESTAMP ," +
                " PRIMARY KEY (" + DbContract.ServiceGpsResultsColumns.LAT + ","
                + DbContract.ServiceGpsResultsColumns.LON +
                ") ) ";

        String CREATE_SERVICE_GPS_REQUEST_TABLE = "CREATE TABLE IF NOT EXISTS "
                + DbContract.Tables.TABLE_SERVICE_GPS_REQUESTS + " ( " +
                DbContract.ServiceGpsRequestsColumns.KEY_ID + " INTEGER ," +
                DbContract.ServiceGpsRequestsColumns.RADIUS + " INTEGER ," +
                DbContract.ServiceGpsRequestsColumns.LAT + " STRING ," +
                DbContract.ServiceGpsRequestsColumns.LON + " STRING ," +
                DbContract.ServiceGpsRequestsColumns.CREATE_AT + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ," +
                " PRIMARY KEY (" + DbContract.ServiceGpsRequestsColumns.LAT + ","
                + DbContract.ServiceGpsRequestsColumns.LON +
                ") ) ";
        // create Records table
        db.execSQL(CREATE_HOTEL_TABLE);
        db.execSQL(CREATE_SERVICE_GPS_RESULTS_TABLE);
        db.execSQL(CREATE_SERVICE_GPS_REQUEST_TABLE);
        db.execSQL(CREATE_SEARCH_HISTORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DbContract.Tables.TABLE_FAVORITES);
            db.execSQL("DROP TABLE IF EXISTS " + DbContract.Tables.TABLE_SERVICE_GPS_RESULTS);
            db.execSQL("DROP TABLE IF EXISTS service_gps" );
            db.execSQL("DROP TABLE IF EXISTS " + DbContract.Tables.TABLE_SERVICE_GPS_REQUESTS);
            db.execSQL("DROP TABLE IF EXISTS " + DbContract.Tables.TABLE_SEARCH_HISTORY);

        }
        this.onCreate(db);
    }

}