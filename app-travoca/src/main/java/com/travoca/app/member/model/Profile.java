package com.travoca.app.member.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author ortal
 * @date 2015-08-06
 */
public class Profile {

    public static final int GENDER_MALE = 1;

    public static final int GENDER_FEMALE = 2;

    public static final int GENDER_OTHER = 3;

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("image_url")
    public String imageUrl;

    @SerializedName("birth_date")
    public String birthDate;

    @SerializedName("country_iso")
    public String countryIso;

    public String city;

    public int gender;

    @SerializedName("local_language")
    public String language;

    @SerializedName("mail_types")
    public int mailTypes;

    public String phone;


    public Profile(String firstName, String lastName, String id, String imageUrl) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }

    public Profile() {

    }
}
