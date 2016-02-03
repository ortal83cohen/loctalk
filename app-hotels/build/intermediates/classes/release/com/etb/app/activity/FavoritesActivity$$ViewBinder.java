// Generated code from Butter Knife. Do not modify!
package com.etb.app.activity;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FavoritesActivity$$ViewBinder<T extends com.etb.app.activity.FavoritesActivity> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131689600, "field 'mToolbar'");
        target.mToolbar = finder.castView(view, 2131689600, "field 'mToolbar'");
    }

    @Override
    public void unbind(T target) {
        target.mToolbar = null;
    }
}
