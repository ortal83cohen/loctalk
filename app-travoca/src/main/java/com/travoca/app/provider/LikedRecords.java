package com.travoca.app.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * @author ortal
 * @date 2015-12-02
 */
public class LikedRecords {
    public static boolean isLiked(int recordId, Context context) {
        Cursor c = context.getContentResolver().query(DbContract.Favorites.CONTENT_URI.buildUpon().appendPath(
                String.valueOf(recordId)).build(), null, null, null, null);
        boolean liked = false;
        if (c != null) {
            if (c.moveToFirst()) {
                liked = true;
            }
            c.close();
        }
        return liked;
    }

    public static void delete(int recordId, Context context) {
        context.getContentResolver().delete(DbContract.Favorites.CONTENT_URI.buildUpon().appendPath(String.valueOf(recordId)).build(), null, null);
    }

    public static void insert(int recordId, String title, String text, Context context) {
        ContentValues values = new ContentValues();
        values.put(DbContract.FavoritesColumns.KEY_ID, recordId);
        values.put(DbContract.FavoritesColumns.TITLE, title);
        values.put(DbContract.FavoritesColumns.TEXT, text);
        context.getContentResolver().insert(DbContract.Favorites.CONTENT_URI, values);

    }

    public static ArrayList<String> loadRecords(String city, String country, Context context) {
        Cursor cursor = context.getContentResolver().query(DbContract.Favorites.CONTENT_URI.buildUpon().
                appendQueryParameter("where", DbContract.FavoritesColumns.TITLE + "='" + city + "' AND " + DbContract.FavoritesColumns.TEXT + "='" + country + "'").build(), null, null, null, null);
        ArrayList<String> records = new ArrayList<>();
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                records.add(cursor.getString(cursor.getColumnIndex(DbContract.FavoritesColumns.KEY_ID)));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return records;
    }
}
