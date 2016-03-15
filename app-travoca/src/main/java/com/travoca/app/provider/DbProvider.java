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

    private static final int FAVORITES = 100;

    private static final int FAVORITES_ID = 101;

    private static final int SEARCH_HISTORY = 200;

    private static final int SERVICE_GPS_REQUESTS = 300;

    private static final int SERVICE_GPS_RESULTS = 400;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private DbDatabase mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DbContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, "favorites", FAVORITES);
        matcher.addURI(authority, "favorites/*", FAVORITES_ID);
        matcher.addURI(authority, "search_history", SEARCH_HISTORY);
        matcher.addURI(authority, "service_gps_requests", SERVICE_GPS_REQUESTS);
        matcher.addURI(authority, "service_gps_results", SERVICE_GPS_RESULTS);

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
            case FAVORITES:
                return DbContract.Favorites.CONTENT_TYPE;
            case FAVORITES_ID:
                return DbContract.Favorites.CONTENT_TYPE;
            case SEARCH_HISTORY:
                return DbContract.SearchHistory.CONTENT_TYPE;
            case SERVICE_GPS_REQUESTS:
                return DbContract.ServiceGpsRequests.CONTENT_TYPE;
            case SERVICE_GPS_RESULTS:
                return DbContract.ServiceGpsResults.CONTENT_TYPE;

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
            case FAVORITES:
                builder.table(DbContract.Tables.TABLE_FAVORITES);
                if (uri.getQueryParameter("group by") != null) {
                    return builder.groupBy(uri.getQueryParameter("group by"))
                            .query(db, false, getFavoritesGroupByColumns(), "", "");
                } else if (uri.getQueryParameter("where") != null) {
                    return builder.where(uri.getQueryParameter("where"))
                            .query(db, false, projection, "", "");
                } else {
                    return builder.query(db, false, projection, "", "");
                }

            case FAVORITES_ID:
                final String recordId = DbContract.Favorites.getRecordId(uri);
                builder.table(DbContract.Tables.TABLE_FAVORITES)
                        .where(DbContract.FavoritesColumns.KEY_ID + " = ?", recordId);
                return builder.query(db, false, projection, "", "");
            case SEARCH_HISTORY:
                builder.table(DbContract.Tables.TABLE_SEARCH_HISTORY);
                return builder.query(db, false, getSearchHistoryColumns(),
                        DbContract.SearchHistoryColumns.CREATE_AT + " DESC",
                        uri.getQueryParameter("limit"));
            case SERVICE_GPS_REQUESTS:
                builder.table(DbContract.Tables.TABLE_SERVICE_GPS_REQUESTS);
                if (uri.getQueryParameter("limit") != null && !uri.getQueryParameter("limit")
                        .isEmpty()) {
                    return builder.query(db, false, projection,
                            DbContract.ServiceGpsResultsColumns.CREATE_AT + " DESC",
                            uri.getQueryParameter("limit"));
                } else {
                    return builder.where(selection).query(db, false, projection, "", "");
                }
            case SERVICE_GPS_RESULTS:
                builder.table(DbContract.Tables.TABLE_SERVICE_GPS_RESULTS);
                return builder.where(selection).query(db, false, projection, "", "");
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
            case FAVORITES:
                db.insert(DbContract.Tables.TABLE_FAVORITES, null, values);
                return DbContract.Favorites
                        .buildRecordUri(values.getAsString(DbContract.FavoritesColumns.KEY_ID),
                                values.getAsString(DbContract.FavoritesColumns.TITLE),
                                values.getAsString(DbContract.FavoritesColumns.TEXT));
            case SERVICE_GPS_REQUESTS:
                db.insert(DbContract.Tables.TABLE_SERVICE_GPS_REQUESTS, null, values);
                return DbContract.ServiceGpsRequests
                        .buildRecordUri(
                                values.getAsString(DbContract.ServiceGpsRequestsColumns.KEY_ID),
                                values.getAsString(
                                        DbContract.ServiceGpsRequestsColumns.RADIUS),
                                values.getAsString(DbContract.ServiceGpsRequestsColumns.LAT),
                                values.getAsString(DbContract.ServiceGpsRequestsColumns.LON),
                                values.getAsString(DbContract.ServiceGpsRequestsColumns.CREATE_AT));
            case SERVICE_GPS_RESULTS:
                db.insert(DbContract.Tables.TABLE_SERVICE_GPS_RESULTS, null, values);
                return DbContract.ServiceGpsResults
                        .buildRecordUri(
                                values.getAsString(DbContract.ServiceGpsResultsColumns.KEY_ID),
                                values.getAsString(DbContract.ServiceGpsResultsColumns.USED),
                                values.getAsString(
                                        DbContract.ServiceGpsResultsColumns.LOCATION_NAME),
                                values.getAsString(DbContract.ServiceGpsResultsColumns.LAT),
                                values.getAsString(DbContract.ServiceGpsResultsColumns.LON),
                                values.getAsString(DbContract.ServiceGpsResultsColumns.CREATE_AT));
            case SEARCH_HISTORY:
                db.insert(DbContract.Tables.TABLE_SEARCH_HISTORY, null, values);
                return DbContract.SearchHistory.buildSearchHistoryUri(
                        values.getAsString(DbContract.SearchHistoryColumns.LOCATION_NAME),
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
        switch (match) {
            case SERVICE_GPS_REQUESTS:
                builder.table(DbContract.Tables.TABLE_SERVICE_GPS_REQUESTS);
                break;
            case SERVICE_GPS_RESULTS:
                builder.table(DbContract.Tables.TABLE_SERVICE_GPS_RESULTS);
                break;
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }

        }
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
        if (match == FAVORITES_ID) {
            builder.table(DbContract.Tables.TABLE_FAVORITES)
                    .where(DbContract.FavoritesColumns.KEY_ID + "=?", uri.getPathSegments().get(1));
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
