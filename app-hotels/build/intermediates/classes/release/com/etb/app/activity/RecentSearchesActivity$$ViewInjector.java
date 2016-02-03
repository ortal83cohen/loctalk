// Generated code from Butter Knife. Do not modify!
package com.etb.app.activity;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class RecentSearchesActivity$$ViewInjector<T extends com.etb.app.activity.RecentSearchesActivity> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 16908298, "field 'mRecyclerView'");
        target.mRecyclerView = finder.castView(view, 16908298, "field 'mRecyclerView'");
        view = finder.findRequiredView(source, 2131624302, "field 'mNoResult'");
        target.mNoResult = finder.castView(view, 2131624302, "field 'mNoResult'");
    }

    @Override
    public void reset(T target) {
        target.mRecyclerView = null;
        target.mNoResult = null;
    }
}
