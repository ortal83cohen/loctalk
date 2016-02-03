// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HotelReviewsFragment$$ViewInjector<T extends com.etb.app.fragment.HotelReviewsFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 16908298, "field 'mReviewsList'");
        target.mReviewsList = finder.castView(view, 16908298, "field 'mReviewsList'");
        view = finder.findRequiredView(source, 16908292, "field 'mEmptyView'");
        target.mEmptyView = view;
    }

    @Override
    public void reset(T target) {
        target.mReviewsList = null;
        target.mEmptyView = null;
    }
}
