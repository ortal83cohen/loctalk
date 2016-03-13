package com.travoca.app.member.model;

import com.google.gson.annotations.SerializedName;

import com.travoca.app.member.model.Profile;

/**
 * @author ortal
 * @date 2015-08-06
 */
public class FacebookUser extends User {

    public FacebookUser(String email, String firstName, String lastName, String id, String imageUrl) {
        this.id = id;
        this.email = email;
        this.profile = new Profile(firstName, lastName, id, imageUrl);
    }

    public FacebookUser() {

    }
}
