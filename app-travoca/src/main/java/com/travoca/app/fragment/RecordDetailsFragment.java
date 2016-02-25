package com.travoca.app.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.travoca.api.TravocaApi;
import com.travoca.api.model.Record;
import com.travoca.api.model.ResultsResponse;
import com.travoca.api.utils.RequestUtils;
import com.travoca.app.App;
import com.travoca.app.R;
import com.travoca.app.TravocaApplication;
import com.travoca.app.activity.HotelDetailsActivity;
import com.travoca.app.activity.RecordDetailsActivity;
import com.travoca.app.adapter.RecordCardViewHolder;
import com.travoca.app.events.Events;
import com.travoca.app.hoteldetails.RecordViewHolder;
import com.travoca.app.member.MemberStorage;
import com.travoca.app.member.model.User;
import com.travoca.app.model.RecordListRequest;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;


public class RecordDetailsFragment extends BaseFragment implements View.OnClickListener, OnMapReadyCallback, OnMapClickListener, OnMarkerClickListener {
    private static final int IMAGE_STATE_EXPANDED = 1;
    private static final int IMAGE_STATE_NORMAL = 2;
    private static final String SNIPPET_DETAILS = "snippet_details";
    private static final String IMAGE_FLAG = "image_flag";
    private static final String BUTTON_FLAG = "button_flag";
    boolean mIsImageExpended = false;
    private int mImageState = IMAGE_STATE_NORMAL;
    private Record mRecord;
    private int mDisplayHeight = 0;
    private int mImageMinimumHeight;
    private boolean mMoreRoomsButtonVisible = true;
    private RecordListRequest mRequest;
    private MediaPlayer mediaPlayer;
    RecordCardViewHolder recordCardViewHolder;
    @Bind(R.id.see_all_record)
    Button mMoreRecordsButton;
    @Bind(R.id.play)
    Button mPlayButton;
    @Bind(R.id.record_card)
    FrameLayout mRecordCard;
    @Bind(R.id.pager)
    android.support.v4.view.ViewPager mImagePager;
    private Callback<ResultsResponse> mLikeResultsCallback = new Callback<ResultsResponse>() {
        @Override
        public void onResponse(Response<ResultsResponse> response, Retrofit retrofit) {

        }

        @Override
        public void onFailure(Throwable t) {

        }
    };


    public static RecordDetailsFragment newInstance(RecordListRequest request, Record record) {
        RecordDetailsFragment fragment = new RecordDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("record", record);
        args.putParcelable("request", request);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        View view = inflater.inflate(R.layout.fragment_record_details, container, false);
        ButterKnife.bind(this, view);
        MemberStorage memberStorage = App.provide(getActivity()).memberStorage();
        final User user = memberStorage.loadUser();
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

        mPlayButton.setEnabled(false);
        new DownloadAndPlay().execute(mRecord.recordUrl);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if (mRecord.canVote.equals("1")) {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    final TravocaApi travocaApi = TravocaApplication.provide(getActivity()).travocaApi();
                    final MediaPlayer.OnCompletionListener emptyListener = new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {

                        }
                    };
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Rate")
                            .setMessage("did you liked this record?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    travocaApi.like(mRecord.id, user.id).enqueue(mLikeResultsCallback);
                                    recordCardViewHolder.addLike();
                                    mediaPlayer.setOnCompletionListener(emptyListener);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    travocaApi.unlike(mRecord.id, user.id).enqueue(mLikeResultsCallback);
                                    recordCardViewHolder.addDislike();
                                    mediaPlayer.setOnCompletionListener(emptyListener);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        }
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    try {
                        mediaPlayer.start();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Alert")
                            .setMessage("You must login to play a record")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        mMoreRecordsButton.setOnClickListener(this);
        if (mIsImageExpended) {
            expand(mImagePager);
        } else {
            mMoreRecordsButton.setVisibility(mMoreRoomsButtonVisible ? View.VISIBLE : View.GONE);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        setupMap();
        fillRecordCard();
    }

    class DownloadAndPlay extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                // Output stream
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().toString()
                        + "/data/downloadedfile.3gp");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }


        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            mPlayButton.setText("play " + Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            try {
                mediaPlayer.setDataSource(Environment
                        .getExternalStorageDirectory().toString()
                        + "/data/downloadedfile.3gp");
                mediaPlayer.prepare();
            } catch (IOException e) {

            }
            mPlayButton.setEnabled(true);
        }
    }


    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_container);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_hotel_summary, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_streetview:
                ((RecordDetailsActivity) getActivity()).showStreetView();
                break;

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


    public void fillRecordCard() {
        if (getActivity() != null) {
            final LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_record_card, mRecordCard, false);

            recordCardViewHolder = new RecordCardViewHolder(view, getActivity());
            recordCardViewHolder.assignItem(mRecord);

            mRecordCard.addView(view);
        }
    }

    private void setMoreRoomsButtonVisibility(boolean visible) {
        mMoreRoomsButtonVisible = visible;
        mMoreRecordsButton.setVisibility(visible ? View.VISIBLE : View.GONE);
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
        mMoreRecordsButton.setVisibility(View.GONE);
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
        mMoreRecordsButton.setVisibility(mMoreRoomsButtonVisible ? View.VISIBLE : View.GONE);
        a.setInterpolator(new DecelerateInterpolator(2));
        a.setDuration(500);
        v.startAnimation(a);
        mImageState = IMAGE_STATE_NORMAL;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_pin_selected));
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(mRecord.lat, mRecord.lon))
                .icon(icon);

        googleMap.addMarker(options);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mRecord.lat + 0.005, mRecord.lon), 12));

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
        return mImageState == RecordDetailsFragment.IMAGE_STATE_EXPANDED;
    }

    public void collapseImage() {
        collapse(mImagePager);
    }

    public void checkAvailability(RecordListRequest request) {
        RequestUtils.apply(mRequest);
        mRecordCard.removeAllViews();
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
                if (height - distanceY >= mImageMinimumHeight && height - distanceY <= getDisplayHeight() - mMoreRecordsButton.getMeasuredHeight()) {
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
