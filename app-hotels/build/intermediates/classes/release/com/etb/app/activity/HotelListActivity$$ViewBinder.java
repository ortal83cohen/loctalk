// Generated code from Butter Knife. Do not modify!
package com.etb.app.activity;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HotelListActivity$$ViewBinder<T extends com.etb.app.activity.HotelListActivity> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131689600, "field 'mToolbar'");
        target.mToolbar = finder.castView(view, 2131689600, "field 'mToolbar'");
        view = finder.findRequiredView(source, 2131689613, "field 'mButtonFilter'");
        target.mButtonFilter = finder.castView(view, 2131689613, "field 'mButtonFilter'");
        view = finder.findRequiredView(source, 2131689612, "field 'mButtonPoisFilter'");
        target.mButtonPoisFilter = finder.castView(view, 2131689612, "field 'mButtonPoisFilter'");
        view = finder.findRequiredView(source, 2131689611, "field 'mRefreshHotels'");
        target.mRefreshHotels = finder.castView(view, 2131689611, "field 'mRefreshHotels'");
        view = finder.findRequiredView(source, 2131689606, "field 'mLoaderImage'");
        target.mLoaderImage = finder.castView(view, 2131689606, "field 'mLoaderImage'");
    }

    @Override
    public void unbind(T target) {
        target.mToolbar = null;
        target.mButtonFilter = null;
        target.mButtonPoisFilter = null;
        target.mRefreshHotels = null;
        target.mLoaderImage = null;
    }
}
