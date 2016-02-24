package com.travoca.api.model.search;

/**
 * @author ortal
 * @date 2015-04-19
 */
public class LikeRequest extends UserRequest{


    public String id,status;

    public LikeRequest(String id, String status,String userId) {
        super(userId);
        this.id = id;
        this.status = status;
    }
}
