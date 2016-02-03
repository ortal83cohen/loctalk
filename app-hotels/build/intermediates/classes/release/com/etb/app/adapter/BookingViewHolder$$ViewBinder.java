// Generated code from Butter Knife. Do not modify!
package com.etb.app.adapter;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BookingViewHolder$$ViewBinder<T extends com.etb.app.adapter.BookingViewHolder> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131689558, "field 'mImageView'");
        target.mImageView = finder.castView(view, 2131689558, "field 'mImageView'");
        view = finder.findRequiredView(source, 2131689729, "field 'mSnippetTitle'");
        target.mSnippetTitle = finder.castView(view, 2131689729, "field 'mSnippetTitle'");
        view = finder.findRequiredView(source, 2131689730, "field 'mRatingBar'");
        target.mRatingBar = finder.castView(view, 2131689730, "field 'mRatingBar'");
        view = finder.findRequiredView(source, 2131689803, "field 'mCity'");
        target.mCity = finder.castView(view, 2131689803, "field 'mCity'");
        view = finder.findRequiredView(source, 2131689804, "field 'mCountry'");
        target.mCountry = finder.castView(view, 2131689804, "field 'mCountry'");
        view = finder.findRequiredView(source, 2131689695, "field 'mCheckIn'");
        target.mCheckIn = finder.castView(view, 2131689695, "field 'mCheckIn'");
        view = finder.findRequiredView(source, 2131689697, "field 'mCheckOut'");
        target.mCheckOut = finder.castView(view, 2131689697, "field 'mCheckOut'");
        view = finder.findRequiredView(source, 2131689634, "field 'mNights'");
        target.mNights = finder.castView(view, 2131689634, "field 'mNights'");
        view = finder.findRequiredView(source, 2131689636, "field 'mRooms'");
        target.mRooms = finder.castView(view, 2131689636, "field 'mRooms'");
        view = finder.findRequiredView(source, 2131689805, "field 'mManageButton'");
        target.mManageButton = finder.castView(view, 2131689805, "field 'mManageButton'");
        view = finder.findRequiredView(source, 2131689647, "field 'mPrice'");
        target.mPrice = finder.castView(view, 2131689647, "field 'mPrice'");
        view = finder.findRequiredView(source, 2131689641, "field 'mRoomName'");
        target.mRoomName = finder.castView(view, 2131689641, "field 'mRoomName'");
    }

    @Override
    public void unbind(T target) {
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
