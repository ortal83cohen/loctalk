package com.travoca.app.activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.squareup.otto.Subscribe;
import com.travoca.api.model.Record;
import com.travoca.api.model.SearchRequest;
import com.travoca.api.model.search.SprType;
import com.travoca.api.model.search.Type;
import com.travoca.api.model.search.ViewPortType;
import com.travoca.api.utils.RequestUtils;
import com.travoca.app.App;
import com.travoca.app.R;
import com.travoca.app.adapter.RecordViewHolder;
import com.travoca.app.analytics.AnalyticsCalls;
import com.travoca.app.events.Events;
import com.travoca.app.events.SearchRequestEvent;
import com.travoca.app.events.SearchResultsEvent;
import com.travoca.app.fragment.HomeFragment;
import com.travoca.app.fragment.RecordListFragment;
import com.travoca.app.fragment.RecordMapSummaryFragment;
import com.travoca.app.fragment.RecordsMapFragment;
import com.travoca.app.fragment.ResultsSortFragment;
import com.travoca.app.map.ResultsMap;
import com.travoca.app.model.RecordListRequest;
import com.travoca.app.widget.AppBar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author ortal
 * @date 2015-04-19
 */
public class RecordListActivity extends BaseActivity
        implements OnMapReadyCallback, ResultsMap.Listener, RecordViewHolder.Listener,
        RecordListFragment.Listener, FragmentManager.OnBackStackChangedListener,
        HomeFragment.Listener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String FRAGMENT_HOME = "fragment_datepicker";

    private static final String FRAGMENT_RESULTSLIST = "fragment_listview";

    private static final String FRAGMENT_MAP = "menu_map";

    private static final String FRAGMENT_SORT = "fragment_sort";

    private static final String FRAGMENT_HOTEL_SUMMARY = "fragment_record_summary";

    protected GoogleApiClient mGoogleApiClient;

    @Bind(R.id.app_bar)
    AppBar mToolbar;

    @Bind(R.id.refresh_records)
    Button mRefreshRecords;

    @Bind(R.id.loader_image)
    ImageView mLoaderImage;

    private ResultsMap mMap;

    private int mRateMinPrice = 10;

    private int mRateMaxPrice = 1250;

    private HashMap<Integer, Integer> mPoisTypes;

    public static Intent createIntent(RecordListRequest request, Context context) {
        Intent intent = new Intent(context, RecordListActivity.class);
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
        setTitle(getRecordsRequest());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        configLoaderImage();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mRefreshRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
                    mMap.updateRequest();
                }
                hideRefreshRecordsButton();
                showLoaderImage();
                refreshMap();
            }
        });

        if (savedInstanceState == null) {
            showList();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        RecordsMapFragment fragmentMap = (RecordsMapFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_MAP);
        if (fragmentMap != null) {
            fragmentMap.getMapAsync(this);
        }
        // For the initial search track here, all others will be tracked with SearchRequestEvent
        AnalyticsCalls.get().trackSearchResults(getRecordsRequest());
    }

    @Override
    protected void onCreateContentView() {
        setContentView(R.layout.activity_records_list);
    }

    public void setTitle(SearchRequest recordsRequest) {
        mToolbar.setLocation(recordsRequest);
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
        setTitle(getRecordsRequest());
    }

    @Override
    public void onLandmarksTypesChange(HashMap<Integer, Integer> types) {
        mPoisTypes = types;
    }

    @Override
    public void onRecordMarkerClick(Record acc) {

        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_overlay_container);

        if (fragment != null && fragment instanceof RecordMapSummaryFragment && fragment
                .isVisible()) {
            getSupportFragmentManager().popBackStack();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_overlay_container, RecordMapSummaryFragment.newInstance(acc),
                        FRAGMENT_HOTEL_SUMMARY)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onRecordClick(Record record, int position) {
        startActivity(RecordDetailsActivity.createIntent(record, getRecordsRequest(), this));
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            Intent startMain = new Intent(this, HomeActivity.class);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
        RecordMapSummaryFragment recordMapSummaryFragment
                = (RecordMapSummaryFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_HOTEL_SUMMARY);
        if (recordMapSummaryFragment == null) {
            hideRefreshRecordsButton();
        }
        super.onBackPressed();
    }

    @Override
    public void removeRecordSummaryFragment() {
        RecordMapSummaryFragment recordMapSummaryFragment
                = (RecordMapSummaryFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_HOTEL_SUMMARY);
        if (recordMapSummaryFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(recordMapSummaryFragment)
                    .commit();
        }
    }


    public void refreshList() {
        RecordListFragment listFragment = (RecordListFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_RESULTSLIST);
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

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshResults(boolean[] result) {
        if (mMap != null) {
            mMap.refreshRecords();
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
        RecordsMapFragment mapFragment = (RecordsMapFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_MAP);
        if (mapFragment == null) {
            mapFragment = new RecordsMapFragment();
            mapFragment.getMapAsync(this);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mapFragment, FRAGMENT_MAP)
                .addToBackStack(null)
                .commit();
    }

    public void showList() {
        removeRecordSummaryFragment();
        hideRefreshRecordsButton();

        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_RESULTSLIST) != null) {
            // Got to initial state of the stack and resume list from stack instead of adding a new one
            getSupportFragmentManager()
                    .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, RecordListFragment.newInstance(),
                            FRAGMENT_RESULTSLIST)
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
        getRecordsRequest().setType(locationType);
        RequestUtils.apply(getRecordsRequest());
        App.provide(this).updateLastSeatchRequest(getRecordsRequest());
        if (mMap != null) {
            mMap.refreshRecords();
            if (locationType instanceof ViewPortType) {
                mMap.moveCamera(((ViewPortType) locationType).getNortheastLat(),
                        ((ViewPortType) locationType).getNortheastLon(),
                        ((ViewPortType) locationType).getSouthwestLat(),
                        ((ViewPortType) locationType).getSouthwestLon());
            } else if (locationType instanceof SprType) {
                mMap.moveCamera(((SprType) locationType).getLatitude(),
                        ((SprType) locationType).getLongitude());
            }
        }
        refreshList();
        setTitle(getRecordsRequest());
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Subscribe
    public void onSearchRequestEvent(SearchRequestEvent event) {
        if (event.getOffset() == 0) { // Only for the fresh recordButton
            AnalyticsCalls.get().trackSearchResults(event.getSearchRequest());
        }
    }

    @Subscribe
    public void onSearchResultsEvent(SearchResultsEvent event) {

    }

    public void hideRefreshRecordsButton() {
        mRefreshRecords.setVisibility(View.GONE);
    }

    public void showRefreshRecordsButton() {
        mRefreshRecords.setVisibility(View.VISIBLE);
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
        mMap.refreshRecords();
        setTitle(getRecordsRequest());
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
