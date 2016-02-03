// Generated code from Butter Knife. Do not modify!
package com.etb.app.activity;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class TabActivity$$ViewInjector<T extends com.etb.app.activity.TabActivity> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624084, "field 'mViewPager'");
        target.mViewPager = finder.castView(view, 2131624084, "field 'mViewPager'");
        view = finder.findRequiredView(source, 2131624083, "field 'mTabLayout'");
        target.mTabLayout = finder.castView(view, 2131624083, "field 'mTabLayout'");
    }

    @Override
    public void reset(T target) {
        target.mViewPager = null;
        target.mTabLayout = null;
    }
}
