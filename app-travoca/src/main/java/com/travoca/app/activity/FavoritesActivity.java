package com.travoca.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.travoca.api.model.Record;
import com.travoca.api.model.search.Type;
import com.travoca.api.utils.RequestUtils;
import com.travoca.app.App;
import com.travoca.app.R;
import com.travoca.app.adapter.RecordViewHolder;
import com.travoca.app.fragment.FavoritesCitiesFragment;
import com.travoca.app.fragment.FavoritesListFragment;
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
                .replace(R.id.fragment_container, FavoritesCitiesFragment.newInstance(),
                        FRAGMENT_FAVORITES_CITIES)
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_list:
                FavoritesListFragment favoritesListFragment = (FavoritesListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_FAVORITES_LIST);
                if (favoritesListFragment != null) {
                    favoritesListFragment.deleteList();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showFavoritesList(String city, String country, String count) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, FavoritesListFragment.newInstance(city, country, count, getHotelsRequest()),
                        FRAGMENT_FAVORITES_LIST)
                .commit();

        setSubtitle(getString(R.string.no_dates_selected));


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
                    .replace(R.id.fragment_container, FavoritesCitiesFragment.newInstance(),
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
//        if (getRequest().isDatesRequest()) {
//        Record firstRate = acc.getFirstRate();
//        int rateId = firstRate == null ? 0 : firstRate.rateId;
//        final HotelSnippet hotelSnippet = HotelSnippet.from(acc, rateId, position);
//        showRecordDetails(hotelSnippet);
//        } else {
//            showHome();
//        }
    }

    public void showHotelDetails(Record record) {

    }

    @Override
    public GoogleApiClient getGoogleApiClient() {
        return null;
    }

    @Override
    public void startSearch(Type locationType) {
        remove(getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOME));

        FavoritesListFragment favoritesListFragment = (FavoritesListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_FAVORITES_LIST);
        RequestUtils.apply(getHotelsRequest());
        if (favoritesListFragment != null) {
            favoritesListFragment.refresh(getHotelsRequest());
            setSubtitle(
                    "xxxxxxxxxxxxxxxxx"
            );
        }
    }
}
