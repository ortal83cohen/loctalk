package com.travoca.app.fragment;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.facebook.device.yearclass.YearClass;
import com.travoca.api.model.SearchRequest;
import com.travoca.api.model.search.Type;
import com.travoca.app.App;
import com.travoca.app.R;
import com.travoca.app.activity.BaseActivity;
import com.travoca.app.activity.HomeActivity;
import com.travoca.app.adapter.PlaceAutocompleteAdapter;
import com.travoca.app.anim.AnimatorCollection;
import com.travoca.app.anim.ResizeAnimator;
import com.travoca.app.anim.RevealAnimatorCompat;
import com.travoca.app.model.CurrentLocation;
import com.travoca.app.model.Location;
import com.travoca.app.model.LocationWithTitle;
import com.travoca.app.model.MapSelectedViewPort;
import com.travoca.app.model.RecordListRequest;
import com.travoca.app.model.ViewPort;
import com.travoca.app.provider.SearchHistory;
import com.travoca.app.randerscript.BlurBuilder;
import com.travoca.app.service.LocalRecordsJobService;
import com.travoca.app.service.LocalRecordsRequest;
import com.travoca.app.utils.AppLog;
import com.travoca.app.utils.TextWatcherAdapter;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

/**
 * @author ortal
 * @date 2015-05-17
 */
public class HomeFragment extends BaseFragment
        implements View.OnTouchListener, View.OnClickListener {

    private static final LatLngBounds BOUNDS_MAP = new LatLngBounds.Builder()
            .include(new LatLng(85, -180)) // top left corner of map
            .include(new LatLng(-85, 180))  // bottom right corner
            .build();


    private static final int REQUEST_PERMISSION_LOCATION = 2;

    private static final String LAST_LOCATION = "last_location";

    private static final String LAST_VIEW_PORT = "last_view_port";

    private static final String ARG_REQUEST = "request";

    private static final int POSITION_UNTOUCHED = -1;

    private static final int POSITION_UNSELECTED = -2;

    private final int[] mTouchLoc = new int[2];

    private final int[] mViewLoc = new int[2];

    @Bind(R.id.autoCompleteTextView_location)
    AutoCompleteTextView mAutocompleteView;

    @Bind(R.id.autoCompleteTextView_clear)
    ImageButton mAutocompleteViewClear;

    @Bind(R.id.instructions)
    TextView mInstructions;

    @Bind(R.id.top_box)
    LinearLayout mTopBox;

    @Bind(R.id.background)
    View mBackground;

    @Bind(R.id.group_buttons_holder)
    LinearLayout mGroupButtonsHolder;

    @Bind(R.id.search)
    Button mSearchRecordsButton;

    private PlaceAutocompleteAdapter mAdapter;

    private int mSelectedPosition = POSITION_UNTOUCHED;

    private GoogleApiClient mGoogleApiClient;

    private Listener mListener;

    private Type mLastLocation;

    private LocationRequest mLocationRequest = new LocationRequest()
            .setPriority(
                    LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY) // (~100m "block" accuracy)
            .setNumUpdates(1);

    private boolean mShowLocation = true;

    private boolean mIsLightBox = false;

    private int mTopBoxHeight = -1;

    private int mInstructionsHeight = -1;

    private View mSelectPanelView;

    private View mBlurContentView;

    private BlurBuilder mBlurBuilder;

    private boolean mAutocompleteOnFocus = false;

    private boolean mSearchButtonClicked = false;

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                places.release();
                return;
            }
            if (getActivity() == null) {
                return;
            }
            // Get the Place object from the buffer.
            if (places.getCount() == 0) {
                Toast.makeText(getActivity(), "No result for " + mAutocompleteView.getText(),
                        Toast.LENGTH_SHORT).show();
                AppLog.e("Google Autocomplete Api error for: " + mAutocompleteView.getText());
                return;
            }
            Place place = places.get(0);
            SearchHistory.insert(place, getActivity());
            Type lastLocation = updateLastLocation(place.getName().toString(), place.getLatLng(),
                    place.getViewport());
            startSearch(lastLocation);
            places.release();
        }
    };

    private TextWatcher mAutocompleteTextWatcher = new TextWatcherAdapter() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mAutocompleteView
                    .setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.destination_active,
                            0, R.color.white, 0);
            mSelectedPosition = POSITION_UNSELECTED;
        }
    };

    public static HomeFragment newInstance(RecordListRequest request) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_REQUEST, request);
        fragment.setArguments(args);
        return fragment;
    }

    private Type updateLastLocation(String title, LatLng loc, LatLngBounds viewport) {
        if (viewport == null) {
            mLastLocation = new Location(title, loc);
        } else {
            mLastLocation = new ViewPort(title, viewport);
        }

        return mLastLocation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final Activity activity = getActivity();
        ButterKnife.bind(this, view);
        setupAutocomplete();
        if (savedInstanceState == null) {
            mGroupButtonsHolder
                    .setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in));
            mGroupButtonsHolder.animate();
        }

        if (mIsLightBox) {
            setupLightBoxBackground();
        } else {
            mInstructions.setVisibility(View.VISIBLE);
        }
        mBackground.setOnTouchListener(this);

        Resources r = getResources();

        ViewCompat.setElevation(mAutocompleteView, r.getDimension(R.dimen.home_view_elevation));
        ViewCompat.setElevation(mAutocompleteViewClear, 50);

        SearchRequest request = getArguments().getParcelable(ARG_REQUEST);
        init(request);

        return view;
    }

    public void init(SearchRequest request) {
//        updateViews(mSelectedRange, request.getNumberOfPersons(), request.getNumberOfRooms());
        mLastLocation = request.getType();
        updateLocationText(mLastLocation);
    }

    private void setupAutocomplete() {
        mAutocompleteView.setThreshold(0);
        mAutocompleteViewClear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mAutocompleteView.setText("");
                return true;
            }
        });

        mAutocompleteView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!mAutocompleteOnFocus) {
                    animateFocusAutocomplete();
                }
                return false;
            }
        });
        mAutocompleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setupHistory(mAutocompleteView.getText());
                mAutocompleteView.showDropDown();
            }
        });
        mAutocompleteView.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (s.length() == 0) {
                    mAutocompleteViewClear.setVisibility(View.GONE);
                } else {
                    mAutocompleteViewClear.setVisibility(View.VISIBLE);
                }
            }
        });
        mAutocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedPosition = position;
                PlaceAutocompleteAdapter.PlaceItem item = mAdapter.getItem(position);
                if (item instanceof PlaceAutocompleteAdapter.PlaceCurrentLocation) {
                    mAutocompleteView.setText("");
                } else if (item instanceof PlaceAutocompleteAdapter.PlaceAutocomplete) {
                } else if (item instanceof PlaceAutocompleteAdapter.PlaceHistory) {
                    PlaceAutocompleteAdapter.PlaceHistory historyItem
                            = (PlaceAutocompleteAdapter.PlaceHistory) item;
//                    updateViews(mSelectedRange, historyItem.getNumberGuests(), historyItem.getNumberRooms());
                }
                mAutocompleteView
                        .setCompoundDrawablesRelativeWithIntrinsicBounds(item.getDrawable(), 0,
                                R.color.white, 0);
                hideKeyboard();
            }
        });
        mAutocompleteView.addTextChangedListener(mAutocompleteTextWatcher);
    }

    private void setupLightBoxBackground() {

        int deviceClass = App.provide(getActivity()).getDeviceClass();
        if (deviceClass >= YearClass.CLASS_2014) {
            mBlurBuilder = new BlurBuilder(getActivity());
            mBlurContentView = getActivity().findViewById(R.id.blur_view);
        }

        Resources r = getResources();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins((int) r.getDimension(R.dimen.default_padding),
                (int) r.getDimension(R.dimen.top_margin_home_fragment),
                (int) r.getDimension(R.dimen.default_padding), 0);
        mGroupButtonsHolder.setLayoutParams(lp);
        mInstructions.setVisibility(View.GONE);
        redrawBlurBackground();
    }

    @Override
    public void onDestroyView() {
        // delete references to activity
        if (mBlurContentView != null) {
            mBlurBuilder = null;
            mBlurContentView = null;
        }
        super.onDestroyView();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mBackground == v) {
            if (mIsLightBox) {
                ((BaseActivity) getActivity()).remove(this);
            } else {
                onBackPressed();
            }
            return true;
        }
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                v.getLocationOnScreen(mTouchLoc);
                mTouchLoc[0] = mTouchLoc[0] + (int) event.getX();
                mTouchLoc[1] = mTouchLoc[1] + (int) event.getY();
                return false;
            default:
        }
        return false;
    }


    public boolean onBackPressed() {
        NavigationFragment navigationDrawer = (NavigationFragment) getActivity()
                .getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        if (navigationDrawer != null && navigationDrawer.isVisible()) {
            navigationDrawer.closeDrawer();
            return true;
        }
        if (mSelectPanelView != null) {
            mSelectPanelView.getLocationOnScreen(mTouchLoc);
            mTouchLoc[0] = mTouchLoc[0] + mSelectPanelView.getWidth() / 2;
            highlightButton(0);
            animatePanelHide(mSelectPanelView);
            return true;
        }
        if (mInstructions.getVisibility() == View.GONE) {
            animateUnFocusAutocomplete();
            return true;
        }
        return false;
    }

    private void redrawBlurBackground() {
        if (mBlurBuilder == null) {
            mBackground.setBackgroundResource(R.color.transparent_black);
        } else {
            Bitmap bgImage = mBlurBuilder.blur(mBlurContentView, R.color.transparent_black);
            mBackground.setBackground(new BitmapDrawable(getResources(), bgImage));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false);
        if (mShowLocation) {
            if (savedInstanceState != null) {
                mLastLocation = savedInstanceState.getParcelable(LAST_LOCATION);
            }


            mGoogleApiClient = mListener.getGoogleApiClient();

            // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
            // the entire world.

            mAdapter = new PlaceAutocompleteAdapter(getActivity(), R.layout.autocomplite_list_item,
                    mGoogleApiClient, BOUNDS_MAP);
            mAutocompleteView.setAdapter(mAdapter);

            if (mLastLocation != null) {
                updateLocationText(mLastLocation);
            } else {
                SearchRequest request = getArguments().getParcelable(ARG_REQUEST);
                updateLocationText(request.getType());
            }

        }
    }

    private void updateLocationText(Type locationType) {
        if (locationType instanceof CurrentLocation) {
            mAutocompleteView.setText("");
        } else if (locationType instanceof MapSelectedViewPort) {
            mAutocompleteView.setText(((MapSelectedViewPort) locationType).getLastSearch());
            if (mSelectedPosition == POSITION_UNTOUCHED) {
                mSelectedPosition = POSITION_UNSELECTED;
            }
        } else if (locationType instanceof LocationWithTitle) {
            String title = ((LocationWithTitle) locationType).getTitle();
            mAutocompleteView.setText(title == null ? "" : title);
            if (mSelectedPosition == POSITION_UNTOUCHED && !TextUtils.isEmpty(title)) {
                mSelectedPosition = POSITION_UNSELECTED;
            }
        }
    }

    private void checkPermissionAndstartSearchInCurrentLocation() {
        if (ActivityCompat
                .checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request missing location permission.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION_LOCATION);
        } else {
            startSearchInCurrentLocation();
        }
    }

    public void onLocationAvailable() {
        android.location.Location location = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if (location!=null) {
            CurrentLocation lastLocation = new CurrentLocation();
            lastLocation.setLatLng(location.getLatitude(), location.getLongitude());
            mLastLocation = lastLocation;
            startSearch(lastLocation);
        }else {
            Toast.makeText(getActivity(),"Location not available",Toast.LENGTH_LONG).show();
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, new LocationListener() {
                        @Override
                        public void onLocationChanged(android.location.Location location) {
                            CurrentLocation lastLocation = new CurrentLocation();
                            lastLocation.setLatLng(location.getLatitude(), location.getLongitude());
                            mLastLocation = lastLocation;
                            startSearch(lastLocation);
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSearchInCurrentLocation();
            }
        }
    }


    private void highlightButton(@IdRes int buttonId) {

    }

    private void animatePanelShow(final View panelView) {
        AnimatorCollection collection = new AnimatorCollection();
        if (mTopBoxHeight == -1) {
            mTopBoxHeight = mTopBox.getMeasuredHeight();
            mInstructionsHeight = mInstructions.getMeasuredHeight();
        }
        collection.add(ResizeAnimator.height(mTopBox.getMeasuredHeight(), 0, mTopBox, 300));
        collection.add(revealAnimatorShow(panelView));
        AnimatorSet set = collection.sequential();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mTopBox.setVisibility(View.GONE);
            }
        });
        set.start();
        mSelectPanelView = panelView;
    }

    private void animatePanelHide(final View panelView) {
        if (mInstructions.getVisibility() == View.GONE) {
            animateUnFocusAutocomplete();
        }
        mTopBox.setVisibility(View.VISIBLE);
        AnimatorCollection collection = new AnimatorCollection();
        Animator pickerHide = revealAnimatorHide(panelView);
        collection.add(pickerHide);
        collection.add(ResizeAnimator.height(0, mTopBoxHeight, mTopBox, 300));
        AnimatorSet set = collection.sequential();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mTopBox.setLayoutParams(
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
            }
        });
        set.start();
        mSelectPanelView = null;
    }

    private void animateUnFocusAutocomplete() {
        mAutocompleteOnFocus = false;
        mInstructions.setVisibility(View.VISIBLE);
        AnimatorCollection collection = new AnimatorCollection();
        collection.add(ResizeAnimator.height(0, mInstructionsHeight, mInstructions, 100));
        collection.add(ResizeAnimator
                .margin(0, getResources().getDimensionPixelSize(R.dimen.home_instructions_margin),
                        mInstructions, 100));
        AnimatorSet set = collection.sequential();
        set.start();
    }

    private void animateFocusAutocomplete() {
        mAutocompleteOnFocus = true;
        if (mTopBoxHeight == -1) {
            mTopBoxHeight = mTopBox.getMeasuredHeight();
            mInstructionsHeight = mInstructions.getMeasuredHeight();
        }
        AnimatorCollection collection = new AnimatorCollection();
        collection.add(ResizeAnimator.height(mInstructionsHeight, 0, mInstructions, 100));
        collection.add(ResizeAnimator
                .margin(getResources().getDimensionPixelSize(R.dimen.home_instructions_margin), 0,
                        mInstructions, 100));
        AnimatorSet set = collection.sequential();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mInstructions.setVisibility(View.GONE);
            }
        });
        set.start();
    }

    private Animator revealAnimatorHide(final View view) {
        view.getLocationOnScreen(mViewLoc);
        int x = mTouchLoc[0] - mViewLoc[0];
        int y = mTouchLoc[1] - mViewLoc[1];
        return RevealAnimatorCompat.hide(view, x, y, 0);
    }

    private Animator revealAnimatorShow(final View view) {
        view.getLocationOnScreen(mViewLoc);
        int x = mTouchLoc[0] - mViewLoc[0];
        int y = mTouchLoc[1] - mViewLoc[1];
        return RevealAnimatorCompat.show(view, x, y, 0);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (mLastLocation instanceof Parcelable) {
            outState.putParcelable(LAST_LOCATION, (Parcelable) mLastLocation);
        }
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.search)
    public void onSearchRecordsClick(View button) {

        try {
            Calendar yesterday = Calendar.getInstance(Locale.getDefault());
            yesterday.add(Calendar.DAY_OF_MONTH, -1);
            PlaceAutocompleteAdapter.PlaceItem item = null;
            if (mSelectedPosition == POSITION_UNTOUCHED) {
                checkPermissionAndstartSearchInCurrentLocation();
                return;
            } else if (mSelectedPosition == POSITION_UNSELECTED) {
                item = mAdapter.getFirstSearchItem();
            } else {
                item = mAdapter.getItem(mSelectedPosition);
            }
            if (item == null || item instanceof PlaceAutocompleteAdapter.PlaceCurrentLocation) {
                checkPermissionAndstartSearchInCurrentLocation();
            } else if (item instanceof PlaceAutocompleteAdapter.PlaceAutocomplete) {
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient,
                                ((PlaceAutocompleteAdapter.PlaceAutocomplete) item).getPlaceId());
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            } else if (item instanceof PlaceAutocompleteAdapter.PlaceHistory) {
                PlaceAutocompleteAdapter.PlaceHistory historyItem
                        = (PlaceAutocompleteAdapter.PlaceHistory) item;
                Type locationType = updateLastLocation(item.toString(), historyItem.getLatLng(),
                        historyItem.getLatLngBounds());
                startSearch(locationType);
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
            Toast.makeText(getActivity(), R.string.choose_location, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResume() {
        mSearchButtonClicked = false;
        super.onResume();
    }

    private void startSearchInCurrentLocation() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        onLocationAvailable();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(),
                                    HomeActivity.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        onLocationAvailable();
                        break;
                }
            }
        });
    }

    private void hideKeyboard() {
        if (mInstructions.getVisibility() == View.GONE) {
            animateUnFocusAutocomplete();
        }
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void startSearch(Type locationType) {
        if (!mSearchButtonClicked) {
            Answers.getInstance().logCustom(new CustomEvent("SearchRecordsClick")
                    .putCustomAttribute("Type", locationType.getType().toString())
                    .putCustomAttribute("Context", locationType.getContext()));

            mSearchButtonClicked = true;
            mListener.startSearch(locationType);
        }
    }


    public interface Listener {

        GoogleApiClient getGoogleApiClient();

        void startSearch(Type locationType);
    }


}
