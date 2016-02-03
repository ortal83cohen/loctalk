// Generated code from Butter Knife. Do not modify!
package com.etb.app.adapter;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ReviewHeaderViewHolder$$ViewInjector<T extends com.etb.app.adapter.ReviewHeaderViewHolder> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624262, "field 'mTripadvisorRating'");
        target.mTripadvisorRating = finder.castView(view, 2131624262, "field 'mTripadvisorRating'");
        view = finder.findRequiredView(source, 2131624260, "field 'mReviewers'");
        target.mReviewers = finder.castView(view, 2131624260, "field 'mReviewers'");
        view = finder.findRequiredView(source, 2131624263, "field 'mReviews'");
        target.mReviews = finder.castView(view, 2131624263, "field 'mReviews'");
        view = finder.findRequiredView(source, 2131624297, "field 'mRatings'");
        target.mRatings = finder.castView(view, 2131624297, "field 'mRatings'");
    }

    @Override
    public void reset(T target) {
        target.mTripadvisorRating = null;
        target.mReviewers = null;
        target.mReviews = null;
        target.mRatings = null;
    }
}
