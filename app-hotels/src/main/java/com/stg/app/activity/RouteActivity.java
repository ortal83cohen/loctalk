package com.stg.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.util.ArrayMap;

import com.socialtravelguide.api.StgApi;
import com.socialtravelguide.api.model.Record;
import com.socialtravelguide.api.model.ResultsResponse;
import com.socialtravelguide.api.model.search.ListType;
import com.stg.app.App;
import com.stg.app.HotelsApplication;
import com.stg.app.core.CoreInterface;
import com.stg.app.etbapi.RetrofitCallback;
import com.stg.app.model.RecordListRequest;
import com.stg.app.model.Location;
import com.stg.app.utils.AppLog;
import com.stg.app.utils.BrowserUtils;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.okhttp.ResponseBody;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Response;


/**
 * @author ortal
 * @date 2015-06-11
 */
public class RouteActivity extends Activity {

    private Uri mUriData;
    private RetrofitCallback<ResultsResponse> mResultsCallback = new RetrofitCallback<ResultsResponse>() {
        @Override
        protected void failure(ResponseBody response, boolean isOffline) {
            AppLog.e("RouteActivity - recordButton failure");
            startActivity(HomeActivity.createIntent(RouteActivity.this));
        }

        @Override
        protected void success(ResultsResponse apiResponse, Response<ResultsResponse> response) {
//            if (apiResponse.recordses == null) {
//                AppLog.e("RouteActivity - recordses == null");
//                startActivity(HomeActivity.createIntent(RouteActivity.this));
//            } else {
//                startHotelSummaryActivity(apiResponse.recordses.get(0));
//            }
        }

    };


    @Override
    public void onStart() {
        try {
            super.onStart();
            mUriData = getIntent().getData();
            AppLog.d("Received data: " + mUriData);
            if (mUriData == null) {
                startActivity(HomeActivity.createIntent(RouteActivity.this));
                finish();
                return;
            }
            if (mUriData.toString().contains("mobile/download.php")) {
                String[] split = mUriData.getEncodedQuery().split("url=");
                mUriData = Uri.parse(URLDecoder.decode(split[1], "UTF-8"));
            }
            if (mUriData.toString().contains("/mybooking/")) {
                {//index.php?br=4404268-66&pc=5418&amu=280822147
                    String[] split = mUriData.getEncodedQuery().split("&");
                    Map<String, String> map = new HashMap<String, String>();
                    for (String param : split) {
                        String name = param.split("=")[0];
                        String value = param.split("=")[1];
                        map.put(name, value);
                    }

                }
            } else {
                if (mUriData.toString().startsWith("http://recp.mkt32.net/")) {
                    String[] split = mUriData.getEncodedQuery().split("&kd=");
                    mUriData = Uri.parse(URLDecoder.decode(split[1], "UTF-8"));
                }
                String uri = mUriData.toString();
                uri = uri.replace("m.socialtravelguide.com", "www.socialtravelguide.com");

                CoreInterface.create(getApplicationContext()).uriParse(uri).enqueue(new RetrofitCallback<ArrayMap<String, String>>() {
                    @Override
                    protected void failure(ResponseBody response, boolean isOffline) {
                        sendToWebBrowser(mUriData);
                    }

                    @Override
                    protected void success(ArrayMap<String, String> params, Response<ArrayMap<String, String>> response) {
                        try {
                            switch (params.get("target")) {
                                case "/search/result.php":
                                case "/index_landing.php":
                                    final RecordListRequest recordListRequest = App.provide(RouteActivity.this).createHotelsRequest();
                                    recordListRequest.setType(new Location(params.get("name"), new LatLng(Double.valueOf(params.get("lat")), Double.valueOf(params.get("lon")))));
                                    startActivity(RecordListActivity.createIntent(recordListRequest, RouteActivity.this));
                                    break;
                                case "/hoteldetails.php":
                                    loadAccommodationDetails(params.get("group_hotel_id"));
                                    break;
                                case "index_home.php":
                                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                                    break;
                                default:
                                    sendToWebBrowser(mUriData);
                            }
                        } catch (Exception e) {
                            AppLog.e(e);
                            sendToWebBrowser(getIntent().getData());
                        }
                    }
                });
            }
        } catch (Exception e) {
            AppLog.e(e);
            sendToWebBrowser(getIntent().getData());
        }
        finish();
    }

    private void loadAccommodationDetails(String hotelId) {
        ArrayList<String> hotels = new ArrayList<>();
        hotels.add(hotelId);
        RecordListRequest request = App.provide(this).createHotelsRequest();
        request.setType(new ListType(hotels));

        StgApi stgApi = HotelsApplication.provide(this).etbApi();
        stgApi.records(request, 0).enqueue(mResultsCallback);
    }

    private void startHotelSummaryActivity(Record acc) {

    }

    private void sendToWebBrowser(Uri data) {
        getIntent().setData(null);
        BrowserUtils.sendToWebBrowser(data, this);
    }


}
