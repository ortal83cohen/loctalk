package com.socialtravelguide.api.model;

import com.socialtravelguide.api.model.search.Type;

/**
 * @author ortal
 * @date 2015-05-20
 */
public class SearchRequest extends HotelRequest {

    private Type mType;
    private Sort mSort;


    public SearchRequest() {
        super();
        setSort(com.socialtravelguide.api.contract.Sort.Type.DISTANCE);
    }

    public Sort getSort() {
        return mSort;
    }

    public void setSort(String type) {
        if (type == null) {
            mSort = null;
            return;
        }
        if (mSort == null) {
            mSort = new Sort();
        }
        mSort.type = type;
    }


    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public static class Sort {
        public String type;
    }
}