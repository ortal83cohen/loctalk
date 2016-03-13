package com.travoca.app.events;

/**
 * @author ortal
 * @date 2015-08-11
 */
public class MassageEvent {

    public static final int NEW_RECORD_SUCCESS = 1;

    private final int mId;

    public MassageEvent(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }
}
