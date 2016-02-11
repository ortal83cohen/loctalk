package com.stg.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.socialtravelguide.api.model.Accommodation;
import com.socialtravelguide.api.model.SearchRequest;
import com.socialtravelguide.api.model.search.SprType;
import com.socialtravelguide.api.model.search.Type;
import com.socialtravelguide.api.model.search.ViewPortType;
import com.socialtravelguide.api.utils.RequestUtils;
import com.stg.app.App;
import com.stg.app.R;
import com.stg.app.adapter.HotelViewHolder;
import com.stg.app.analytics.AnalyticsCalls;
import com.stg.app.events.Events;
import com.stg.app.events.SearchRequestEvent;
import com.stg.app.events.SearchResultsEvent;
import com.stg.app.fragment.HomeFragment;
import com.stg.app.fragment.HotelMapSummaryFragment;
import com.stg.app.fragment.HotelsListFragment;
import com.stg.app.fragment.HotelsMapFragment;
import com.stg.app.fragment.ResultsSortFragment;
import com.stg.app.hoteldetails.HotelSnippet;
import com.stg.app.map.PoiMarker;
import com.stg.app.map.ResultsMap;
import com.stg.app.model.HotelListRequest;
import com.stg.app.utils.AppLog;
import com.stg.app.widget.AppBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author ortal
 * @date 2015-04-19
 */
public class HotelListActivity extends BaseActivity implements OnMapReadyCallback, ResultsMap.Listener, HotelViewHolder.Listener, HotelsListFragment.Listener, FragmentManager.OnBackStackChangedListener, HomeFragment.Listener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final String FRAGMENT_HOME = "fragment_datepicker";
    private static final String FRAGMENT_RESULTSLIST = "fragment_listview";
    private static final String FRAGMENT_MAP = "menu_map";
    private static final String FRAGMENT_SORT = "fragment_sort";
    private static final String FRAGMENT_HOTEL_SUMMARY = "fragment_hotel_summary";
    protected GoogleApiClient mGoogleApiClient;
    @Bind(R.id.app_bar)
    AppBar mToolbar;
    @Bind(R.id.refresh_hotels)
    Button mRefreshHotels;
    @Bind(R.id.loader_image)
    ImageView mLoaderImage;
    private ResultsMap mMap;

    private int mRateMinPrice = 10;
    private int mRateMaxPrice = 1250;
    private HashMap<Integer, Integer> mPoisTypes;
    private boolean[] mPoisFilter;

    public static Intent createIntent(HotelListRequest request, Context context) {
        Intent intent = new Intent(context, HotelListActivity.class);
        intent.putExtra(EXTRA_REQUEST, request);
        return intent;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Events.register(this);
    }

    @Override
    protected void onPause() {
        Events.unregister(this);
        super.onPause();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setTitle(getHotelsRequest());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        configLoaderImage();

        mPoisFilter = createFilters(PoiMarker.TYPES);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mRefreshHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
                    mMap.updateRequest();
                }
                hideRefreshHotelsButton();
                showLoaderImage();
                refreshMap();
            }
        });

        if (savedInstanceState == null) {
            showList();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        HotelsMapFragment fragmentMap = (HotelsMapFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_MAP);
        if (fragmentMap != null) {
            fragmentMap.getMapAsync(this);
        }
        // For the initial search track here, all others will be tracked with SearchRequestEvent
        AnalyticsCalls.get().trackSearchResults(getHotelsRequest());
    }

    @Override
    protected void onCreateContentView() {
        setContentView(R.layout.activity_hotel_list);
    }

    public void setTitle(SearchRequest hotelsRequest) {
        mToolbar.setLocation(hotelsRequest);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                R.string.could_not_connect_to_google + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        mToolbar.setSubtitle("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = new ResultsMap(googleMap, this, this);
        setTitle(getHotelsRequest());
    }

    @Override
    public void onLandmarksTypesChange(HashMap<Integer, Integer> types) {
        mPoisTypes = types;
    }

    @Override
    public void onHotelMarkerClick(Accommodation acc) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_overlay_container);

        if (fragment != null && fragment instanceof HotelMapSummaryFragment && fragment.isVisible()) {
            getSupportFragmentManager().popBackStack();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_overlay_container, HotelMapSummaryFragment.newInstance(acc),
                        FRAGMENT_HOTEL_SUMMARY)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onHotelClick(Accommodation acc, int position) {
        if (acc.getFirstRate() == null) {
            Toast.makeText(getBaseContext(), R.string.hotel_not_available, Toast.LENGTH_LONG).show();
            AppLog.e("HotelListActivity: hotel_not_available-" + acc.id);
        } else {
            final HotelSnippet hotelSnippet = HotelSnippet.from(acc, acc.getFirstRate().rateId, position);
            showHotelDetails(hotelSnippet);
        }
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            Intent startMain = new Intent(this, HomeActivity.class);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
        HotelMapSummaryFragment hotelMapSummaryFragment = (HotelMapSummaryFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOTEL_SUMMARY);
        if (hotelMapSummaryFragment == null) {
            hideRefreshHotelsButton();
        }
        super.onBackPressed();
    }

    @Override
    public void removeHotelSummaryFragment() {
        HotelMapSummaryFragment hotelMapSummaryFragment = (HotelMapSummaryFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOTEL_SUMMARY);
        if (hotelMapSummaryFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(hotelMapSummaryFragment)
                    .commit();
        }
    }


    public void showHotelDetails(HotelSnippet hotelSnippet) {

        startActivity(HotelSummaryActivity.createIntent(hotelSnippet, getHotelsRequest(), this));

    }

    public void refreshList() {
        HotelsListFragment listFragment = (HotelsListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_RESULTSLIST);
        if (listFragment != null) {
            listFragment.refresh();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list:
                showList();
                break;
            case R.id.menu_map:
                showMap();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean[] createFilters(int[] values) {
        boolean[] selected = new boolean[values.length];

        for (int i = 0; i < values.length; i++) {
            selected[i] = false;
        }

        return selected;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            refreshResults(mPoisFilter);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshResults(boolean[] result) {
        if (mMap != null) {
            mMap.setPoiFilters(result);
            mMap.refreshHotels();
        }
        refreshList();
    }

    public void showSort() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_overlay_container, ResultsSortFragment.newInstance(),
                        FRAGMENT_SORT)
                .addToBackStack("sort")
                .commit();
    }

    private void showMap() {
        HotelsMapFragment mapFragment = (HotelsMapFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_MAP);
        if (mapFragment == null) {
            mapFragment = new HotelsMapFragment();
            mapFragment.getMapAsync(this);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mapFragment, FRAGMENT_MAP)
                .addToBackStack(null)
                .commit();
    }

    public void showList() {
        removeHotelSummaryFragment();
        hideRefreshHotelsButton();

        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_RESULTSLIST) != null) {
            // Got to initial state of the stack and resume list from stack instead of adding a new one
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, HotelsListFragment.newInstance(), FRAGMENT_RESULTSLIST)
                    .commit();
        }
    }

    @Override
    public void onBackStackChanged() {
    }

    @Override
    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    public void startSearch(Type locationType) {
        remove(getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOME));
        getHotelsRequest().setType(locationType);
        RequestUtils.apply(getHotelsRequest());
        App.provide(this).updateLastSeatchRequest(getHotelsRequest());
        if (mMap != null) {
            mMap.refreshHotels();
            mMap.setPoiFilters(mPoisFilter);
            if (locationType instanceof ViewPortType) {
                mMap.moveCamera(((ViewPortType) locationType).getNortheastLat(), ((ViewPortType) locationType).getNortheastLon(), ((ViewPortType) locationType).getSouthwestLat(), ((ViewPortType) locationType).getSouthwestLon());
            } else if (locationType instanceof SprType) {
                mMap.moveCamera(((SprType) locationType).getLatitude(), ((SprType) locationType).getLongitude());
            }
        }
        refreshList();
        setTitle(getHotelsRequest());
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Subscribe
    public void onSearchRequestEvent(SearchRequestEvent event) {
        if (event.getOffset() == 0) { // Only for the fresh records
            AnalyticsCalls.get().trackSearchResults(event.getSearchRequest());
        }
    }

    @Subscribe
    public void onSearchResultsEvent(SearchResultsEvent event) {

    }

    public void hideRefreshHotelsButton() {
        mRefreshHotels.setVisibility(View.GONE);
    }

    public void showRefreshHotelsButton() {
        mRefreshHotels.setVisibility(View.VISIBLE);
    }

    private void configLoaderImage() {
        mLoaderImage.setBackgroundResource(R.drawable.logo_animation);
        AnimationDrawable logoAnimation = (AnimationDrawable) mLoaderImage.getBackground();
        logoAnimation.start();
        showLoaderImage();
    }

    public void hideLoaderImage() {
        mLoaderImage.setVisibility(View.GONE);
    }

    public void showLoaderImage() {
        mLoaderImage.setVisibility(View.VISIBLE);
    }

    public void refreshMap() {
        mMap.refreshHotels();
        setTitle(getHotelsRequest());
    }


    public void setRateMinPrice(int mRateMinPrice) {
        this.mRateMinPrice = mRateMinPrice;
    }

    public void setRateMaxPrice(int mRateMaxPrice) {
        this.mRateMaxPrice = mRateMaxPrice;
    }

    @Override
    public void onEditLocationClick() {

    }


}
