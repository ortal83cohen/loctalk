// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HotelListFilterFragment$$ViewInjector<T extends com.etb.app.fragment.HotelListFilterFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624202, "field 'mAccTypesList'");
        target.mAccTypesList = finder.castView(view, 2131624202, "field 'mAccTypesList'");
        view = finder.findRequiredView(source, 2131624203, "field 'mFacilitiesList'");
        target.mFacilitiesList = finder.castView(view, 2131624203, "field 'mFacilitiesList'");
        view = finder.findRequiredView(source, 2131624204, "field 'mApplyButton'");
        target.mApplyButton = finder.castView(view, 2131624204, "field 'mApplyButton'");
        view = finder.findRequiredView(source, 2131624279, "field 'mPriceRangeBar'");
        target.mPriceRangeBar = finder.castView(view, 2131624279, "field 'mPriceRangeBar'");
        view = finder.findRequiredView(source, 2131624280, "field 'mPriceFrom'");
        target.mPriceFrom = finder.castView(view, 2131624280, "field 'mPriceFrom'");
        view = finder.findRequiredView(source, 2131624281, "field 'mPriceTo'");
        target.mPriceTo = finder.castView(view, 2131624281, "field 'mPriceTo'");
        view = finder.findRequiredView(source, 2131624283, "field 'mStarsGroup'");
        target.mStarsGroup = finder.castView(view, 2131624283, "field 'mStarsGroup'");
        view = finder.findRequiredView(source, 2131624282, "field 'mRatingsGroup'");
        target.mRatingsGroup = finder.castView(view, 2131624282, "field 'mRatingsGroup'");
    }

    @Override
    public void reset(T target) {
        target.mAccTypesList = null;
        target.mFacilitiesList = null;
        target.mApplyButton = null;
        target.mPriceRangeBar = null;
        target.mPriceFrom = null;
        target.mPriceTo = null;
        target.mStarsGroup = null;
        target.mRatingsGroup = null;
    }
}
