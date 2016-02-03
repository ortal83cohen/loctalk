// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HotelFacilitiesFragment$$ViewBinder<T extends com.etb.app.fragment.HotelFacilitiesFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689734, "field 'mHotelDetailsText'");
    target.mHotelDetailsText = finder.castView(view, 2131689734, "field 'mHotelDetailsText'");
    view = finder.findRequiredView(source, 2131689738, "field 'mPaymentMethodsText'");
    target.mPaymentMethodsText = finder.castView(view, 2131689738, "field 'mPaymentMethodsText'");
    view = finder.findRequiredView(source, 2131689739, "field 'mServices'");
    target.mServices = finder.castView(view, 2131689739, "field 'mServices'");
    view = finder.findRequiredView(source, 2131689740, "field 'mGeneralFacilities'");
    target.mGeneralFacilities = finder.castView(view, 2131689740, "field 'mGeneralFacilities'");
    view = finder.findRequiredView(source, 2131689736, "field 'mFacilities'");
    target.mFacilities = finder.castView(view, 2131689736, "field 'mFacilities'");
    view = finder.findRequiredView(source, 2131689737, "field 'mPaymentMethodsImages'");
    target.mPaymentMethodsImages = finder.castView(view, 2131689737, "field 'mPaymentMethodsImages'");
    view = finder.findRequiredView(source, 2131689735, "method 'onReadMoreDescriptionPressed'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onReadMoreDescriptionPressed(finder.<android.widget.Button>castParam(p0, "doClick", 0, "onReadMoreDescriptionPressed", 0));
        }
      });
  }

  @Override public void unbind(T target) {
    target.mHotelDetailsText = null;
    target.mPaymentMethodsText = null;
    target.mServices = null;
    target.mGeneralFacilities = null;
    target.mFacilities = null;
    target.mPaymentMethodsImages = null;
  }
}
