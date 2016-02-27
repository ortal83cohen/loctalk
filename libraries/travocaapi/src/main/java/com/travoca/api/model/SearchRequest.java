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