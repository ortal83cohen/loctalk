package com.travoca.api.model.search;

/**
 * @author ortal
 * @date 2015-04-19
 */
public class ImageRequest extends UserBaseRequest {


    public String image, record,title, description, locationName ,lat, lon ,type;

    public ImageRequest(String image, String record, String title, String description, String locationName, String lat, String lon, String type, String userId) {
        super(userId);
        this.image = image;
        this.record = record;
        this.title = title;
        this.description = description;
        this.locationName = locationName;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
    }


}
