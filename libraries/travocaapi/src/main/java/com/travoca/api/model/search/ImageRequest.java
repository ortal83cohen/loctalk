package com.travoca.api.model.search;

import com.travoca.api.model.Payment;
import com.travoca.api.model.Personal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ortal
 * @date 2015-04-19
 */
public class ImageRequest {


    public String image,title, description, locationName ,lat, lon ,type;

    public ImageRequest(String image, String title, String description, String locationName, String lat, String lon, String type) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.locationName = locationName;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
    }


}
