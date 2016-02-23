package com.travoca.app.member.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author ortal
 * @date 2015-08-06
 */
public class AccessToken {
    @SerializedName("access_token")
    public String token;
    @SerializedName("expires_in")
    public int expiresIn;
    @SerializedName("token_type")
    public String tokenType;
    @SerializedName("scope")
    public String scope;

    public AccessToken(com.facebook.AccessToken accessToken) {
        token = accessToken.getToken();
    }
}
