package com.travoca.api.model.search;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ortal
 * @date 2015-04-19
 */
public class ImageRequest extends UserBaseRequest implements Parcelable {


    public String image, record,title, description, locationName ,lat, lon ,type;

    public ImageRequest( String title, String description, String locationName, String lat, String lon, String type, String userId) {
        super(userId);
        this.image = "";
        this.record = "";
        this.title = title;
        this.description = description;
        this.locationName = locationName;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
    }


    protected ImageRequest(Parcel in) {
        super( in.readString());
        image = in.readString();
        record = in.readString();
        title = in.readString();
        description = in.readString();
        locationName = in.readString();
        lat = in.readString();
        lon = in.readString();
        type = in.readString();
    }

    public static final Creator<ImageRequest> CREATOR = new Creator<ImageRequest>() {
        @Override
        public ImageRequest createFromParcel(Parcel in) {
            return new ImageRequest(in);
        }

        @Override
        public ImageRequest[] newArray(int size) {
            return new ImageRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(image);
        dest.writeString(record);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(locationName);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeString(type);
    }
}
