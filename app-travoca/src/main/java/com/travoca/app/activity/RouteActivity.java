package com.travoca.app.activity;

import com.squareup.okhttp.ResponseBody;
import com.travoca.api.model.Record;
import com.travoca.api.model.ResultsResponse;
import com.travoca.api.model.search.ListType;
import com.travoca.app.App;
import com.travoca.app.model.RecordListRequest;
import com.travoca.app.travocaapi.RetrofitCallback;
import com.travoca.app.utils.AppLog;
import com.travoca.app.utils.BrowserUtils;

import android.app.Activity;
import android.net.Uri;

import java.util.ArrayList;

import retrofit.Response;


/**
 * @author ortal
 * @date 2015-06-11
 */
public class RouteActivity extends Activity {

    private Uri mUriData;

    private RetrofitCallback<ResultsResponse> mResultsCallback
            = new RetrofitCallback<ResultsResponse>() {
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
//                startRecordSummaryActivity(apiResponse.recordses.get(0));
//            }
        }

    };


    @Override
    public void onStart() {
        super.onStart();

    }

    private void loadAccommodationDetails(String recordId) {
        ArrayList<String> record = new ArrayList<>();
        record.add(recordId);
        RecordListRequest request = App.provide(this).createRequest();
        request.setType(new ListType(record));

//        TravocaApi travocaApi = TravocaApplication.provide(this).travocaApi();
//        travocaApi.records(request, 0).enqueue(mResultsCallback);
    }

    private void startRecordSummaryActivity(Record acc) {

    }

    private void sendToWebBrowser(Uri data) {
        getIntent().setData(null);
        BrowserUtils.sendToWebBrowser(data, this);
    }


}
