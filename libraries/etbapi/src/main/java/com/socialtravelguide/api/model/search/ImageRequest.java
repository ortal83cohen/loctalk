package com.socialtravelguide.api.model.search;

import com.socialtravelguide.api.model.Payment;
import com.socialtravelguide.api.model.Personal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alex
 * @date 2015-04-19
 */
public class ImageRequest {

    public String name;
    public String image;

    public ImageRequest(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
