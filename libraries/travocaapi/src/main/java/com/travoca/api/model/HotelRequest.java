package com.travoca.api.model;

import com.travoca.api.contract.Language;

public class HotelRequest {


    protected int mNumbersOfRooms;
    protected int mNumberOfPersons;

    protected String mCurrency = "EUR";
    protected String mLanguage = Language.DEFAULT;
    private String mCustomerCountryCode;


    public HotelRequest() {
        mNumbersOfRooms = 1;
        mNumberOfPersons = 2;

    }

    public String getCustomerCountryCode() {
        return mCustomerCountryCode;
    }

    public void setCustomerCountryCode(String customerCountryCode) {
        mCustomerCountryCode = customerCountryCode;
    }

    public int getNumberOfRooms() {
        return mNumbersOfRooms;
    }

    public void setNumbersOfRooms(int numbersOfRooms) {
        this.mNumbersOfRooms = numbersOfRooms;
    }


    public int getNumberOfPersons() {
        return mNumberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        mNumberOfPersons = numberOfPersons;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }


}
