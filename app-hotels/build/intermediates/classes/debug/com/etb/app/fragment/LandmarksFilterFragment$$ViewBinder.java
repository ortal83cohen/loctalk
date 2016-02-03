// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LandmarksFilterFragment$$ViewBinder<T extends com.etb.app.fragment.LandmarksFilterFragment> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624200, "field 'mAccTypesList'");
        target.mAccTypesList = finder.castView(view, 2131624200, "field 'mAccTypesList'");
        view = finder.findRequiredView(source, 2131624202, "field 'mApplyButton'");
        target.mApplyButton = finder.castView(view, 2131624202, "field 'mApplyButton'");
    }

    @Override
    public void unbind(T target) {
        target.mAccTypesList = null;
        target.mApplyButton = null;
    }
}
