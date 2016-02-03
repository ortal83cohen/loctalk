// Generated code from Butter Knife. Do not modify!
package com.etb.app.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ReviewViewHolder$$ViewBinder<T extends com.etb.app.adapter.ReviewViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689657, "field 'mName'");
    target.mName = finder.castView(view, 2131689657, "field 'mName'");
    view = finder.findRequiredView(source, 2131689837, "field 'mPurpose'");
    target.mPurpose = finder.castView(view, 2131689837, "field 'mPurpose'");
    view = finder.findRequiredView(source, 2131689838, "field 'mDate'");
    target.mDate = finder.castView(view, 2131689838, "field 'mDate'");
    view = finder.findRequiredView(source, 16908310, "field 'mTitle'");
    target.mTitle = finder.castView(view, 16908310, "field 'mTitle'");
    view = finder.findRequiredView(source, 16908308, "field 'mTextShort'");
    target.mTextShort = finder.castView(view, 16908308, "field 'mTextShort'");
    view = finder.findRequiredView(source, 2131689801, "field 'mRatingBar'");
    target.mRatingBar = finder.castView(view, 2131689801, "field 'mRatingBar'");
    view = finder.findRequiredView(source, 2131689802, "field 'mRating'");
    target.mRating = finder.castView(view, 2131689802, "field 'mRating'");
  }

  @Override public void unbind(T target) {
    target.mName = null;
    target.mPurpose = null;
    target.mDate = null;
    target.mTitle = null;
    target.mTextShort = null;
    target.mRatingBar = null;
    target.mRating = null;
  }
}
