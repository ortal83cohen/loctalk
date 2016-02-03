// Generated code from Butter Knife. Do not modify!
package com.etb.app.adapter;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class BookingViewHolder$$ViewInjector<T extends com.etb.app.adapter.BookingViewHolder> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624021, "field 'mImageView'");
        target.mImageView = finder.castView(view, 2131624021, "field 'mImageView'");
        view = finder.findRequiredView(source, 2131624185, "field 'mSnippetTitle'");
        target.mSnippetTitle = finder.castView(view, 2131624185, "field 'mSnippetTitle'");
        view = finder.findRequiredView(source, 2131624186, "field 'mRatingBar'");
        target.mRatingBar = finder.castView(view, 2131624186, "field 'mRatingBar'");
        view = finder.findRequiredView(source, 2131624255, "field 'mCity'");
        target.mCity = finder.castView(view, 2131624255, "field 'mCity'");
        view = finder.findRequiredView(source, 2131624256, "field 'mCountry'");
        target.mCountry = finder.castView(view, 2131624256, "field 'mCountry'");
        view = finder.findRequiredView(source, 2131624153, "field 'mCheckIn'");
        target.mCheckIn = finder.castView(view, 2131624153, "field 'mCheckIn'");
        view = finder.findRequiredView(source, 2131624155, "field 'mCheckOut'");
        target.mCheckOut = finder.castView(view, 2131624155, "field 'mCheckOut'");
        view = finder.findRequiredView(source, 2131624094, "field 'mNights'");
        target.mNights = finder.castView(view, 2131624094, "field 'mNights'");
        view = finder.findRequiredView(source, 2131624096, "field 'mRooms'");
        target.mRooms = finder.castView(view, 2131624096, "field 'mRooms'");
        view = finder.findRequiredView(source, 2131624257, "field 'mManageButton'");
        target.mManageButton = finder.castView(view, 2131624257, "field 'mManageButton'");
        view = finder.findRequiredView(source, 2131624107, "field 'mPrice'");
        target.mPrice = finder.castView(view, 2131624107, "field 'mPrice'");
        view = finder.findRequiredView(source, 2131624100, "field 'mRoomName'");
        target.mRoomName = finder.castView(view, 2131624100, "field 'mRoomName'");
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
