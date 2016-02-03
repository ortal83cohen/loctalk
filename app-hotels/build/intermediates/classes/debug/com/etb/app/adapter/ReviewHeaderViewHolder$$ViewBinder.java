// Generated code from Butter Knife. Do not modify!
package com.etb.app.adapter;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ReviewHeaderViewHolder$$ViewBinder<T extends com.etb.app.adapter.ReviewHeaderViewHolder> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624259, "field 'mTripadvisorRating'");
        target.mTripadvisorRating = finder.castView(view, 2131624259, "field 'mTripadvisorRating'");
        view = finder.findRequiredView(source, 2131624257, "field 'mReviewers'");
        target.mReviewers = finder.castView(view, 2131624257, "field 'mReviewers'");
        view = finder.findRequiredView(source, 2131624260, "field 'mReviews'");
        target.mReviews = finder.castView(view, 2131624260, "field 'mReviews'");
        view = finder.findRequiredView(source, 2131624294, "field 'mRatings'");
        target.mRatings = finder.castView(view, 2131624294, "field 'mRatings'");
    }

    @Override
    public void unbind(T target) {
        target.mTripadvisorRating = null;
        target.mReviewers = null;
        target.mReviews = null;
        target.mRatings = null;
    }
}
