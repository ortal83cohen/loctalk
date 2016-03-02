package com.travoca.app.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.travoca.app.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class RecentSearchesAdapter extends CursorAdapter {

    public RecentSearchesAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // when the view will be created for first time,
        // we need to tell the adapters, how each item will look
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return inflater.inflate(R.layout.autocomplite_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewPersonName = (TextView) view.findViewById(android.R.id.title);
        Resources r = context.getResources();

        String[] locationName = cursor.getString(cursor.getColumnIndex("location_name")).split(",");
        String title = "<font color=\"black\"> <b>" + locationName[0] + " </b>";
        for (int i = 1; i < locationName.length; i++) {
            title += locationName[i];
        }
        String subTitle = "";

        textViewPersonName.setText(
                Html.fromHtml(title + "</font>" + subTitle));

        textViewPersonName.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.mainnav_recent_search, 0, 0, 0);
        int padding = (int) r.getDimension(R.dimen.minimum_default_padding);
        textViewPersonName.setPadding(0, padding, padding, padding);

    }
}