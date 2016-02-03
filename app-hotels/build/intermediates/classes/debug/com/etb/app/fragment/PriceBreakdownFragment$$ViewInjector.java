// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class PriceBreakdownFragment$$ViewInjector<T extends com.etb.app.fragment.PriceBreakdownFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624176, "field 'mBackground'");
        target.mBackground = finder.castView(view, 2131624176, "field 'mBackground'");
        view = finder.findRequiredView(source, 2131624244, "field 'mCloseButton'");
        target.mCloseButton = finder.castView(view, 2131624244, "field 'mCloseButton'");
        view = finder.findRequiredView(source, 2131624240, "field 'mRoomText'");
        target.mRoomText = finder.castView(view, 2131624240, "field 'mRoomText'");
        view = finder.findRequiredView(source, 2131624241, "field 'mRoomValue'");
        target.mRoomValue = finder.castView(view, 2131624241, "field 'mRoomValue'");
        view = finder.findRequiredView(source, 2131624169, "field 'mDiscountText'");
        target.mDiscountText = finder.castView(view, 2131624169, "field 'mDiscountText'");
        view = finder.findRequiredView(source, 2131624242, "field 'mDiscountValue'");
        target.mDiscountValue = finder.castView(view, 2131624242, "field 'mDiscountValue'");
        view = finder.findRequiredView(source, 2131624243, "field 'mTaxValue'");
        target.mTaxValue = finder.castView(view, 2131624243, "field 'mTaxValue'");
        view = finder.findRequiredView(source, 2131624119, "field 'mTotalPrice'");
        target.mTotalPrice = finder.castView(view, 2131624119, "field 'mTotalPrice'");
    }

    @Override
    public void reset(T target) {
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
