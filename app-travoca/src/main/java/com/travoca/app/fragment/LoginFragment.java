package com.travoca.app.fragment;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.okhttp.ResponseBody;
import com.travoca.api.TravocaApi;
import com.travoca.api.model.ResultsResponse;
import com.travoca.app.App;
import com.travoca.app.R;
import com.travoca.app.TravocaApplication;
import com.travoca.app.activity.LoginActivity;
import com.travoca.app.events.Events;
import com.travoca.app.events.UserLogOutEvent;
import com.travoca.app.events.UserLoginEvent;
import com.travoca.app.member.MemberStorage;
import com.travoca.app.member.model.AccessToken;
import com.travoca.app.member.model.FacebookUser;
import com.travoca.app.member.model.GoogleUser;
import com.travoca.app.member.model.User;
import com.travoca.app.travocaapi.RetrofitCallback;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Response;

/**
 * @author ortal
 * @date 2015-05-17
 */
public class LoginFragment extends BaseFragment
        implements View.OnClickListener {

    AccessTokenTracker accessTokenTracker;

    @Bind(R.id.login_button)
    LoginButton mFacebookButton;

    @Bind(R.id.sign_in_button)
    SignInButton mSignInButton;

    @Bind(R.id.button_sign_out)
    Button mSignOutButton;

    CallbackManager callbackManager;

    private RetrofitCallback<ResultsResponse> mResultsCallback
            = new RetrofitCallback<ResultsResponse>() {

        @Override
        protected void failure(ResponseBody errorBody, boolean isOffline) {

        }

        @Override
        public void success(ResultsResponse apiResponse, Response response) {

        }
    };

    private TravocaApi mTravocaApi;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        mTravocaApi = TravocaApplication.provide(getActivity()).travocaApi();
        mMemberStorage = App.provide(getActivity()).memberStorage();
        mFacebookButton.setReadPermissions("public_profile");
        mFacebookButton.setReadPermissions("email");
        // If using in a fragment
        mFacebookButton.setFragment(this);
        // Other app specific specialization
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(com.facebook.AccessToken accessToken,
                    com.facebook.AccessToken accessToken1) {
                if (accessToken1 == null) {
                    mMemberStorage.clear();
                    Events.post(new UserLogOutEvent());
                    updateUI(null);
                }
            }
        };
        // Callback registration
        mFacebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mMemberStorage.saveAccessToken(new AccessToken(loginResult.getAccessToken()));
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // Application code
                                try {
                                    String email = object.getString("email");
                                    String[] splitName = object.getString("name").split(" ");
                                    String firstName = splitName[0];
                                    String lastName = splitName[1];
                                    String id = object.getString("id");
                                    String imageUrl = "https://graph.facebook.com/" + id
                                            + "/picture?type=large";

                                    mTravocaApi.saveUser(id, email, imageUrl, firstName, lastName)
                                            .enqueue(mResultsCallback);
                                    User user = new FacebookUser(email, firstName, lastName, id,
                                            imageUrl);
                                    Events.post(new UserLoginEvent(user));
                                    mMemberStorage.saveUser(user);
                                    updateUI(user);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                mFacebookButton.setReadPermissions("email");
            }

            @Override
            public void onError(FacebookException exception) {
                mFacebookButton.setReadPermissions("email");
            }
        });

        mSignOutButton.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
        mSignInButton.setSize(SignInButton.SIZE_WIDE);
        mSignInButton.setScopes(((LoginActivity) getActivity()).getGso().getScopeArray());
        User user = mMemberStorage.loadUser();
        updateUI(user);
        return view;
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


    private void updateUI(User user) {
        if (user == null) {
            mFacebookButton.setVisibility(View.VISIBLE);
            mSignOutButton.setVisibility(View.GONE);
            mSignInButton.setVisibility(View.VISIBLE);
        } else if (user instanceof GoogleUser) {
            mFacebookButton.setVisibility(View.GONE);
            mSignOutButton.setVisibility(View.VISIBLE);
            mSignInButton.setVisibility(View.GONE);
        } else if (user instanceof FacebookUser) {
            mFacebookButton.setVisibility(View.VISIBLE);
            mSignOutButton.setVisibility(View.GONE);
            mSignInButton.setVisibility(View.GONE);
        }

    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi
                .getSignInIntent(((LoginActivity) getActivity()).getGoogleApiClient());
        startActivityForResult(signInIntent, LoginActivity.RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(((LoginActivity) getActivity()).getGoogleApiClient())
                .setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                mMemberStorage.clear();
                                Events.post(new UserLogOutEvent());
                                updateUI(null);
                            }
                        });
    }

    private MemberStorage mMemberStorage;

    private ConnectionResult mConnectionResult;

    public void handleSignInResult(GoogleSignInResult result) {
        if(result!=null) {
//        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.getStatus().isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();

//            mMemberStorage.saveAccessToken(new AccessToken(loginResult.getAccessToken()));
                String email = acct.getEmail();
                String[] splitName = acct.getDisplayName().split(" ");
                String firstName = splitName[0];
                String lastName = splitName[1];
                String id = acct.getId();
                String imageUrl = "";
                try {
                    imageUrl = acct.getPhotoUrl().toString();
                } catch (NullPointerException ignored) {
                }

                mTravocaApi.saveUser(id, email, imageUrl, firstName, lastName)
                        .enqueue(mResultsCallback);
                User user = new GoogleUser(email, firstName, lastName, id, imageUrl);
                Events.post(new UserLoginEvent(user));
                mMemberStorage.saveUser(user);
                updateUI(user);
            } else {
                updateUI(mMemberStorage.loadUser());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();
        accessTokenTracker.isTracking();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

}
