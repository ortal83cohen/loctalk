package com.travoca.app.fragment;

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
import com.travoca.app.events.Events;
import com.travoca.app.events.UserLogOutEvent;
import com.travoca.app.events.UserLoginEvent;
import com.travoca.app.member.MemberStorage;
import com.travoca.app.member.model.AccessToken;
import com.travoca.app.member.model.User;
import com.travoca.app.travocaapi.RetrofitCallback;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Response;

/**
 * @author ortal
 * @date 2015-05-17
 */
public class LoginFragment extends BaseFragment {

    AccessTokenTracker accessTokenTracker;

    @Bind(R.id.login_button)
    LoginButton facebookButton;

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
        final MemberStorage memberStorage = App.provide(getActivity()).memberStorage();
        facebookButton.setReadPermissions("public_profile");
        facebookButton.setReadPermissions("email");
        // If using in a fragment
        facebookButton.setFragment(this);
        // Other app specific specialization
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(com.facebook.AccessToken accessToken,
                    com.facebook.AccessToken accessToken1) {
                if (accessToken1 == null) {
                    memberStorage.clear();
                    Events.post(new UserLogOutEvent());
                }
            }
        };
        // Callback registration
        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                memberStorage.saveAccessToken(new AccessToken(loginResult.getAccessToken()));
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
                                    TravocaApi travocaApi = TravocaApplication
                                            .provide(getActivity()).travocaApi();
                                    travocaApi.saveUser(id, email, imageUrl, firstName, lastName)
                                            .enqueue(mResultsCallback);
                                    User user = new User(email, firstName, lastName, id, imageUrl);
                                    Events.post(new UserLoginEvent(user));
                                    memberStorage.saveUser(user);
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
                facebookButton.setReadPermissions("email");
            }

            @Override
            public void onError(FacebookException exception) {
                facebookButton.setReadPermissions("email");
            }
        });

        return view;
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
        setHasOptionsMenu(false);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

}
