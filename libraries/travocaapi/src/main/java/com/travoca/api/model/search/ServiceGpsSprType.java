package com.travoca.api.model.search;

public class ServiceGpsSprType extends Type {
    private double mLatitude;
    private double mLongitude;

    private double mRadius; // in km

    public ServiceGpsSprType() {
        super(SPR);
        mRadius = 5.0f;
    }

    public ServiceGpsSprType(double latitude, double longitude) {
        this(latitude, longitude, 5.0f);
    }

    public ServiceGpsSprType(double latitude, double longitude, double radius) {
        super(SERVICE_GPS_SPR);
        mLatitude = latitude;
        mLongitude = longitude;
        mRadius = radius;
    }

    @Override
    public String getContext() {
        return mLatitude + "," + mLongitude + ";" + mRadius;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLatLng(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public double getRadius() {
        return mRadius;
    }

    public void setRadius(double radius) {
        mRadius = radius;
    }
}