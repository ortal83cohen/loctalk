// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class LandmarksFilterFragment$$ViewInjector<T extends com.etb.app.fragment.LandmarksFilterFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624211, "field 'mAccTypesList'");
        target.mAccTypesList = finder.castView(view, 2131624211, "field 'mAccTypesList'");
        view = finder.findRequiredView(source, 2131624213, "field 'mApplyButton'");
        target.mApplyButton = finder.castView(view, 2131624213, "field 'mApplyButton'");
    }

    @Override
    public void reset(T target) {
        target.mAccTypesList = null;
        target.mApplyButton = null;
    }
}
