package com.travoca.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.squareup.okhttp.ResponseBody;
import com.travoca.api.TravocaApi;
import com.travoca.api.model.ResultsResponse;
import com.travoca.app.App;
import com.travoca.app.R;
import com.travoca.app.TravocaApplication;
import com.travoca.app.events.Events;
import com.travoca.app.events.UserLogOutEvent;
import com.travoca.app.events.UserLoginEvent;
import com.travoca.app.fragment.LoginFragment;
import com.travoca.app.member.MemberStorage;
import com.travoca.app.member.model.AccessToken;
import com.travoca.app.member.model.User;
import com.travoca.app.travocaapi.RetrofitCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Response;

/**
 * @author user
 * @date 2016-02-17
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
    @Bind(R.id.sign_in_button)
    SignInButton btnSignIn;
    @Bind(R.id.button_sign_out)
    Button btnSignOut;
    private MemberStorage memberStorage;
    private static final int RC_SIGN_IN = 0;
    private ConnectionResult mConnectionResult;
    protected GoogleApiClient mGoogleApiClient;
    private static final String FRAGMENT_LOGIN = "login";


    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,  this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

         memberStorage = App.provide(this).memberStorage();
        btnSignOut.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment.newInstance(),
                            FRAGMENT_LOGIN)
                    .commit();
        }

    }

    @Override
    protected void onCreateContentView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.button_sign_out:
                signOut();
                break;
        }
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        memberStorage.clear();
                        Events.post(new UserLogOutEvent());
                    }
                });
    }

    private RetrofitCallback<ResultsResponse> mResultsCallback = new RetrofitCallback<ResultsResponse>() {

        @Override
        protected void failure(ResponseBody errorBody, boolean isOffline) {

        }

        @Override
        public void success(ResultsResponse apiResponse, Response response) {

        }};
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
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

//            if (mSignInClicked) {
//                // The user has already clicked 'sign-in' so we attempt to
//                // resolve all
//                // errors until the user is signed in, or they cancel.
//                resolveSignInError();
//            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
//        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.getStatus().isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

//            memberStorage.saveAccessToken(new AccessToken(loginResult.getAccessToken()));
            String email = acct.getEmail();
            String[] splitName = acct.getDisplayName().split(" ");
            String firstName = splitName[0];
            String lastName = splitName[1];
            String id =  acct.getId();
            String imageUrl = acct.getPhotoUrl().toString();
            TravocaApi travocaApi = TravocaApplication.provide(this).travocaApi();
            travocaApi.saveUser(id,email,imageUrl,firstName,lastName).enqueue(mResultsCallback);
            User user = new User(email, firstName,lastName, id,imageUrl);
            Events.post(new UserLoginEvent(user));
            memberStorage.saveUser(user);
            updateUI(true);
        } else {
            updateUI(false);
        }
    }

    /**
     * Updating the UI, showing/hiding buttons and profile layout
     */
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
//            llProfileLayout.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
//            llProfileLayout.setVisibility(View.GONE);
        }
    }

}