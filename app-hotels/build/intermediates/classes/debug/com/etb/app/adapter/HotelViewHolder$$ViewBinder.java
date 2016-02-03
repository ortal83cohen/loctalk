// Generated code from Butter Knife. Do not modify!
package com.etb.app.adapter;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HotelViewHolder$$ViewBinder<T extends com.etb.app.adapter.HotelViewHolder> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624022, "field 'mImageView'");
        target.mImageView = finder.castView(view, 2131624022, "field 'mImageView'");
        view = finder.findRequiredView(source, 16908310, "field 'mTitleView'");
        target.mTitleView = finder.castView(view, 16908310, "field 'mTitleView'");
        view = finder.findRequiredView(source, 2131624111, "field 'mPrice'");
        target.mPrice = finder.castView(view, 2131624111, "field 'mPrice'");
        view = finder.findRequiredView(source, 2131624110, "field 'mBaseRate'");
        target.mBaseRate = finder.castView(view, 2131624110, "field 'mBaseRate'");
        view = finder.findRequiredView(source, 2131624204, "field 'mDiscount'");
        target.mDiscount = finder.castView(view, 2131624204, "field 'mDiscount'");
        view = finder.findRequiredView(source, 2131624203, "field 'mTriangle'");
        target.mTriangle = view;
        view = finder.findRequiredView(source, 2131624188, "field 'mStarRating'");
        target.mStarRating = finder.castView(view, 2131624188, "field 'mStarRating'");
        view = finder.findRequiredView(source, 2131624259, "field 'mTripadvisorRating'");
        target.mTripadvisorRating = finder.castView(view, 2131624259, "field 'mTripadvisorRating'");
        view = finder.findRequiredView(source, 2131624292, "field 'mTripadvisorImage'");
        target.mTripadvisorImage = finder.castView(view, 2131624292, "field 'mTripadvisorImage'");
        view = finder.findRequiredView(source, 2131624291, "field 'mHeartIcon'");
        target.mHeartIcon = finder.castView(view, 2131624291, "field 'mHeartIcon'");
        view = finder.findRequiredView(source, 2131624175, "field 'mBackground'");
        target.mBackground = finder.castView(view, 2131624175, "field 'mBackground'");
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
