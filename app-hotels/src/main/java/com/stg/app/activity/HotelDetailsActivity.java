package com.stg.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.socialtravelguide.api.model.Record;
import com.socialtravelguide.api.model.search.Poi;
import com.stg.app.R;
import com.stg.app.adapter.ViewPagerAdapter;
import com.stg.app.core.CoreInterface;
import com.stg.app.fragment.RecordsMapFragment;
import com.stg.app.map.PoiMarker;
import com.stg.app.model.HotelListRequest;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author ortal
 * @date 2015-06-14
 */
public class HotelDetailsActivity extends TabActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    public static final int TAB_MAP = 2;
    private static final String MAP = "menu_map";
    private static final int MAP_POSITION = 2;
    private static final int LANDMARKS_SIZE = 1000;
    private static final String EXTRA_DATA = "snipet";
    @Bind(R.id.see_all_record)
    Button mAllRoomsButton;
    @Bind(R.id.show_landmarks)
    Button mShowLandmarks;
    private Record mRecord;
    private SupportMapFragment mSupportMapFragment;
    private int mDrawLandmarksInMeters = 0;
    private List<Poi> mPoiList = null;
    private PoiMarker mPoiMarker;
    private boolean isFirstTime = true;

    public static Intent createIntent(Record record, HotelListRequest request, int tabId, Context context) {
        Intent intent = new Intent(context, HotelDetailsActivity.class);
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

//        setTitle(mHotelSnippet.getName());
        ButterKnife.bind(this);

        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("menu_map");
        if (mSupportMapFragment == null) {
            mSupportMapFragment = RecordsMapFragment.newInstance();
        }
        mSupportMapFragment.getMapAsync(this);

//        if (mHotelSnippetDetails != null && !mHotelSnippetDetails.hasRates()) {
//            mAllRoomsButton.setVisibility(View.GONE);
//        }
        mPoiMarker = new PoiMarker(this);
        adapter.addFragment(mSupportMapFragment, getString(R.string.cps_map), MAP);
    }

    @Override
    protected void onCreateContentView() {
        setContentView(R.layout.activity_hoteldetails);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        super.onTabSelected(tab);
        if (tab.getPosition() == MAP_POSITION) {
            mShowLandmarks.setVisibility(View.VISIBLE);
        } else {
            mShowLandmarks.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        super.onTabReselected(tab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hotel_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        if (mHotelSnippet.getLocation() != null) {
//            googleMap.clear();
//            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_pin_selected));
//            MarkerOptions options = new MarkerOptions()
//                    .position(new LatLng(mHotelSnippet.getLocation().lat, mHotelSnippet.getLocation().lon)).icon(icon).snippet(HOTEL_MARKER);
//
//            googleMap.addMarker(options);
//            if (isFirstTime) {
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mHotelSnippet.getLocation().lat, mHotelSnippet.getLocation().lon), 13));
//                isFirstTime = false;
//            }
//
//            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//            googleMap.getUiSettings().setAllGesturesEnabled(true);
//
//            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
//
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                googleMap.setMyLocationEnabled(true);
//            }
//
//            // Turns traffic layer on
//            googleMap.setTrafficEnabled(false);
//
//            // Enables indoor maps
//            googleMap.setIndoorEnabled(true);
//
//            // Turns on 3D buildings
//            googleMap.setBuildingsEnabled(true);
//
//            // Show Zoom buttons
//            googleMap.getUiSettings().setZoomControlsEnabled(true);
//            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//                @Override
//                public View getInfoWindow(Marker marker) {
//                    View view = getLayoutInflater().inflate(R.layout.poi_info_window, null, false);
//                    if (marker.getTitle() != null) {
//                        TextView title = (TextView) view.findViewById(android.R.id.title);
//                        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
//                        ImageView background = (ImageView) view.findViewById(R.id.background);
//                        FrameLayout frame = (FrameLayout) view.findViewById(R.id.frame);
//                        title.setText(marker.getTitle());
//                        text1.setText(marker.getSnippet());
//                        ViewGroup.LayoutParams params = background.getLayoutParams();
//                        params.width = Math.max(marker.getSnippet().length(), marker.getTitle().length()) * 30 + 36;
//                        background.setLayoutParams(params);
//                        frame.setLayoutParams(params);
//                    }
//                    return view;
//                }
//
//                @Override
//                public View getInfoContents(Marker marker) {
//                    return null;
//                }
//            });
//            if (mDrawLandmarksInMeters != 0) {
//                CircleOptions circleOptions = new CircleOptions()
//                        .center(new LatLng(mHotelSnippet.getLocation().lat, mHotelSnippet.getLocation().lon))   //set center
//                        .radius(mDrawLandmarksInMeters)   //set radius in meters
//                        .fillColor(getResources().getColor(R.color.transparent_bright_black))
//                        .strokeColor(Color.TRANSPARENT)
//                        .strokeWidth(0);
//                googleMap.addCircle(circleOptions);
//                if (mPoiList == null) {
//                    Toast.makeText(getBaseContext(), R.string.nothing_to_show_in_this_area, Toast.LENGTH_LONG).show();
//                } else {
//                    Integer id = 0;
//                    for (Poi poi : mPoiList) {
//                        googleMap.addMarker(mPoiMarker.create(id, poi, PoiMarker.STATUS_UNSEEN));
//                        id++;
//                    }
//                }
//                googleMap.setOnMarkerClickListener(this);
//            }
//        }
    }

    @OnClick(R.id.see_all_record)
    public void onClickAvailableRooms(Button button) {
//        showRoomsList(mHotelSnippet.geId());
    }


    @OnClick(R.id.show_landmarks)
    public void onClickShowLandmarks(Button button) {
        if (mDrawLandmarksInMeters != 0) {
            mDrawLandmarksInMeters = 0;
            button.setText(R.string.show_landmarks);
            mapAsync(null);
        } else {
            mDrawLandmarksInMeters = mDrawLandmarksInMeters + LANDMARKS_SIZE;
            button.setText(R.string.remove_landmarks);
            CoreInterface.Service mCoreInterface = CoreInterface.create(getApplicationContext());

//            Call<List<Poi>> call = mCoreInterface.poiList(String.valueOf(mHotelSnippet.getLocation().lon), String.valueOf(mHotelSnippet.getLocation().lat), String.valueOf(mDrawLandmarksInMeters));
//            call.enqueue(new RetrofitCallback<List<Poi>>() {
//                @Override
//                public void success(List<Poi> list, Response<List<Poi>> response) {
//                    mapAsync(list);
//                }
//
//                @Override
//                public void failure(ResponseBody error, boolean isOffline) {
//                    mapAsync(null);
//                }
//            });
        }
    }


    private void mapAsync(List<Poi> poiList) {
        mPoiList = poiList;
        if (poiList != null) {
            for (int i = 0; i < poiList.size(); i++) {
                Poi poi = poiList.get(i);
                if (poi.type_id == PoiMarker.TYPE_DISTRICT || poi.type_id == PoiMarker.TYPE_AREA) {
                    poiList.remove(i);
                }
            }
        }
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
