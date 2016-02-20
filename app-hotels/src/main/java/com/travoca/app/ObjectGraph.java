package com.travoca.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

import com.facebook.CallbackManager;
import com.facebook.device.yearclass.YearClass;
import com.travoca.api.StgApi;
import com.travoca.api.StgApiConfig;
import com.travoca.api.mock.ResultsMockClient;
import com.travoca.api.model.SearchRequest;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.travoca.app.analytics.Facebook;
import com.travoca.app.etbapi.CacheRequestInterceptor;
import com.travoca.app.etbapi.CacheResponseInterceptor;
import com.travoca.app.etbapi.RetrofitLogger;
import com.travoca.app.etbapi.UserAgentInterceptor;
import com.travoca.app.member.MemberStorage;
import com.travoca.app.model.RecordListRequest;
import com.travoca.app.preferences.UserPreferences;
import com.travoca.app.preferences.UserPreferencesStorage;
import com.travoca.app.utils.DefaultHttpClient;
import com.travoca.app.utils.NetworkUtilities;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.concurrent.TimeUnit;

/**
 * @author ortal
 * @date 2015-04-07
 */
public class ObjectGraph {


    private static final long DISK_CACHE_SIZE = 5000000; // 5mbX
    private static final long CONNECT_TIMEOUT_MILLIS = 30000;
    private static final long READ_TIMEOUT_MILLIS = 40000;
    protected final Context app;
    private RecordListRequest mHotelsRequest;
    private Facebook mFacebook;
    private UserPreferences mUserPrefs;
    private StgApi mStgApi;
    private OkHttpClient mHttpClient;
    private MemberStorage mMemberStorage;
    private SearchRequest mLastSearchRequest;


    public ObjectGraph(Context applicationContext) {
        this.app = applicationContext;
    }

    public SearchRequest getLastSearchRequest() {
        return mLastSearchRequest;
    }

    public void updateLastSeatchRequest(SearchRequest request) {
        if (mLastSearchRequest == null) {
            mLastSearchRequest = new SearchRequest();
        }
        mLastSearchRequest.setType(request.getType());
        mLastSearchRequest.setNumberOfPersons(request.getNumberOfPersons());
        mLastSearchRequest.setNumbersOfRooms(request.getNumberOfRooms());
    }

    public RecordListRequest createHotelsRequest() {
        RecordListRequest request = new RecordListRequest();
        UserPreferences userPrefs = getUserPrefs();
        request.setLanguage(userPrefs.getLang());
        request.setCurrency(userPrefs.getCurrencyCode());
        request.setCustomerCountryCode(userPrefs.getCountryCode());
        return request;
    }

    public StgApi etbApi() {
        if (mStgApi == null) {
            StgApiConfig cfg = new StgApiConfig(Config.ETB_API_KEY, Config.ETB_API_CAMPAIGN_ID);
            cfg.setDebug(BuildConfig.DEBUG);
            cfg.setLogger(new RetrofitLogger());
            mStgApi = new StgApi(cfg, apiHttpClient());
        }
        return mStgApi;
    }

    private OkHttpClient apiHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient(this.app);
            File directory = new File(this.app.getCacheDir(), "responses");

            mHttpClient.setCache(new Cache(directory, DISK_CACHE_SIZE));
            mHttpClient.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            mHttpClient.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

            mHttpClient.networkInterceptors().add(new CacheResponseInterceptor());
            mHttpClient.networkInterceptors().add(new UserAgentInterceptor(this.app));
            mHttpClient.interceptors().add(new CacheRequestInterceptor(new NetworkUtilities(connectivityManager())));
            mHttpClient.interceptors().add(RetrofitLogger.create());
            mHttpClient.interceptors().add(new ResultsMockClient());
        }
        return mHttpClient;
    }

    public UserPreferences getUserPrefs() {
        if (mUserPrefs == null) {
            UserPreferencesStorage storage = new UserPreferencesStorage(this.app);
            mUserPrefs = storage.load();
        }
        return mUserPrefs;
    }

    public TelephonyManager getTelephonyManager() {
        return (TelephonyManager) app.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public CallbackManager facebookCallbackManager() {
        return CallbackManager.Factory.create();
    }

    public Facebook facebook() {
        if (mFacebook == null) {
            mFacebook = new Facebook(app);
        }
        return mFacebook;
    }

    public ConnectivityManager connectivityManager() {
        return (ConnectivityManager) this.app.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public NetworkUtilities netUtils() {
        return new NetworkUtilities(connectivityManager());
    }

    public MemberStorage memberStorage() {
        if (mMemberStorage == null) {
            mMemberStorage = new MemberStorage(this.app);
        }
        return mMemberStorage;
    }

    public int getDeviceClass() {
        return YearClass.get(this.app);
    }


    public NumberFormat getNumberFormatter(String currencyCode) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(0);
        if (formatter instanceof DecimalFormat) {
            formatter.setCurrency(Currency.getInstance(currencyCode));
        }
        return formatter;
    }

}
