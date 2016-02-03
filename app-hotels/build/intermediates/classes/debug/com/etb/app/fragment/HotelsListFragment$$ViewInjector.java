// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HotelsListFragment$$ViewInjector<T extends com.etb.app.fragment.HotelsListFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 16908298, "field 'mRecyclerView'");
        target.mRecyclerView = finder.castView(view, 16908298, "field 'mRecyclerView'");
        view = finder.findRequiredView(source, 2131624190, "field 'mButtonSort'");
        target.mButtonSort = finder.castView(view, 2131624190, "field 'mButtonSort'");
        view = finder.findRequiredView(source, 2131624312, "field 'mNoResult'");
        target.mNoResult = finder.castView(view, 2131624312, "field 'mNoResult'");
        view = finder.findRequiredView(source, 2131624189, "field 'mTopPanel'");
        target.mTopPanel = finder.castView(view, 2131624189, "field 'mTopPanel'");
        view = finder.findRequiredView(source, 2131624191, "field 'mAvailableCountText'");
        target.mAvailableCountText = finder.castView(view, 2131624191, "field 'mAvailableCountText'");
        view = finder.findRequiredView(source, 2131624076, "field 'mLoaderImage'");
        target.mLoaderImage = finder.castView(view, 2131624076, "field 'mLoaderImage'");
        view = finder.findRequiredView(source, 2131624175, "field 'mLoaderText'");
        target.mLoaderText = finder.castView(view, 2131624175, "field 'mLoaderText'");
        view = finder.findRequiredView(source, 2131624313, "field 'mModifyPreferences'");
        target.mModifyPreferences = finder.castView(view, 2131624313, "field 'mModifyPreferences'");
        view = finder.findRequiredView(source, 2131624314, "field 'mResetFilters'");
        target.mResetFilters = finder.castView(view, 2131624314, "field 'mResetFilters'");
    }

    @Override
    public void reset(T target) {
        target.mRecyclerView = null;
        target.mButtonSort = null;
        target.mNoResult = null;
        target.mTopPanel = null;
        target.mAvailableCountText = null;
        target.mLoaderImage = null;
        target.mLoaderText = null;
        target.mModifyPreferences = null;
        target.mResetFilters = null;
    }
}
