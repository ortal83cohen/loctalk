package com.travoca.app.model;

import com.travoca.api.model.SearchRequest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ortal
 * @date 2015-06-17
 */
public class RecordListRequest extends SearchRequest implements Parcelable {

    public static final Parcelable.Creator<RecordListRequest> CREATOR
            = new Parcelable.Creator<RecordListRequest>() {
        public RecordListRequest createFromParcel(Parcel in) {
            return new RecordListRequest(in);
        }

        public RecordListRequest[] newArray(int size) {
            return new RecordListRequest[size];
        }
    };

    public RecordListRequest() {
        super();
    }

    public RecordListRequest(Parcel in) {
        int readType = in.readInt();
        if (readType == 1) {
            setType((CurrentLocation) in.readParcelable(CurrentLocation.class.getClassLoader()));
        } else if (readType == 2) {
            setType((Location) in.readParcelable(Location.class.getClassLoader()));
        } else if (readType == 3) {
            setType((ViewPort) in.readParcelable(ViewPort.class.getClassLoader()));
        } else if (readType == 4) {
            setType((MapSelectedViewPort) in
                    .readParcelable(MapSelectedViewPort.class.getClassLoader()));
        }

        setCurrency(in.readString());
        setCustomerCountryCode(in.readString());
        setLanguage(in.readString());

    }

    public RecordListRequest(RecordListRequest recordsRequest) {
        setType(recordsRequest.getType());
        setLanguage(recordsRequest.getLanguage());
        setCurrency(recordsRequest.getCurrency());
        setCustomerCountryCode(recordsRequest.getCustomerCountryCode());
    }

    public Location getLocation() {
        return (Location) getType();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (getType() instanceof CurrentLocation) {
            dest.writeInt(1);
            dest.writeParcelable((CurrentLocation) getType(), flags);
        } else if (getType() instanceof Location) {
            dest.writeInt(2);
            dest.writeParcelable((Location) getType(), flags);
        } else if (getType() instanceof ViewPort) {
            dest.writeInt(3);
            dest.writeParcelable((ViewPort) getType(), flags);
        } else if (getType() instanceof MapSelectedViewPort) {
            dest.writeInt(4);
            dest.writeParcelable((MapSelectedViewPort) getType(), flags);
        } else {
            dest.writeInt(0);
        }

        dest.writeString(getCurrency());
        dest.writeString(getCustomerCountryCode());
        dest.writeString(getLanguage());

    }

}
