package com.travoca.app.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.travoca.api.model.Record;
import com.travoca.api.model.search.Poi;
import com.travoca.app.R;
import com.travoca.app.adapter.ViewPagerAdapter;
import com.travoca.app.fragment.RecordsMapFragment;
import com.travoca.app.model.RecordListRequest;

import java.util.List;

import butterknife.ButterKnife;


/**
 * @author ortal
 * @date 2015-06-14
 */
public class RecordDetailsTabsActivity extends TabActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    public static final int TAB_MAP = 2;
    private static final String MAP = "menu_map";
    private static final String EXTRA_DATA = "snipet";
    private static final String HOTEL_MARKER = "marker";

    private Record mRecord;
    private SupportMapFragment mSupportMapFragment;
    private int mDrawLandmarksInMeters = 0;
    private boolean isFirstTime = true;

    public static Intent createIntent(Record record, RecordListRequest request, int tabId, Context context) {
        Intent intent = new Intent(context, RecordDetailsTabsActivity.class);
        intent.putExtra(EXTRA_DATA, record);

        intent.putExtra(EXTRA_TABID, tabId);
        intent.putExtra(EXTRA_REQUEST, request);
        return intent;
    }

    @Override
    protected void onCreateTabFragments(ViewPagerAdapter adapter, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRecord = savedInstanceState.getParcelable(EXTRA_DATA);
        } else {
            mRecord = getIntent().getParcelableExtra(EXTRA_DATA);
        }

//        setTitle(mRecordSnippet.getName());
        ButterKnife.bind(this);

        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("menu_map");
        if (mSupportMapFragment == null) {
            mSupportMapFragment = RecordsMapFragment.newInstance();
        }
        mSupportMapFragment.getMapAsync(this);

//        if (mRecordSnippetDetails != null && !mRecordSnippetDetails.hasRates()) {
//            mAllRoomsButton.setVisibility(View.GONE);
//        }

        adapter.addFragment(mSupportMapFragment, getString(R.string.cps_map), MAP);
    }

    @Override
    protected void onCreateContentView() {
        setContentView(R.layout.activity_record_details_tabs);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        super.onTabSelected(tab);

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        super.onTabReselected(tab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mRecord.lat != null) {
            googleMap.clear();
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_pin_selected));
            MarkerOptions options = new MarkerOptions()
                    .position(new LatLng(mRecord.lat, mRecord.lon)).icon(icon).snippet(HOTEL_MARKER);

            googleMap.addMarker(options);
            if (isFirstTime) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mRecord.lat, mRecord.lon), 13));
                isFirstTime = false;
            }

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            googleMap.getUiSettings().setAllGesturesEnabled(true);

            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(true);
            }

            // Turns traffic layer on
            googleMap.setTrafficEnabled(false);

            // Enables indoor maps
            googleMap.setIndoorEnabled(true);

            // Turns on 3D buildings
            googleMap.setBuildingsEnabled(true);

            // Show Zoom buttons
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    View view = getLayoutInflater().inflate(R.layout.poi_info_window, null, false);
                    if (marker.getTitle() != null) {
                        TextView title = (TextView) view.findViewById(android.R.id.title);
                        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                        ImageView background = (ImageView) view.findViewById(R.id.background);
                        FrameLayout frame = (FrameLayout) view.findViewById(R.id.frame);
                        title.setText(marker.getTitle());
                        text1.setText(marker.getSnippet());
                        ViewGroup.LayoutParams params = background.getLayoutParams();
                        params.width = Math.max(marker.getSnippet().length(), marker.getTitle().length()) * 30 + 36;
                        background.setLayoutParams(params);
                        frame.setLayoutParams(params);
                    }
                    return view;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    return null;
                }
            });
            if (mDrawLandmarksInMeters != 0) {
                CircleOptions circleOptions = new CircleOptions()
                        .center(new LatLng(mRecord.lat, mRecord.lon))   //set center
                        .radius(mDrawLandmarksInMeters)   //set radius in meters
                        .fillColor(getResources().getColor(R.color.transparent_bright_black))
                        .strokeColor(Color.TRANSPARENT)
                        .strokeWidth(0);
                googleMap.addCircle(circleOptions);

                googleMap.setOnMarkerClickListener(this);
            }
        }
    }


    private void mapAsync(List<Poi> poiList) {

        mSupportMapFragment.getMapAsync(this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_DATA, mRecord);
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
