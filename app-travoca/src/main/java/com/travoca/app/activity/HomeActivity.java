package com.travoca.app.activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import com.crashlytics.android.answers.Answers;
import com.facebook.appevents.AppEventsLogger;
import com.squareup.okhttp.ResponseBody;
import com.travoca.api.model.SearchRequest;
import com.travoca.api.model.search.Type;
import com.travoca.api.utils.RequestUtils;
import com.travoca.app.App;
import com.travoca.app.R;
import com.travoca.app.analytics.AnalyticsCalls;
import com.travoca.app.anim.BlurAnimation;
import com.travoca.app.core.CoreInterface;
import com.travoca.app.fragment.HomeFragment;
import com.travoca.app.model.RecordListRequest;
import com.travoca.app.preferences.UserPreferences;
import com.travoca.app.service.LocalRecordsJobService;
import com.travoca.app.travocaapi.RetrofitCallback;
import com.travoca.app.widget.IntentIntegrator;
import com.travoca.app.widget.IntentResult;

import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import retrofit.Response;

//import com.newrelic.agent.android.NewRelic;

public class HomeActivity extends BaseActivity
        implements HomeFragment.Listener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    public static final int REQUEST_CHECK_SETTINGS = 1;

    private static final String FRAGMENT_HOME = "loc_chooser";

    private static final int NOTIFICATION_ID = 0;

    private static final int JOB_ID = 1;

    protected GoogleApiClient mGoogleApiClient;

    private CoreInterface.Service mCoreInterface;

    private RetrofitCallback<String> mResultsCallback = new RetrofitCallback<String>() {
        @Override
        protected void failure(ResponseBody response, boolean isOffline) {
            String number = "tel:+972 52 6088707";
            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
            startActivity(callIntent);
        }

        @Override
        protected void success(String phoneNumber, Response<String> response) {
            String number = "tel:" + phoneNumber;
            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
            startActivity(callIntent);
        }

    };

    public static Intent createIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected boolean requiresRequest() {
        return false;
    }

    @Override
    public Uri getReferrer() {
        String referrer = getIntent().getStringExtra("android.intent.extra.REFERRER_NAME");
        if (referrer != null) {
            try {
                return Uri.parse(referrer);
            } catch (ParseException ignored) {
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        AnalyticsCalls.get().register(getApplicationContext());
        Fabric.with(this, new Answers());
        App.provide(this).facebook().initialize();
        mCoreInterface = CoreInterface.create(getApplicationContext());

        if (getRecordsRequest() == null) {
            setRecordsRequest(App.provide(this).createRequest());
        }

        mToolbar.showLogo();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        if (savedInstanceState == null) {
            RecordListRequest request = getRecordsRequest();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment.newInstance(request),
                            FRAGMENT_HOME)
                    .commit();
        }
        animateBackground();
        initGob();
        AnalyticsCalls.get().trackLanding();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
        updateSearchBox();
    }

    private void updateSearchBox() {
        SearchRequest lastSearchRequest = App.provide(this).getLastSearchRequest();
        if (lastSearchRequest != null) {
            RecordListRequest request = getRecordsRequest();
            RequestUtils.apply(request);
            request.setType(lastSearchRequest.getType());
            HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_HOME);
            if (homeFragment != null) {
                homeFragment.init(request);
            }
        }
    }

    @Override
    protected void onCreateContentView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_customer:
                UserPreferences userPrefs = getUserPrefs();
                mCoreInterface.customerServicePhone(userPrefs.getCountryCode().toLowerCase())
                        .enqueue(mResultsCallback);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void animateBackground() {
        final View content = getWindow().getDecorView();
        content.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        final Drawable background = getResources()
                                .getDrawable(R.drawable.img_background);
                        ValueAnimator blurAnimation = new BlurAnimation()
                                .blur(content, background, 4f);
                        blurAnimation.setStartDelay(100);
                        blurAnimation.start();
                        content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_HOME);
        if (homeFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                getString(R.string.could_not_connect_to_google) + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    public void startSearch(Type locationType) {
        RecordListRequest request = getRecordsRequest();

        request.setType(locationType);
        RequestUtils.apply(request);
        startActivity(RecordListActivity
                .createIntent(request, this));//// TODO: 3/3/2016  move to base activity
    }

    @Override
    public void onConnected(Bundle bundle) {

    }
    void initGob(){
        JobScheduler jobScheduler = JobScheduler.getInstance(this);

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,
                new ComponentName(this, LocalRecordsJobService.class));
        builder
                .setOverrideDeadline(3600000 * 24)// max in hours
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//                .setPeriodic(10000)
                .setPersisted(true);

        jobScheduler.schedule(builder.build());
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setTitle("");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                if (resultCode == RESULT_OK) {
                    HomeFragment fragment = (HomeFragment) getSupportFragmentManager()
                            .findFragmentByTag(FRAGMENT_HOME);
                    if (fragment != null) {
                        fragment.onLocationAvailable();
                    }
                }
                break;
            case IntentIntegrator.REQUEST_CODE:
                IntentResult scanningResult = IntentIntegrator
                        .parseActivityResult(requestCode, resultCode, data);
                //check we have a valid result
                if (scanningResult != null) {
                    //get content from Intent Result
                    String scanContent = scanningResult.getContents();
                    //get format name of data scanned
                    String scanFormat = scanningResult.getFormatName();
                    //output to UI
                    if (scanFormat != null) {
                        if (scanFormat.equals("QR_CODE")) {
                            Intent intent = new Intent(this, RouteActivity.class);
                            intent.setData(Uri.parse(scanContent));
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, R.string.qr_not_match_warning, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
        }
    }


}
