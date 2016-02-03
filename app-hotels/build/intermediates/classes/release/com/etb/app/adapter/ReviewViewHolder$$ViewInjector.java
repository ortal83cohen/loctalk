// Generated code from Butter Knife. Do not modify!
package com.etb.app.adapter;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ReviewViewHolder$$ViewInjector<T extends com.etb.app.adapter.ReviewViewHolder> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624117, "field 'mName'");
        target.mName = finder.castView(view, 2131624117, "field 'mName'");
        view = finder.findRequiredView(source, 2131624288, "field 'mPurpose'");
        target.mPurpose = finder.castView(view, 2131624288, "field 'mPurpose'");
        view = finder.findRequiredView(source, 2131624289, "field 'mDate'");
        target.mDate = finder.castView(view, 2131624289, "field 'mDate'");
        view = finder.findRequiredView(source, 16908310, "field 'mTitle'");
        target.mTitle = finder.castView(view, 16908310, "field 'mTitle'");
        view = finder.findRequiredView(source, 16908308, "field 'mTextShort'");
        target.mTextShort = finder.castView(view, 16908308, "field 'mTextShort'");
        view = finder.findRequiredView(source, 2131624253, "field 'mRatingBar'");
        target.mRatingBar = finder.castView(view, 2131624253, "field 'mRatingBar'");
        view = finder.findRequiredView(source, 2131624254, "field 'mRating'");
        target.mRating = finder.castView(view, 2131624254, "field 'mRating'");
    }

    @Override
    public void reset(T target) {
        target.mName = null;
        target.mPurpose = null;
        target.mDate = null;
        target.mTitle = null;
        target.mTextShort = null;
        target.mRatingBar = null;
        target.mRating = null;
    }
}
