// Generated code from Butter Knife. Do not modify!
package com.etb.app.hoteldetails;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HotelSnippetViewHolder$$ViewInjector<T extends com.etb.app.hoteldetails.HotelSnippetViewHolder> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624073, "field 'mSnippetImagePager'");
        target.mSnippetImagePager = finder.castView(view, 2131624073, "field 'mSnippetImagePager'");
        view = finder.findRequiredView(source, 2131624194, "field 'mSnippetTitle'");
        target.mSnippetTitle = finder.castView(view, 2131624194, "field 'mSnippetTitle'");
        view = finder.findRequiredView(source, 2131624195, "field 'mRatingBar'");
        target.mRatingBar = finder.castView(view, 2131624195, "field 'mRatingBar'");
        view = finder.findRequiredView(source, 2131624260, "field 'mReviewers'");
        target.mReviewers = finder.castView(view, 2131624260, "field 'mReviewers'");
        view = finder.findRequiredView(source, 2131624263, "field 'mReviews'");
        target.mReviews = finder.castView(view, 2131624263, "field 'mReviews'");
        view = finder.findRequiredView(source, 2131624196, "field 'mNumberImages'");
        target.mNumberImages = finder.castView(view, 2131624196, "field 'mNumberImages'");
        view = finder.findRequiredView(source, 2131624262, "field 'mTripadvisorRating'");
        target.mTripadvisorRating = finder.castView(view, 2131624262, "field 'mTripadvisorRating'");
        view = finder.findRequiredView(source, 2131624261, "field 'mTripadvisorBar'");
        target.mTripadvisorBar = view;
        view = finder.findRequiredView(source, 2131624201, "field 'mFacilities'");
        target.mFacilities = finder.castView(view, 2131624201, "field 'mFacilities'");
        view = finder.findRequiredView(source, 2131624258, "field 'mFacilitiesBar'");
        target.mFacilitiesBar = view;
    }

    @Override
    public void reset(T target) {
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
