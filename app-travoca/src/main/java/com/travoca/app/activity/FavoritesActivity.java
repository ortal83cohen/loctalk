package com.travoca.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.travoca.api.model.Record;
import com.travoca.api.model.search.Type;
import com.travoca.app.App;
import com.travoca.app.R;
import com.travoca.app.adapter.RecordViewHolder;
import com.travoca.app.fragment.FavoritesFragment;
import com.travoca.app.fragment.HomeFragment;
import com.travoca.app.widget.AppBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author ortal
 * @date 2015-06-14
 */
public class FavoritesActivity extends BaseActivity implements RecordViewHolder.Listener, HomeFragment.Listener {

    private static final String FRAGMENT_FAVORITES_CITIES = "favorites_cities";
    private static final String FRAGMENT_FAVORITES_LIST = "favorites_list";
    private static final String FRAGMENT_HOME = "home";
    @Bind(R.id.app_bar)
    AppBar mToolbar;


    public static Intent createIntent(Context context) {
        return new Intent(context, FavoritesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setHotelsRequest(App.provide(this).createHotelsRequest());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, FavoritesFragment.newInstance(),
                        FRAGMENT_FAVORITES_CITIES)
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }


    public void setSubtitle(String titleId) {
        mToolbar.setSubtitle(titleId);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOME) != null) {
            super.onBackPressed();
        } else if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_FAVORITES_LIST) != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, FavoritesFragment.newInstance(),
                            FRAGMENT_FAVORITES_CITIES)
                    .commit();
            setSubtitle("");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreateContentView() {
        setContentView(R.layout.activity_favorites);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hotel_details, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onRecordClick(Record acc, int position) {

    }

    @Override
    public GoogleApiClient getGoogleApiClient() {
        return null;
    }

    @Override
    public void startSearch(Type locationType) {

    }
}
