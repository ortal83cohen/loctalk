package com.stg.app.events;

/**
 * @author ortal
 * @date 2015-07-12
 */
public class NetworkStateChangeEvent {
    private final boolean mIsOnline;

    public NetworkStateChangeEvent(boolean isOnline) {
        mIsOnline = isOnline;
    }

    public boolean isOnline() {
        return mIsOnline;
    }
}
