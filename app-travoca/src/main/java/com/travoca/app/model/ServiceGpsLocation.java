package com.travoca.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.travoca.api.model.search.ServiceGpsSprType;
import com.travoca.api.model.search.SprType;

/**
 * @author ortal
 * @date 2015-04-26
 */
public class ServiceGpsLocation extends ServiceGpsSprType implements Parcelable {

    public static final Creator<ServiceGpsLocation> CREATOR = new Creator<ServiceGpsLocation>() {
        public ServiceGpsLocation createFromParcel(Parcel in) {
            return new ServiceGpsLocation(in);
        }

        public ServiceGpsLocation[] newArray(int size) {
            return new ServiceGpsLocation[size];
        }
    };
    private  String mLastUpdate;

    private String mTitle;


    public ServiceGpsLocation() {

    }

    public ServiceGpsLocation(String title, double latitude, double longitude,String lastUpdate) {
        super(latitude, longitude);
        mTitle = title;
        mLastUpdate = lastUpdate;
    }

    public ServiceGpsLocation(String title, LatLng loc) {
        super(loc.latitude, loc.longitude);
        mTitle = title;
    }

    public ServiceGpsLocation(Parcel in) {
        mLastUpdate = in.readString();
        mTitle = in.readString();
        setLatLng(in.readDouble(), in.readDouble());
    }


    public LatLng getLatLng() {
        return new LatLng(getLatitude(), getLongitude());
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLastUpdate);
        dest.writeString(mTitle);
        dest.writeDouble(getLatitude());
        dest.writeDouble(getLongitude());
    }
    @Override
    public String getContext() {
        return getLatitude() + "," + getLongitude() + ";" + mLastUpdate;
    }
}
