package com.travoca.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.otto.Subscribe;
import com.travoca.api.model.Record;
import com.travoca.api.model.SearchRequest;
import com.travoca.api.model.search.Type;
import com.travoca.api.utils.RequestUtils;
import com.travoca.app.App;
import com.travoca.app.R;
import com.travoca.app.adapter.RecordViewHolder;
import com.travoca.app.analytics.AnalyticsCalls;
import com.travoca.app.events.Events;
import com.travoca.app.events.MassageEvent;
import com.travoca.app.events.SearchRequestEvent;
import com.travoca.app.fragment.HomeFragment;
import com.travoca.app.fragment.HotelMapSummaryFragment;
import com.travoca.app.fragment.MyListFragment;
import com.travoca.app.model.RecordListRequest;
import com.travoca.app.widget.AppBar;
import com.travoca.app.widget.NavigationDrawer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author ortal
 * @date 2015-04-19
 */
public class MyListActivity extends BaseActivity implements RecordViewHolder.Listener, MyListFragment.Listener, FragmentManager.OnBackStackChangedListener, HomeFragment.Listener {

    private static final String FRAGMENT_HOME = "fragment_datepicker";
    private static final String FRAGMENT_RESULTSLIST = "fragment_listview";
    private static final String FRAGMENT_HOTEL_SUMMARY = "fragment_hotel_summary";

    @Bind(R.id.app_bar)
    AppBar mToolbar;
    @Bind(R.id.refresh_hotels)
    Button mRefreshHotels;
    @Bind(R.id.loader_image)
    ImageView mLoaderImage;
    @Bind(R.id.action_button)
    FloatingActionButton mAddFab;


    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MyListActivity.class);
        RecordListRequest request = new RecordListRequest();
        intent.putExtra(EXTRA_REQUEST, request);
        return intent;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Events.register(this);
    }

    @Override
    protected void onDestroy() {
        Events.unregister(this);
        super.onDestroy();
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

        if (savedInstanceState == null) {
            showList();
        }

        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPageChange(NavigationDrawer.ADD_NEW_RECORD, null);
            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        // For the initial search track here, all others will be tracked with SearchRequestEvent
        AnalyticsCalls.get().trackSearchResults(getRecordsRequest());
    }

    @Override
    protected void onCreateContentView() {
        setContentView(R.layout.activity_my_list);
    }

    public void setTitle(SearchRequest hotelsRequest) {
        mToolbar.setLocation(hotelsRequest);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        mToolbar.setSubtitle("");
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
        HotelMapSummaryFragment hotelMapSummaryFragment = (HotelMapSummaryFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOTEL_SUMMARY);
        if (hotelMapSummaryFragment == null) {
            hideRefreshHotelsButton();
        }
        super.onBackPressed();
    }

    public void refreshList() {
        MyListFragment listFragment = (MyListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_RESULTSLIST);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showList() {

        hideRefreshHotelsButton();

        mAddFab.setVisibility(View.VISIBLE);

        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_RESULTSLIST) != null) {
            // Got to initial state of the stack and resume list from stack instead of adding a new one
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, MyListFragment.newInstance(), FRAGMENT_RESULTSLIST)
                    .commit();
        }
    }

    @Override
    public void onBackStackChanged() {
    }

    @Override
    public GoogleApiClient getGoogleApiClient() {
        return null;
    }

    @Override
    public void startSearch(Type locationType) {
        remove(getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOME));
        getRecordsRequest().setType(locationType);
        RequestUtils.apply(getRecordsRequest());
        App.provide(this).updateLastSeatchRequest(getRecordsRequest());
        refreshList();
        setTitle(getRecordsRequest());
    }

    @Subscribe
    public void onSearchRequestEvent(SearchRequestEvent event) {
        if (event.getOffset() == 0) { // Only for the fresh recordButton
            AnalyticsCalls.get().trackSearchResults(event.getSearchRequest());
        }
    }


    @Subscribe
    public void onMassageEvent(MassageEvent event) {
        switch (event.getId()) {
            case MassageEvent.NEW_RECORD_SUCCESS:
                refreshList();
        }
    }

    public void hideRefreshHotelsButton() {
        mRefreshHotels.setVisibility(View.GONE);
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

    @Override
    public void onEditLocationClick() {

    }


}
