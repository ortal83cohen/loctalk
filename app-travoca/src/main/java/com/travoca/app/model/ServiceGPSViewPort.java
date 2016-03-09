package com.travoca.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLngBounds;
import com.travoca.api.model.search.ServiceGpsViewPortType;

/**
 * @author ortal
 * @date 2015-10-08
 */
public class ServiceGPSViewPort extends ServiceGpsViewPortType implements Parcelable, LocationWithTitle {
    public static final Creator<ServiceGPSViewPort> CREATOR = new Creator<ServiceGPSViewPort>() {
        @Override
        public ServiceGPSViewPort createFromParcel(Parcel in) {
            return new ServiceGPSViewPort(in);
        }

        @Override
        public ServiceGPSViewPort[] newArray(int size) {
            return new ServiceGPSViewPort[size];
        }
    };
    private String mTitle;

    protected ServiceGPSViewPort(Parcel in) {
        mTitle = in.readString();
        setNortheastLat(in.readDouble());
        setNortheastLon(in.readDouble());
        setSouthwestLat(in.readDouble());
        setSouthwestLon(in.readDouble());

    }

    public ServiceGPSViewPort() {
    }

    public ServiceGPSViewPort(String title, double northeastLat, double northeastLon, double southwestLat, double southwestLon) {
        super(northeastLat, northeastLon, southwestLat, southwestLon);
        mTitle = title;
    }

    public ServiceGPSViewPort(String title, LatLngBounds latLngBounds) {
        super(latLngBounds.northeast.latitude, latLngBounds.northeast.longitude, latLngBounds.southwest.latitude, latLngBounds.southwest.longitude);
        mTitle = title;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeDouble(getNortheastLat());
        dest.writeDouble(getNortheastLon());
        dest.writeDouble(getSouthwestLat());
        dest.writeDouble(getSouthwestLon());
    }


}
