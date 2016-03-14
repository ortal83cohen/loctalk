package com.travoca.app.activity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

import com.travoca.app.R;
import com.travoca.app.fragment.LoginFragment;
import com.travoca.app.widget.AppBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author user
 * @date 2016-02-17
 */
public class LoginActivity extends BaseActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    private static final String FRAGMENT_LOGIN = "login";

    public static final int RC_SIGN_IN = 230;

    protected GoogleApiClient mGoogleApiClient;

    private GoogleSignInOptions gso;


    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        setTitle("Login");

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment.newInstance(),
                            FRAGMENT_LOGIN)
                    .commit();
        }

    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public GoogleSignInOptions getGso() {
        return gso;
    }

    @Override
    protected void onCreateContentView() {
        setContentView(R.layout.activity_login);
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            LoginFragment fragment = (LoginFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_LOGIN);
            fragment.handleSignInResult(result);
        }
        else if(resultCode == -1){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            LoginFragment fragment = (LoginFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_LOGIN);
            fragment.handleSignInResult(result);
        }
    }


}