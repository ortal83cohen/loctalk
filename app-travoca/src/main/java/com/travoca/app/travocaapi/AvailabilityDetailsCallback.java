package com.travoca.app.travocaapi;

import com.squareup.okhttp.ResponseBody;
import com.travoca.api.model.DetailsResponse;
import com.travoca.app.R;
import com.travoca.app.utils.AppLog;

import android.widget.Toast;

import retrofit.Response;

/**
 * @author ortal
 * @date 2015-11-03
 */
public abstract class AvailabilityDetailsCallback extends RetrofitCallback<DetailsResponse> {

    protected boolean isDatesRequest = true;

    public void setIsDatesRequest(boolean isDatesRequest) {
        this.isDatesRequest = isDatesRequest;
    }

    @Override
    protected void failure(ResponseBody response, boolean isOffline) {
        if (!isOffline) {
            onNoAvailability(null);
        }
    }

    @Override
    protected void success(DetailsResponse detailsResponse, Response<DetailsResponse> response) {
        if (mWeakContext == null || mWeakContext.get() == null) {
            return;
        }

        if (isDatesRequest && detailsResponse.record == null) {
            onNoAvailability(detailsResponse);
            AppLog.e(new Throwable("No availability"));
            return;
        }
        onDetailsResponse(detailsResponse);
    }

    protected abstract void onDetailsResponse(DetailsResponse detailsResponse);

    protected void onNoAvailability(DetailsResponse detailsResponse) {
        if (mWeakContext != null && mWeakContext.get() != null) {
            Toast.makeText(mWeakContext.get(), R.string.record_not_available, Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
