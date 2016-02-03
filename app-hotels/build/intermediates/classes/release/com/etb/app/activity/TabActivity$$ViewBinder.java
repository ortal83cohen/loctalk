// Generated code from Butter Knife. Do not modify!
package com.etb.app.activity;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TabActivity$$ViewBinder<T extends com.etb.app.activity.TabActivity> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131689615, "field 'mViewPager'");
        target.mViewPager = finder.castView(view, 2131689615, "field 'mViewPager'");
        view = finder.findRequiredView(source, 2131689614, "field 'mTabLayout'");
        target.mTabLayout = finder.castView(view, 2131689614, "field 'mTabLayout'");
    }

    @Override
    public void unbind(T target) {
        target.mViewPager = null;
        target.mTabLayout = null;
    }
}
