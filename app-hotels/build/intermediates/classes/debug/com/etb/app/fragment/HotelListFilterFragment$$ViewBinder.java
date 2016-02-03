// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.content.res.Resources;
import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HotelListFilterFragment$$ViewBinder<T extends com.etb.app.fragment.HotelListFilterFragment> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624205, "field 'mAccTypesList'");
        target.mAccTypesList = finder.castView(view, 2131624205, "field 'mAccTypesList'");
        view = finder.findRequiredView(source, 2131624206, "field 'mFacilitiesList'");
        target.mFacilitiesList = finder.castView(view, 2131624206, "field 'mFacilitiesList'");
        view = finder.findRequiredView(source, 2131624207, "field 'mApplyButton'");
        target.mApplyButton = finder.castView(view, 2131624207, "field 'mApplyButton'");
        view = finder.findRequiredView(source, 2131624287, "field 'mPriceRangeBar'");
        target.mPriceRangeBar = finder.castView(view, 2131624287, "field 'mPriceRangeBar'");
        view = finder.findRequiredView(source, 2131624286, "field 'mPriceText'");
        target.mPriceText = finder.castView(view, 2131624286, "field 'mPriceText'");
        view = finder.findRequiredView(source, 2131624289, "field 'mStarsGroup'");
        target.mStarsGroup = finder.castView(view, 2131624289, "field 'mStarsGroup'");
        view = finder.findRequiredView(source, 2131624288, "field 'mRatingsGroup'");
        target.mRatingsGroup = finder.castView(view, 2131624288, "field 'mRatingsGroup'");
        Resources res = finder.getContext(source).getResources();
        target.mColumnMargin = res.getDimensionPixelSize(2131296430);
    }

    @Override
    public void unbind(T target) {
        target.mAccTypesList = null;
        target.mFacilitiesList = null;
        target.mApplyButton = null;
        target.mPriceRangeBar = null;
        target.mPriceText = null;
        target.mStarsGroup = null;
        target.mRatingsGroup = null;
    }
}
