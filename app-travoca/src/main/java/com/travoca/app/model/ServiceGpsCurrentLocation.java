package com.travoca.app.model;

import android.os.Parcel;

public class ServiceGpsCurrentLocation extends ServiceGpsLocation {

    public static final Creator<ServiceGpsCurrentLocation> CREATOR
            = new Creator<ServiceGpsCurrentLocation>() {
        public ServiceGpsCurrentLocation createFromParcel(Parcel in) {
            return new ServiceGpsCurrentLocation(in);
        }

        public ServiceGpsCurrentLocation[] newArray(int size) {
            return new ServiceGpsCurrentLocation[size];
        }
    };

    public ServiceGpsCurrentLocation(Parcel in) {
        super(in);
    }

    public ServiceGpsCurrentLocation() {
    }
}