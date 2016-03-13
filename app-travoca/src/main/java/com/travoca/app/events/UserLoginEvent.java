package com.travoca.app.events;

import com.travoca.app.member.model.User;

/**
 * @author ortal
 * @date 2015-08-11
 */
public class UserLoginEvent {

    private final User mUser;

    public UserLoginEvent(User user) {
        mUser = user;
    }

    public User getUser() {
        return mUser;
    }
}
