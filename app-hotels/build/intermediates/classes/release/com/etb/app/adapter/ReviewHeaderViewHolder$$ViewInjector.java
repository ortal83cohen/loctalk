// Generated code from Butter Knife. Do not modify!
package com.etb.app.adapter;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ReviewHeaderViewHolder$$ViewInjector<T extends com.etb.app.adapter.ReviewHeaderViewHolder> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624253, "field 'mTripadvisorRating'");
        target.mTripadvisorRating = finder.castView(view, 2131624253, "field 'mTripadvisorRating'");
        view = finder.findRequiredView(source, 2131624251, "field 'mReviewers'");
        target.mReviewers = finder.castView(view, 2131624251, "field 'mReviewers'");
        view = finder.findRequiredView(source, 2131624254, "field 'mReviews'");
        target.mReviews = finder.castView(view, 2131624254, "field 'mReviews'");
        view = finder.findRequiredView(source, 2131624287, "field 'mRatings'");
        target.mRatings = finder.castView(view, 2131624287, "field 'mRatings'");
    }

    @Override
    public void reset(T target) {
        target.mTripadvisorRating = null;
        target.mReviewers = null;
        target.mReviews = null;
        target.mRatings = null;
    }
}
