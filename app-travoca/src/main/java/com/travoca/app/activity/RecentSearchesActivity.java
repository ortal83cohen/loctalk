package com.travoca.app.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.travoca.app.App;
import com.travoca.app.R;
import com.travoca.app.adapter.RecentSearchesAdapter;
import com.travoca.app.model.Location;
import com.travoca.app.model.RecordListRequest;
import com.travoca.app.provider.DbContract;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecentSearchesActivity extends BaseActivity {
    @Bind(android.R.id.list)
    ListView mRecyclerView;
    @Bind(R.id.record_list_no_result)
    LinearLayout mNoResult;

    public static Intent createIntent(Context context) {
        return new Intent(context, RecentSearchesActivity.class);
    }

    @Override
    protected boolean requiresRequest() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Cursor cursor = getContentResolver().query(DbContract.SearchHistory.CONTENT_URI.buildUpon().build(), null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() == 0) {
                mNoResult.setVisibility(View.VISIBLE);
            } else {
                mNoResult.setVisibility(View.GONE);
            }
        }

        mRecyclerView.setAdapter(new RecentSearchesAdapter(this, cursor));
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {

                        String northeastLat = cursor.getString(cursor.getColumnIndex(DbContract.SearchHistoryColumns.NORTHEAST_LAT));
                        String northeastLon = cursor.getString(cursor.getColumnIndex(DbContract.SearchHistoryColumns.NORTHEAST_LON));
                        String southwestLat = cursor.getString(cursor.getColumnIndex(DbContract.SearchHistoryColumns.SOUTHWEST_LAT));
                        String southwestLon = cursor.getString(cursor.getColumnIndex(DbContract.SearchHistoryColumns.SOUTHWEST_LON));
                        startSearch(new Location(cursor.getString(cursor.getColumnIndex(DbContract.SearchHistoryColumns.LOCATION_NAME)), new LatLng(cursor.getDouble(cursor.getColumnIndex(DbContract.SearchHistoryColumns.LAT)), cursor.getDouble(cursor.getColumnIndex(DbContract.SearchHistoryColumns.LON)))));

                }
            }
        });

    }

    public void startSearch(Location location) {
        RecordListRequest request = App.provide(this).createRequest();

        startActivity(RecordListActivity.createIntent(request, this));
    }

    @Override
    protected void onCreateContentView() {
        setContentView(R.layout.activity_recent_searches);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setTitle(getString(R.string.recent_searched));
    }


}
