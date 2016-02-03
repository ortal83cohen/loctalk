// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PriceBreakdownFragment$$ViewBinder<T extends com.etb.app.fragment.PriceBreakdownFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624175, "field 'mBackground'");
    target.mBackground = finder.castView(view, 2131624175, "field 'mBackground'");
    view = finder.findRequiredView(source, 2131624247, "field 'mCloseButton'");
    target.mCloseButton = finder.castView(view, 2131624247, "field 'mCloseButton'");
    view = finder.findRequiredView(source, 2131624243, "field 'mRoomText'");
    target.mRoomText = finder.castView(view, 2131624243, "field 'mRoomText'");
    view = finder.findRequiredView(source, 2131624244, "field 'mRoomValue'");
    target.mRoomValue = finder.castView(view, 2131624244, "field 'mRoomValue'");
    view = finder.findRequiredView(source, 2131624166, "field 'mDiscountText'");
    target.mDiscountText = finder.castView(view, 2131624166, "field 'mDiscountText'");
    view = finder.findRequiredView(source, 2131624245, "field 'mDiscountValue'");
    target.mDiscountValue = finder.castView(view, 2131624245, "field 'mDiscountValue'");
    view = finder.findRequiredView(source, 2131624246, "field 'mTaxValue'");
    target.mTaxValue = finder.castView(view, 2131624246, "field 'mTaxValue'");
    view = finder.findRequiredView(source, 2131624114, "field 'mTotalPrice'");
    target.mTotalPrice = finder.castView(view, 2131624114, "field 'mTotalPrice'");
  }

  @Override public void unbind(T target) {
    target.mBackground = null;
    target.mCloseButton = null;
    target.mRoomText = null;
    target.mRoomValue = null;
    target.mDiscountText = null;
    target.mDiscountValue = null;
    target.mTaxValue = null;
    target.mTotalPrice = null;
  }
}
