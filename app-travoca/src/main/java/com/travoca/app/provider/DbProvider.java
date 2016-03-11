package com.travoca.app.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author ortal
 * @date 2015-07-13
 */
public class DbProvider extends ContentProvider {
    private static final int FAVORITES_HOTELS = 100;
    private static final int FAVORITES_HOTEL_ID = 101;
    private static final int SEARCH_HISTORY = 200;
    private static final int SERVICE_GPS = 300;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbDatabase mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DbContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, "favorites", FAVORITES_HOTELS);
        matcher.addURI(authority, "favorites/*", FAVORITES_HOTEL_ID);
        matcher.addURI(authority, "search_history", SEARCH_HISTORY);
        matcher.addURI(authority, "service_gps", SERVICE_GPS);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DbDatabase(getContext());
        return true;
    }

    private void deleteDatabase() {
        mOpenHelper.close();
        Context context = getContext();
        DbDatabase.deleteDatabase(context);
        mOpenHelper = new DbDatabase(getContext());
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES_HOTELS:
                return DbContract.Favorites.CONTENT_TYPE;
            case FAVORITES_HOTEL_ID:
                return DbContract.Favorites.CONTENT_TYPE;
            case SEARCH_HISTORY:
                return DbContract.SearchHistory.CONTENT_TYPE;
            case SERVICE_GPS:
                return DbContract.ServiceGps.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        final DbSelectionBuilder builder = new DbSelectionBuilder();
        switch (match) {
            case FAVORITES_HOTELS:
                builder.table(DbContract.Tables.TABLE_FAVORITES);
                if (uri.getQueryParameter("group by") != null) {
                    return builder.groupBy(uri.getQueryParameter("group by")).query(db, false, getFavoritesGroupByColumns(), "", "");
                } else if (uri.getQueryParameter("where") != null) {
                    return builder.where(uri.getQueryParameter("where")).query(db, false, projection, "", "");
                } else {
                    return builder.query(db, false, projection, "", "");
                }

            case FAVORITES_HOTEL_ID:
                final String recordId = DbContract.Favorites.getRecordId(uri);
                builder.table(DbContract.Tables.TABLE_FAVORITES).where(DbContract.FavoritesColumns.KEY_ID + " = ?", recordId);
                return builder.query(db, false, projection, "", "");
            case SEARCH_HISTORY:
                builder.table(DbContract.Tables.TABLE_SEARCH_HISTORY);
                return builder.query(db, false, getSearchHistoryColumns(), DbContract.SearchHistoryColumns.CREATE_AT + " DESC", uri.getQueryParameter("limit"));
            case SERVICE_GPS:
                builder.table(DbContract.Tables.TABLE_SERVICE_GPS);
                return builder.query(db, false, projection, "","");

            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }


    }

    private String[] getSearchHistoryColumns() {
        String[] columns = new String[2];
        columns[0] = "rowId _id";
        columns[1] = "*";

        return columns;
    }

    private String[] getFavoritesGroupByColumns() {
        String[] columns = new String[2];
        columns[0] = "count(*) as count";
        columns[1] = "*";

        return columns;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES_HOTELS:
                db.insert(DbContract.Tables.TABLE_FAVORITES, null, values);
                return DbContract.Favorites.buildRecordUri(values.getAsString(DbContract.FavoritesColumns.KEY_ID),
                        values.getAsString(DbContract.FavoritesColumns.TITLE),
                        values.getAsString(DbContract.FavoritesColumns.TEXT));
            case SERVICE_GPS:
                db.insert(DbContract.Tables.TABLE_SERVICE_GPS, null, values);
                return DbContract.ServiceGps.buildRecordUri(values.getAsString(DbContract.ServiceGpsColumns.KEY_ID),
                        values.getAsString(DbContract.ServiceGpsColumns.LOCATION_NAME),
                        values.getAsString(DbContract.ServiceGpsColumns.LAT),
                        values.getAsString(DbContract.ServiceGpsColumns.LON),
                        values.getAsString(DbContract.ServiceGpsColumns.CREATE_AT));
            case SEARCH_HISTORY:
                db.insert(DbContract.Tables.TABLE_SEARCH_HISTORY, null, values);
                return DbContract.SearchHistory.buildSearchHistoryUri(values.getAsString(DbContract.SearchHistoryColumns.LOCATION_NAME),
                        values.getAsString(DbContract.SearchHistoryColumns.LAT),
                        values.getAsString(DbContract.SearchHistoryColumns.LON),
                        values.getAsString(DbContract.SearchHistoryColumns.NORTHEAST_LAT),
                        values.getAsString(DbContract.SearchHistoryColumns.NORTHEAST_LON),
                        values.getAsString(DbContract.SearchHistoryColumns.SOUTHWEST_LAT),
                        values.getAsString(DbContract.SearchHistoryColumns.SOUTHWEST_LON),
                        values.getAsString(DbContract.SearchHistoryColumns.TYPES));

            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final DbSelectionBuilder builder = new DbSelectionBuilder();
        final int match = sUriMatcher.match(uri);

        return builder.where(selection, selectionArgs).update(db, values);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        if (uri.equals(DbContract.BASE_CONTENT_URI)) {
            deleteDatabase();
            return 1;
        }
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final DbSelectionBuilder builder = new DbSelectionBuilder();
        final int match = sUriMatcher.match(uri);
        if (match == FAVORITES_HOTEL_ID) {
            builder.table(DbContract.Tables.TABLE_FAVORITES).where(DbContract.FavoritesColumns.KEY_ID + "=?", uri.getPathSegments().get(1));
        }

        return builder.where(selection, selectionArgs).delete(db);
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }


}
