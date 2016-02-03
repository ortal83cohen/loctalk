// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HotelSummaryFragment$$ViewBinder<T extends com.etb.app.fragment.HotelSummaryFragment> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131689617, "field 'mMoreRoomsButton'");
        target.mMoreRoomsButton = finder.castView(view, 2131689617, "field 'mMoreRoomsButton'");
        view = finder.findRequiredView(source, 2131689796, "field 'mFacilitiesBox'");
        target.mFacilitiesBox = view;
        view = finder.findRequiredView(source, 2131689798, "field 'mReviewsBox'");
        target.mReviewsBox = view;
        view = finder.findRequiredView(source, 2131689733, "field 'mRoomView'");
        target.mRoomView = finder.castView(view, 2131689733, "field 'mRoomView'");
        view = finder.findRequiredView(source, 2131689603, "field 'mImagePager'");
        target.mImagePager = finder.castView(view, 2131689603, "field 'mImagePager'");
    }

    @Override
    public void unbind(T target) {
        target.mMoreRoomsButton = null;
        target.mFacilitiesBox = null;
        target.mReviewsBox = null;
        target.mRoomView = null;
        target.mImagePager = null;
    }
}
