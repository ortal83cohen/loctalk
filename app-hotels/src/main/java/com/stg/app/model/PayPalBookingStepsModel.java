package com.stg.app.model;

import android.content.Context;

import com.tech.freak.wizardpager.model.PageList;

/**
 * @author ortal
 * @date 2015-05-03
 */
public class PayPalBookingStepsModel extends BookingStepsModel {

    public PayPalBookingStepsModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new PersonalInfoPage(this)
        );
    }
}
