// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HotelSummaryFragment$$ViewInjector<T extends com.etb.app.fragment.HotelSummaryFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624077, "field 'mMoreRoomsButton'");
        target.mMoreRoomsButton = finder.castView(view, 2131624077, "field 'mMoreRoomsButton'");
        view = finder.findRequiredView(source, 2131624248, "field 'mFacilitiesBox'");
        target.mFacilitiesBox = view;
        view = finder.findRequiredView(source, 2131624250, "field 'mReviewsBox'");
        target.mReviewsBox = view;
        view = finder.findRequiredView(source, 2131624189, "field 'mRoomView'");
        target.mRoomView = finder.castView(view, 2131624189, "field 'mRoomView'");
        view = finder.findRequiredView(source, 2131624066, "field 'mImagePager'");
        target.mImagePager = finder.castView(view, 2131624066, "field 'mImagePager'");
    }

    @Override
    public void reset(T target) {
        target.mMoreRoomsButton = null;
        target.mFacilitiesBox = null;
        target.mReviewsBox = null;
        target.mRoomView = null;
        target.mImagePager = null;
    }
}
