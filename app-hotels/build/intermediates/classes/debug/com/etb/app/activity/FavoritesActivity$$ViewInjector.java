// Generated code from Butter Knife. Do not modify!
package com.etb.app.activity;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class FavoritesActivity$$ViewInjector<T extends com.etb.app.activity.FavoritesActivity> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624070, "field 'mToolbar'");
        target.mToolbar = finder.castView(view, 2131624070, "field 'mToolbar'");
    }

    @Override
    public void reset(T target) {
        target.mToolbar = null;
    }
}
