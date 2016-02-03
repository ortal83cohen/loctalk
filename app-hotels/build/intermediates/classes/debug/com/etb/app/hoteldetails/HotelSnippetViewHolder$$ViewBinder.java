// Generated code from Butter Knife. Do not modify!
package com.etb.app.hoteldetails;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HotelSnippetViewHolder$$ViewBinder<T extends com.etb.app.hoteldetails.HotelSnippetViewHolder> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624067, "field 'mSnippetImagePager'");
        target.mSnippetImagePager = finder.castView(view, 2131624067, "field 'mSnippetImagePager'");
        view = finder.findRequiredView(source, 2131624187, "field 'mSnippetTitle'");
        target.mSnippetTitle = finder.castView(view, 2131624187, "field 'mSnippetTitle'");
        view = finder.findRequiredView(source, 2131624188, "field 'mRatingBar'");
        target.mRatingBar = finder.castView(view, 2131624188, "field 'mRatingBar'");
        view = finder.findRequiredView(source, 2131624257, "field 'mReviewers'");
        target.mReviewers = finder.castView(view, 2131624257, "field 'mReviewers'");
        view = finder.findRequiredView(source, 2131624260, "field 'mReviews'");
        target.mReviews = finder.castView(view, 2131624260, "field 'mReviews'");
        view = finder.findRequiredView(source, 2131624189, "field 'mNumberImages'");
        target.mNumberImages = finder.castView(view, 2131624189, "field 'mNumberImages'");
        view = finder.findRequiredView(source, 2131624259, "field 'mTripadvisorRating'");
        target.mTripadvisorRating = finder.castView(view, 2131624259, "field 'mTripadvisorRating'");
        view = finder.findRequiredView(source, 2131624258, "field 'mTripadvisorBar'");
        target.mTripadvisorBar = view;
        view = finder.findRequiredView(source, 2131624194, "field 'mFacilities'");
        target.mFacilities = finder.castView(view, 2131624194, "field 'mFacilities'");
        view = finder.findRequiredView(source, 2131624255, "field 'mFacilitiesBar'");
        target.mFacilitiesBar = view;
    }

    @Override
    public void unbind(T target) {
        target.mSnippetImagePager = null;
        target.mSnippetTitle = null;
        target.mRatingBar = null;
        target.mReviewers = null;
        target.mReviews = null;
        target.mNumberImages = null;
        target.mTripadvisorRating = null;
        target.mTripadvisorBar = null;
        target.mFacilities = null;
        target.mFacilitiesBar = null;
    }
}
