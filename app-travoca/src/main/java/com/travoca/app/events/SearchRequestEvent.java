package com.travoca.app.events;

import com.travoca.api.model.SearchRequest;

/**
 * @author ortal
 * @date 2015-07-06
 */
public class SearchRequestEvent {

    private final int mOffset;

    private SearchRequest mRecordListRequest;

    public SearchRequestEvent(SearchRequest recordsRequest, int offset) {
        mRecordListRequest = recordsRequest;
        mOffset = offset;
    }

    public SearchRequest getSearchRequest() {
        return mRecordListRequest;
    }

    public int getOffset() {
        return mOffset;
    }
}
