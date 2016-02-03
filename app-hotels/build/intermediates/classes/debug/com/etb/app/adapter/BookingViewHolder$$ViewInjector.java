// Generated code from Butter Knife. Do not modify!
package com.etb.app.adapter;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class BookingViewHolder$$ViewInjector<T extends com.etb.app.adapter.BookingViewHolder> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624028, "field 'mImageView'");
        target.mImageView = finder.castView(view, 2131624028, "field 'mImageView'");
        view = finder.findRequiredView(source, 2131624194, "field 'mSnippetTitle'");
        target.mSnippetTitle = finder.castView(view, 2131624194, "field 'mSnippetTitle'");
        view = finder.findRequiredView(source, 2131624195, "field 'mRatingBar'");
        target.mRatingBar = finder.castView(view, 2131624195, "field 'mRatingBar'");
        view = finder.findRequiredView(source, 2131624264, "field 'mCity'");
        target.mCity = finder.castView(view, 2131624264, "field 'mCity'");
        view = finder.findRequiredView(source, 2131624265, "field 'mCountry'");
        target.mCountry = finder.castView(view, 2131624265, "field 'mCountry'");
        view = finder.findRequiredView(source, 2131624162, "field 'mCheckIn'");
        target.mCheckIn = finder.castView(view, 2131624162, "field 'mCheckIn'");
        view = finder.findRequiredView(source, 2131624164, "field 'mCheckOut'");
        target.mCheckOut = finder.castView(view, 2131624164, "field 'mCheckOut'");
        view = finder.findRequiredView(source, 2131624103, "field 'mNights'");
        target.mNights = finder.castView(view, 2131624103, "field 'mNights'");
        view = finder.findRequiredView(source, 2131624105, "field 'mRooms'");
        target.mRooms = finder.castView(view, 2131624105, "field 'mRooms'");
        view = finder.findRequiredView(source, 2131624266, "field 'mManageButton'");
        target.mManageButton = finder.castView(view, 2131624266, "field 'mManageButton'");
        view = finder.findRequiredView(source, 2131624116, "field 'mPrice'");
        target.mPrice = finder.castView(view, 2131624116, "field 'mPrice'");
        view = finder.findRequiredView(source, 2131624109, "field 'mRoomName'");
        target.mRoomName = finder.castView(view, 2131624109, "field 'mRoomName'");
    }

    @Override
    public void reset(T target) {
        target.mImageView = null;
        target.mSnippetTitle = null;
        target.mRatingBar = null;
        target.mCity = null;
        target.mCountry = null;
        target.mCheckIn = null;
        target.mCheckOut = null;
        target.mNights = null;
        target.mRooms = null;
        target.mManageButton = null;
        target.mPrice = null;
        target.mRoomName = null;
    }
}
