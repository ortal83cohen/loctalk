package com.travoca.api.model.search;


/**
 * @author user
 * @date 2015-10-08
 */
public class ServiceGpsViewPortType extends Type {
    protected double mNortheastLat;
    protected double mNortheastLon;
    protected double mSouthwestLat;
    protected double mSouthwestLon;

    public ServiceGpsViewPortType() {
        super(SERVICE_GPS_VIEWPORT);

    }

    public ServiceGpsViewPortType(double northeastLat, double northeastLon, double southwestLat, double southwestLon) {
        super(SERVICE_GPS_VIEWPORT);
        this.mNortheastLat = northeastLat;
        this.mNortheastLon = northeastLon;
        this.mSouthwestLat = southwestLat;
        this.mSouthwestLon = southwestLon;
    }

    @Override
    public String getContext() {
        return mNortheastLat + "," + mNortheastLon + ";" + mSouthwestLat + "," + mSouthwestLon;
    }

    public double getSouthwestLon() {
        return mSouthwestLon;
    }

    public void setSouthwestLon(double mSouthwestLon) {
        this.mSouthwestLon = mSouthwestLon;
    }

    public double getNortheastLat() {
        return mNortheastLat;
    }

    public void setNortheastLat(double mNortheastLat) {
        this.mNortheastLat = mNortheastLat;
    }

    public double getNortheastLon() {
        return mNortheastLon;
    }

    public void setNortheastLon(double mNortheastLon) {
        this.mNortheastLon = mNortheastLon;
    }

    public double getSouthwestLat() {
        return mSouthwestLat;
    }

    public void setSouthwestLat(double mSouthwestLat) {
        this.mSouthwestLat = mSouthwestLat;
    }
}
