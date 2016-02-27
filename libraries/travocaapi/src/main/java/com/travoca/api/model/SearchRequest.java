package com.travoca.api.model;

import com.travoca.api.contract.Language;
import com.travoca.api.model.search.Type;

/**
 * @author ortal
 * @date 2015-05-20
 */
public class SearchRequest {

    private Type mType;
    private Sort mSort;
    protected String mLanguage = Language.DEFAULT;
    protected String mCurrency = "EUR";
    private String mCustomerCountryCode = "il";
    int offset = 0, limit = 15;
    String userId;



    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String mCurrency) {
        this.mCurrency = mCurrency;
    }

    public SearchRequest() {
        super();
        setSort(com.travoca.api.contract.Sort.Type.DISTANCE);
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

    public String getCustomerCountryCode() {
        return mCustomerCountryCode;
    }

    public void setCustomerCountryCode(String mCustomerCountryCode) {
        this.mCustomerCountryCode = mCustomerCountryCode;
    }

    public static class Sort {
        public String type;
    }
}