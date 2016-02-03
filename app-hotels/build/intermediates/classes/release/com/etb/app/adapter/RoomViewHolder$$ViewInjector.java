// Generated code from Butter Knife. Do not modify!
package com.etb.app.adapter;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class RoomViewHolder$$ViewInjector<T extends com.etb.app.adapter.RoomViewHolder> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 16908310, "field 'mTitle'");
        target.mTitle = finder.castView(view, 16908310, "field 'mTitle'");
        view = finder.findRequiredView(source, 2131624107, "field 'mPrice'");
        target.mPrice = finder.castView(view, 2131624107, "field 'mPrice'");
        view = finder.findRequiredView(source, 2131624200, "field 'mTriangle'");
        target.mTriangle = finder.castView(view, 2131624200, "field 'mTriangle'");
        view = finder.findRequiredView(source, 2131624105, "field 'mBaseRate'");
        target.mBaseRate = finder.castView(view, 2131624105, "field 'mBaseRate'");
        view = finder.findRequiredView(source, 2131624201, "field 'mDiscount'");
        target.mDiscount = finder.castView(view, 2131624201, "field 'mDiscount'");
        view = finder.findRequiredView(source, 2131624102, "field 'mExtra'");
        target.mExtra = finder.castView(view, 2131624102, "field 'mExtra'");
        view = finder.findRequiredView(source, 2131624103, "field 'mRefundable'");
        target.mRefundable = finder.castView(view, 2131624103, "field 'mRefundable'");
        view = finder.findOptionalView(source, 2131624293, null);
        target.mMaxGuest = finder.castView(view, 2131624293, "field 'mMaxGuest'");
        view = finder.findOptionalView(source, 2131624294, null);
        target.mRoomTotal = finder.castView(view, 2131624294, "field 'mRoomTotal'");
        view = finder.findOptionalView(source, 2131624295, null);
        target.mRoomTotalTax = finder.castView(view, 2131624295, "field 'mRoomTotalTax'");
        view = finder.findRequiredView(source, 2131624199, "field 'mBookButton'");
        target.mBookButton = finder.castView(view, 2131624199, "field 'mBookButton'");
    }

    @Override
    public void reset(T target) {
        target.mTitle = null;
        target.mPrice = null;
        target.mTriangle = null;
        target.mBaseRate = null;
        target.mDiscount = null;
        target.mExtra = null;
        target.mRefundable = null;
        target.mMaxGuest = null;
        target.mRoomTotal = null;
        target.mRoomTotalTax = null;
        target.mBookButton = null;
    }
}
