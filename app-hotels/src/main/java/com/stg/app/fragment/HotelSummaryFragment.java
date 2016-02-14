package com.stg.app.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;

import com.socialtravelguide.api.model.DetailsResponse;
import com.socialtravelguide.api.model.Record;
import com.socialtravelguide.api.utils.RequestUtils;
import com.stg.app.R;
import com.stg.app.activity.HotelDetailsActivity;
import com.stg.app.etbapi.AvailabilityDetailsCallback;
import com.stg.app.events.Events;
import com.stg.app.events.HotelDetailsResultsEvent;
import com.stg.app.hoteldetails.RecordViewHolder;
import com.stg.app.model.HotelListRequest;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.ResponseBody;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;


public class HotelSummaryFragment extends BaseFragment implements View.OnClickListener, OnMapReadyCallback, OnMapClickListener, OnMarkerClickListener {
    private static final int IMAGE_STATE_EXPANDED = 1;
    private static final int IMAGE_STATE_NORMAL = 2;

    private static final String SNIPPET_DETAILS = "snippet_details";
    private static final String IMAGE_FLAG = "image_flag";
    private static final String BUTTON_FLAG = "button_flag";
    @Bind(R.id.available_rooms_button)
    Button mMoreRoomsButton;
    @Bind(R.id.hotel_details_room)
    FrameLayout mRoomView;
    @Bind(R.id.pager)
    android.support.v4.view.ViewPager mImagePager;
    boolean mIsImageExpended = false;
    private int mImageState = IMAGE_STATE_NORMAL;
    private Record mRecord;
    private int mDisplayHeight = 0;
    private int mImageMinimumHeight;
    private boolean mMoreRoomsButtonVisible = false;
    private HotelListRequest mRequest;

    private AvailabilityDetailsCallback mResultsCallback = new AvailabilityDetailsCallback() {
        @Override
        protected void failure(ResponseBody response, boolean isOffline) {
            super.failure(response, isOffline);
            Events.post(new HotelDetailsResultsEvent());
        }

        @Override
        protected void onDetailsResponse(DetailsResponse detailsResponse) {
            notifyResponse(detailsResponse);
        }

        @Override
        protected void onNoAvailability(DetailsResponse detailsResponse) {
            if (detailsResponse != null) {
                notifyResponse(detailsResponse);
            }
        }

        private void notifyResponse(DetailsResponse detailsResponse) {
//            mHotelSnippetDetails = new HotelSnippet(detailsResponse.record, rateId);
//            setDetailsResponse(mHotelSnippetDetails);
//            Events.post(new HotelDetailsResultsEvent(mHotelSnippetDetails));
        }
    };

    public static HotelSummaryFragment newInstance(HotelListRequest request, Record record) {
        HotelSummaryFragment fragment = new HotelSummaryFragment();
        Bundle args = new Bundle();
        args.putParcelable("record", record);
        args.putParcelable("request", request);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mResultsCallback.attach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mResultsCallback.detach();
    }

    @Override
    public void onResume() {
        super.onResume();
        Events.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Events.unregister(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_summary, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            mIsImageExpended = savedInstanceState.getBoolean(IMAGE_FLAG);
            mMoreRoomsButtonVisible = savedInstanceState.getBoolean(BUTTON_FLAG);
        }

        mImageMinimumHeight = (int) getResources().getDimension(R.dimen.hotel_summary_image_size);
        RecordViewHolder headerRender = new RecordViewHolder(view, getActivity());

        mRecord = getArguments().getParcelable("record");
        mRequest = getArguments().getParcelable("request");
        headerRender.render(mRecord);

        final GestureDetector tapGestureDetector = new GestureDetector(getActivity(), new TapGestureListener());

        mImagePager.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Integer height = mImagePager.getHeight();
                    if (!height.equals(getDisplayHeight()) && !height.equals(mImageMinimumHeight)) {
                        if (height > (getDisplayHeight() / 2 + mImageMinimumHeight / 2)) {
                            expand(mImagePager);
                        } else {
                            collapse(mImagePager);
                        }
                    }
                }
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return tapGestureDetector.onTouchEvent(event);
            }
        });

        mMoreRoomsButton.setOnClickListener(this);
        if (mIsImageExpended) {
            expand(mImagePager);
        } else {
            mMoreRoomsButton.setVisibility(mMoreRoomsButtonVisible ? View.VISIBLE : View.GONE);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        setupMap();
//        loadHotel();
    }


    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_container);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.menu_like);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


        }
        getActivity().invalidateOptionsMenu();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SNIPPET_DETAILS, mRecord);
        outState.putBoolean(IMAGE_FLAG, mIsImageExpended);
        outState.putBoolean(BUTTON_FLAG, mMoreRoomsButtonVisible);
        super.onSaveInstanceState(outState);
    }

//    @Subscribe
//    public void onHotelDetailsResultsEvent(HotelDetailsResultsEvent event) {
//
//        if (getActivity() == null) {
//            return;
//        }
//        mRoomView.removeAllViews();
//        View view = null;
//        if (event.isError()) {
//            view = getNoAvailabilityView();
//            setMoreRoomsButtonVisibility(false);
//        } else {
//            HotelSnippet hotelSnippetDetails = event.getHotelDetails();
//            if (hotelSnippetDetails.hasRates()) {
//                if (hotelSnippetDetails.getAccommodation().rates.size() > 1) {
//                    setMoreRoomsButtonVisibility(true);
//                }
//                mSelectedRate = findCheapestRoom(mHotelSnippetDetails.getAccommodation());
//                view = getSelectedRoomView(mSelectedRate);
//            } else if (false) {
//                view = getNoAvailabilityView();
//                setMoreRoomsButtonVisibility(false);
//            } else {
//                setMoreRoomsButtonVisibility(false);
//            }
//        }
//        mRoomView.addView(view);
//        AnalyticsCalls.get().trackHotelDetails(mRequest, mHotelSnippet, mSelectedRate, getCurrencyCode());
//    }

    private void setMoreRoomsButtonVisibility(boolean visible) {
        mMoreRoomsButtonVisible = visible;
        mMoreRoomsButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }




    private View getNoAvailabilityView() {
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_hoteldetails_noavailability, mRoomView, false);


        return view;
    }





    @Override
    public void onClick(View v) {
        if (mRecord != null) {
            int viewId = v.getId();
//            if (viewId == R.id.book_button) {
//                if (mHotelSnippetDetails.hasRates()) {
//
//                }
//            } else if (viewId == R.id.facilities_box) {
//                showFacilities(mHotelSnippetDetails);
//            } else if (viewId == R.id.reviews_box) {
//                showReviews(mHotelSnippetDetails);
//            } else if (viewId == R.id.available_rooms_button) {
//                if (mHotelSnippetDetails.hasRates()) {
//
//                }
//            }
        }
    }


    private void expand(final View v) {
        final int mStartHeight = v.getHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = (int) ((getDisplayHeight() - mStartHeight) * interpolatedTime) + mStartHeight;
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        mMoreRoomsButton.setVisibility(View.GONE);
        mIsImageExpended = true;
        a.setInterpolator(new DecelerateInterpolator(2));
        a.setDuration(500);
        v.startAnimation(a);
        mImageState = IMAGE_STATE_EXPANDED;
    }

    public int getDisplayHeight() {
        if (mDisplayHeight == 0) {
            mDisplayHeight = getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
        }
        return mDisplayHeight;
    }

    private void collapse(final View v) {
        final int mStartHeight = v.getHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = (int) ((mStartHeight - mImageMinimumHeight) * (1 - interpolatedTime)) + mImageMinimumHeight;
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        mIsImageExpended = false;
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mMoreRoomsButton.setVisibility(mMoreRoomsButtonVisible ? View.VISIBLE : View.GONE);
        a.setInterpolator(new DecelerateInterpolator(2));
        a.setDuration(500);
        v.startAnimation(a);
        mImageState = IMAGE_STATE_NORMAL;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_pin_selected));
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(mRecord.lat,mRecord.lon))
                .icon(icon);

        googleMap.addMarker(options);

//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mHotelSnippet.getLocation().lat + 0.005, mHotelSnippet.getLocation().lon), 12));

        googleMap.setMapType(MAP_TYPE_NORMAL);

        googleMap.getUiSettings().setAllGesturesEnabled(false);

        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        // Place dot on current location
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }

        // Turns traffic layer on
        googleMap.setTrafficEnabled(false);

        // Enables indoor maps
        googleMap.setIndoorEnabled(true);

        // Turns on 3D buildings
        googleMap.setBuildingsEnabled(true);

        // Show Zoom buttons
        googleMap.getUiSettings().setZoomControlsEnabled(false);

        googleMap.setOnMapClickListener(this);
        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (mRecord != null) {
            showFullMap(mRecord);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (mRecord != null) {
            showFullMap(mRecord);
        }
        return true;
    }

    public void showFullMap(Record record) {
        Intent intent = HotelDetailsActivity.createIntent(record, getRequest(), HotelDetailsActivity.TAB_MAP, getActivity());
        startActivity(intent);
    }

    public boolean isImageExpanded() {
        return mImageState == HotelSummaryFragment.IMAGE_STATE_EXPANDED;
    }

    public void collapseImage() {
        collapse(mImagePager);
    }

    public void checkAvailability(HotelListRequest request) {
        RequestUtils.apply(mRequest);
        mRoomView.removeAllViews();
//        loadHotel();
    }


    class TapGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityY) > Math.abs(velocityX)) {
                if (velocityY < 0) {
                    collapse(mImagePager);
                } else {
                    expand(mImagePager);
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceY) > Math.abs(distanceX)) {
                int height = mImagePager.getHeight();
                int width = mImagePager.getWidth();
                if (height - distanceY >= mImageMinimumHeight && height - distanceY <= getDisplayHeight() - mMoreRoomsButton.getMeasuredHeight()) {
                    height -= (int) distanceY;
                }
                mImagePager.setLayoutParams(new FrameLayout.LayoutParams(width, height));
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mImageState == IMAGE_STATE_EXPANDED) {
                collapse(mImagePager);
            } else {
                expand(mImagePager);
            }
            return true;
        }
    }
}
