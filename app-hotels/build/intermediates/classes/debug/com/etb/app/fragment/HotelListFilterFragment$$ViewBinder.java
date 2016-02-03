// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.content.res.Resources;
import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HotelListFilterFragment$$ViewBinder<T extends com.etb.app.fragment.HotelListFilterFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624211, "field 'mAccTypesList'");
    target.mAccTypesList = finder.castView(view, 2131624211, "field 'mAccTypesList'");
    view = finder.findRequiredView(source, 2131624212, "field 'mFacilitiesList'");
    target.mFacilitiesList = finder.castView(view, 2131624212, "field 'mFacilitiesList'");
    view = finder.findRequiredView(source, 2131624213, "field 'mApplyButton'");
    target.mApplyButton = finder.castView(view, 2131624213, "field 'mApplyButton'");
    view = finder.findRequiredView(source, 2131624293, "field 'mPriceRangeBar'");
    target.mPriceRangeBar = finder.castView(view, 2131624293, "field 'mPriceRangeBar'");
    view = finder.findRequiredView(source, 2131624292, "field 'mPriceText'");
    target.mPriceText = finder.castView(view, 2131624292, "field 'mPriceText'");
    view = finder.findRequiredView(source, 2131624295, "field 'mStarsGroup'");
    target.mStarsGroup = finder.castView(view, 2131624295, "field 'mStarsGroup'");
    view = finder.findRequiredView(source, 2131624294, "field 'mRatingsGroup'");
    target.mRatingsGroup = finder.castView(view, 2131624294, "field 'mRatingsGroup'");
    Resources res = finder.getContext(source).getResources();
    target.mColumnMargin = res.getDimensionPixelSize(2131296430);
  }

  @Override public void unbind(T target) {
    target.mAccTypesList = null;
    target.mFacilitiesList = null;
    target.mApplyButton = null;
    target.mPriceRangeBar = null;
    target.mPriceText = null;
    target.mStarsGroup = null;
    target.mRatingsGroup = null;
  }
}
