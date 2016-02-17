package com.stg.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.socialtravelguide.api.contract.Language;
import com.stg.app.App;
import com.stg.app.R;
import com.stg.app.analytics.AnalyticsCalls;
import com.stg.app.events.Events;
import com.stg.app.events.NetworkStateChangeEvent;
import com.stg.app.model.HotelListRequest;
import com.stg.app.preferences.UserPreferences;
import com.stg.app.widget.AppBar;
import com.stg.app.widget.IntentIntegrator;
import com.stg.app.widget.NavigationDrawer;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author ortal
 * @date 2015-04-28
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected static final String EXTRA_SNIPPET = "snippet";
    protected static final String EXTRA_ORDER_ITEM = "order_item";
    protected static final String EXTRA_REQUEST = "request";
    protected AppBar mToolbar;
    private Menu mMenu;
    private NavigationDrawer mNavigationDrawer;
    private boolean mWasOffline;
    private HotelListRequest mRequest;

    private BroadcastReceiver mNetworkChangeBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (isOnline()) {
                if (mWasOffline) {
                    mWasOffline = false;
                    Events.post(new NetworkStateChangeEvent(true));
                }
            } else if (!mWasOffline) {
                mWasOffline = true;
                Events.post(new NetworkStateChangeEvent(false));
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateContentView();
        setupDrawer();
        if (savedInstanceState != null) {
            mRequest = savedInstanceState.getParcelable(EXTRA_REQUEST);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mRequest != null) {
            outState.putParcelable(EXTRA_REQUEST, mRequest);
        }
        super.onSaveInstanceState(outState);
    }

    protected abstract void onCreateContentView();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private void setupDrawer() {
        setupToolbar();
        mNavigationDrawer = new NavigationDrawer(this);
    }

    public AppBar getToolbar() {
        return mToolbar;
    }

    public NavigationDrawer getNavigationDrawer() {
        return mNavigationDrawer;
    }

    public boolean isOnline() {
        return App.provide(this).netUtils().isOnline();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyticsCalls.get().pauseCollectingLifecycleData();
        App.provide(this).facebook().pause(this);
        unregisterReceiver(mNetworkChangeBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyticsCalls.get().collectLifecycleData(this);
        App.provide(this).facebook().resume(this);
        mWasOffline = !isOnline();
        registerReceiver(mNetworkChangeBroadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    // Custom fonts support: @see https://github.com/chrisjenx/Calligraphy
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void setupToolbar() {
        mToolbar = (AppBar) findViewById(R.id.app_bar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            final ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onPageChange(int tab, Bundle extras) {
        switch (tab) {
            case NavigationDrawer.NAV_SETTING:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case NavigationDrawer.NAV_RECENT_SEARCH:
                startActivity(RecentSearchesActivity.createIntent(this));
                break;
            case NavigationDrawer.NAV_FAVORITES:
                startActivity(FavoritesActivity.createIntent(this));
                break;
            case NavigationDrawer.NAV_SCANNER:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                //start scanning
                scanIntegrator.initiateScan();
                break;
            case NavigationDrawer.NAV_NEW_RECORD:
                startActivity(NewRecord.createIntent(this));
                break;
            default:
        }
    }

    public UserPreferences getUserPrefs() {
        return App.provide(this).getUserPrefs();
    }

    public HotelListRequest getHotelsRequest() {
        if (mRequest == null) {
            if (getIntent().hasExtra(EXTRA_REQUEST)) {
                mRequest = getIntent().getParcelableExtra(EXTRA_REQUEST);
            }
            if (requiresRequest() && mRequest == null) {
                throw new IllegalStateException("Request is not available, override requiresRequest to disable the check");
            }
            if (mRequest != null) {
                UserPreferences prefs = getUserPrefs();
                mRequest.setCurrency(prefs.getCurrencyCode());
                mRequest.setLanguage(Language.getSupported(prefs.getLang()));
            }
        }
        return mRequest;
    }

    public void setHotelsRequest(HotelListRequest request) {
        mRequest = request;
    }

    protected boolean requiresRequest() {
        return true;
    }

    public String getCurrencyCode() {
        return getUserPrefs().getCurrencyCode();
    }

    public void remove(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
        getSupportFragmentManager()
                .popBackStack();
    }

    public Menu getMenu() {
        return mMenu;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
