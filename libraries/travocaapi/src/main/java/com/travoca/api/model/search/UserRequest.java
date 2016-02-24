package com.travoca.api.model.search;

/**
 * @author ortal
 * @date 2015-04-19
 */
public class UserRequest extends UserBaseRequest {


    public String email,imageUrl, firstName, lastName;

    public UserRequest( String userId,String email, String imageUrl,String firstName,String lastName) {
        super(userId);
        this.email = email;
        this.imageUrl = imageUrl;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
