package com.travoca.app.member.model;

/**
 * @author ortal
 * @date 2015-08-06
 */
public class GoogleUser extends User {

    public GoogleUser(String email, String firstName, String lastName, String id, String imageUrl) {
        this.id = id;
        this.email = email;
        this.profile = new Profile(firstName, lastName, id, imageUrl);
    }

    public GoogleUser() {

    }
}
