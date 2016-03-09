package com.travoca.app.member.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author ortal
 * @date 2015-08-06
 */
public class User {

    public String id;

    public String email;

    public int status;

    public Profile profile;

    @SerializedName("provider_id")
    public int providerId;


    public User(String email, String firstName, String lastName, String id, String imageUrl) {
        this.id = id;
        this.email = email;
        this.profile = new Profile(firstName, lastName, id, imageUrl);
    }

    public User() {

    }
}
