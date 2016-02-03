// Generated code from Butter Knife. Do not modify!
package com.etb.app.adapter;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HotelViewHolder$$ViewBinder<T extends com.etb.app.adapter.HotelViewHolder> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131689558, "field 'mImageView'");
        target.mImageView = finder.castView(view, 2131689558, "field 'mImageView'");
        view = finder.findRequiredView(source, 16908310, "field 'mTitleView'");
        target.mTitleView = finder.castView(view, 16908310, "field 'mTitleView'");
        view = finder.findRequiredView(source, 2131689647, "field 'mPrice'");
        target.mPrice = finder.castView(view, 2131689647, "field 'mPrice'");
        view = finder.findRequiredView(source, 2131689646, "field 'mBaseRate'");
        target.mBaseRate = finder.castView(view, 2131689646, "field 'mBaseRate'");
        view = finder.findRequiredView(source, 2131689746, "field 'mDiscount'");
        target.mDiscount = finder.castView(view, 2131689746, "field 'mDiscount'");
        view = finder.findRequiredView(source, 2131689745, "field 'mTriangle'");
        target.mTriangle = view;
        view = finder.findRequiredView(source, 2131689730, "field 'mStarRating'");
        target.mStarRating = finder.castView(view, 2131689730, "field 'mStarRating'");
        view = finder.findRequiredView(source, 2131689801, "field 'mTripadvisorRating'");
        target.mTripadvisorRating = finder.castView(view, 2131689801, "field 'mTripadvisorRating'");
        view = finder.findRequiredView(source, 2131689834, "field 'mTripadvisorImage'");
        target.mTripadvisorImage = finder.castView(view, 2131689834, "field 'mTripadvisorImage'");
        view = finder.findRequiredView(source, 2131689833, "field 'mHeartIcon'");
        target.mHeartIcon = finder.castView(view, 2131689833, "field 'mHeartIcon'");
        view = finder.findRequiredView(source, 2131689711, "field 'mBackground'");
        target.mBackground = finder.castView(view, 2131689711, "field 'mBackground'");
    }

    @Override
    public void unbind(T target) {
        target.mImageView = null;
        target.mTitleView = null;
        target.mPrice = null;
        target.mBaseRate = null;
        target.mDiscount = null;
        target.mTriangle = null;
        target.mStarRating = null;
        target.mTripadvisorRating = null;
        target.mTripadvisorImage = null;
        target.mHeartIcon = null;
        target.mBackground = null;
    }
}
