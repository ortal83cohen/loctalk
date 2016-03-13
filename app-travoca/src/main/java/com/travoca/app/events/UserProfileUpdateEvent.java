package com.travoca.app.events;

import com.travoca.app.member.model.User;

/**
 * @author ortal
 * @date 2015-08-17
 */
public class UserProfileUpdateEvent {

    private final User mUser;

    public UserProfileUpdateEvent(User user) {
        mUser = user;
    }

    public User getUser() {
        return mUser;
    }
}
