package com.travoca.app.activity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.travoca.api.TravocaApiConfig;
import com.travoca.api.contract.Language;
import com.travoca.app.App;
import com.travoca.app.BuildConfig;
import com.travoca.app.Config;
import com.travoca.app.R;
import com.travoca.app.preferences.UserPreferences;
import com.travoca.app.preferences.UserPreferencesStorage;
import com.travoca.app.utils.AppLog;

import java.util.ArrayList;

import de.psdev.licensesdialog.LicensesDialog;

/**
 * @author ortal
 * @date 2015-07-02
 */
public class SettingsActivity extends SettingsActionBarActivity {

    private static final int ACTION_API_ENDPOINT = 1;
    private static final int ACTION_LANGUAGE = 3;
    private static final int ACTION_PRIVACY_POLICY = 4;
    private static final int ACTION_TOS = 5;
    private static final int ACTION_LICENSES = 6;
    private static final int ACTION_VERSION = 7;
    private static final int ACTION_NOTIFICATION = 8;
    private int mVersionPress = 0;

    @Override
    protected void init() {

    }

    @Override
    protected ArrayList<SettingsActionBarActivity.Preference> initPreferenceItems() {
        ArrayList<Preference> preferences = new ArrayList<>();

        UserPreferences up = App.provide(this).getUserPrefs();
        String languageCode = up.getLanguage();

        preferences.add(new Item(R.string.language, languageCode, ACTION_LANGUAGE));
        preferences.add(new SwitchItem(R.string.allow_notification, true, ACTION_NOTIFICATION));

        preferences.add(new Category(R.string.settings_about));
        preferences.add(new Item(R.string.terms_of_service, ACTION_TOS));
        preferences.add(new Item(R.string.privacy_policy, ACTION_PRIVACY_POLICY));
        preferences.add(new Item(R.string.version, renderVersion(), ACTION_VERSION));
        preferences.add(new Item(R.string.open_source_licenses, ACTION_LICENSES));


        if (BuildConfig.DEBUG) {
            preferences.addAll(createDevelopmentPreferences());
        }

        return preferences;
    }

    private ArrayList<Preference> createDevelopmentPreferences() {
        ArrayList<Preference> preferences = new ArrayList<>();
        preferences.add(new Category("Development"));
        String endpoint = App.provide(this).travocaApi().getConfig().getEndpoint();
        preferences.add(new Item("API Endpoint", endpoint, ACTION_API_ENDPOINT));
        return preferences;
    }

    @Override
    protected void onPreferenceItemClick(int action, final Item pref) {
        if (action == ACTION_API_ENDPOINT) {
            showEndpointDialog(pref);
        } else if (action == ACTION_LANGUAGE) {
            showLanguageDialog(pref);
        } else if (action == ACTION_PRIVACY_POLICY) {
            showWebViewDialog(R.string.privacy_policy, "http://www.travoca.com/en/privacy/");
        } else if (action == ACTION_TOS) {
            showWebViewDialog(R.string.terms_of_service, "http://www.travoca.com/en/disclaimer/");
        } else if (action == ACTION_LICENSES) {
            new LicensesDialog.Builder(this)
                    .setNotices(R.raw.notices)
                    .build()
                    .show();
        }
    }

    private void saveUserPrefs(UserPreferences userPrefs) {
        UserPreferencesStorage storage = new UserPreferencesStorage(this);
        storage.save(userPrefs);
    }

    private void showWebViewDialog(int titleRes, String url) {
        WebView webView = new WebView(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        webView.loadUrl(url);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(webView);
        dialog.setTitle(titleRes);
        dialog.setPositiveButton(android.R.string.ok, null);
        dialog.show();
    }

    private void showLanguageDialog(final Item pref) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        Locale[] locales = Locale.getAvailableLocales();
//        final CharSequence[] items = new CharSequence[locales.length];
//        for (int i = 0; i < locales.length; i++) {
//            if (!locales[i].getDisplayLanguage().isEmpty()  ) {
//                items[i] = locales[i].getDisplayLanguage();
//            }
//        }
        final CharSequence[] items = new CharSequence[1];
        items[0] = Language.getDefault();
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String locale = (String) items[which];
                UserPreferences userPrefs = App.provide(SettingsActivity.this).getUserPrefs();
                userPrefs.setLanguageCode(locale);
                pref.summary = locale;
                saveUserPrefs(userPrefs);
                notifyDataSetChanged();
            }
        });

        builder.setTitle(R.string.select_language);
        builder.create().show();
    }

    private void showEndpointDialog(final Item pref) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] items = new CharSequence[]{
//                "production", "mock","trunk", "alex", "ortal"
                "production", "mock", "trunk"
        };
        final CharSequence[] api = new CharSequence[]{
//                "api.travoca.com","mock", "trunk.api.travoca.us", "api-alex.il.travoca.us", "api.travoca.com"
                "http://maorbolo.com", "mock", "trunk.api.travoca.us"
        };
        final CharSequence[] core = new CharSequence[]{
//                "www.travoca.com","mock", "trunk-site.travoca.us", "devsite-alex.il.travoca.us", "devsite-ortal.il.travoca.us"
                "http://maorbolo.com", "mock", "trunk-site.travoca.us"
        };
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TravocaApiConfig cfg = App.provide(SettingsActivity.this).travocaApi().getConfig();
                if (which == 0) {
                    cfg.setEndpoint(TravocaApiConfig.TRAVOCA_API_ENDPOINT_DEFAULT);
                    Config.setCoreInterfaceEndpoint(Config.CORE_INTERFACE_ENDPOINT);
                    Config.setCoreInterfaceSecureEndpoint(Config.CORE_INTERFACE_SECURE_ENDPOINT);
                    Config.setProductionEnv(true);
                    pref.summary = "production";
                } else {
                    cfg.setEndpoint("http://" + api[which]);
                    Config.setCoreInterfaceEndpoint("http://" + core[which]);
                    Config.setCoreInterfaceSecureEndpoint("https://" + core[which]);
                    Config.setProductionEnv(false);
                    pref.summary = (String) items[which];
                }

                notifyDataSetChanged();
            }
        });

        builder.setTitle("API Endpoint");
        builder.create().show();

    }

    private String renderVersion() {
        String versionName = "";
        try {
            versionName = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            AppLog.w(e.getMessage());
        }
        return versionName;
    }

    @Override
    protected void onCreateContentView() {
        setContentView(R.layout.activity_settings);
    }
}
