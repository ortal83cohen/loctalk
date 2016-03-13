package com.travoca.app.events;

/**
 * @author ortal
 * @date 2015-07-06
 */
public class SearchResultsEvent {

    private int mCount;

    private boolean mError;

    public SearchResultsEvent(int totalNr) {
        mCount = totalNr;
    }

    public SearchResultsEvent(boolean error, int count) {
        mError = error;
        mCount = count;
    }

    public int getCount() {
        return mCount;
    }

    public boolean hasError() {
        return mError;
    }
}
