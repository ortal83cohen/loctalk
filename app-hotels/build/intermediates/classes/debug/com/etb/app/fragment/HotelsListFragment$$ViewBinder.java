// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HotelsListFragment$$ViewBinder<T extends com.etb.app.fragment.HotelsListFragment> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 16908298, "field 'mRecyclerView'");
        target.mRecyclerView = finder.castView(view, 16908298, "field 'mRecyclerView'");
        view = finder.findRequiredView(source, 2131624183, "field 'mButtonSort'");
        target.mButtonSort = finder.castView(view, 2131624183, "field 'mButtonSort'");
        view = finder.findRequiredView(source, 2131624307, "field 'mNoResult'");
        target.mNoResult = finder.castView(view, 2131624307, "field 'mNoResult'");
        view = finder.findRequiredView(source, 2131624182, "field 'mTopPanel'");
        target.mTopPanel = finder.castView(view, 2131624182, "field 'mTopPanel'");
        view = finder.findRequiredView(source, 2131624184, "field 'mAvailableCountText'");
        target.mAvailableCountText = finder.castView(view, 2131624184, "field 'mAvailableCountText'");
        view = finder.findRequiredView(source, 2131624174, "field 'mLoaderText'");
        target.mLoaderText = finder.castView(view, 2131624174, "field 'mLoaderText'");
        view = finder.findRequiredView(source, 2131624308, "field 'mFiltersText'");
        target.mFiltersText = finder.castView(view, 2131624308, "field 'mFiltersText'");
        view = finder.findRequiredView(source, 2131624309, "field 'mModifyPreferences'");
        target.mModifyPreferences = finder.castView(view, 2131624309, "field 'mModifyPreferences'");
        view = finder.findRequiredView(source, 2131624310, "field 'mResetFilters'");
        target.mResetFilters = finder.castView(view, 2131624310, "field 'mResetFilters'");
    }

    @Override
    public void unbind(T target) {
        target.mRecyclerView = null;
        target.mButtonSort = null;
        target.mNoResult = null;
        target.mTopPanel = null;
        target.mAvailableCountText = null;
        target.mLoaderText = null;
        target.mFiltersText = null;
        target.mModifyPreferences = null;
        target.mResetFilters = null;
    }
}
