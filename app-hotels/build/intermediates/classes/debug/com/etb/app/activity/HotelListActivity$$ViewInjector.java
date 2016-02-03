// Generated code from Butter Knife. Do not modify!
package com.etb.app.activity;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HotelListActivity$$ViewInjector<T extends com.etb.app.activity.HotelListActivity> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624070, "field 'mToolbar'");
        target.mToolbar = finder.castView(view, 2131624070, "field 'mToolbar'");
        view = finder.findRequiredView(source, 2131624082, "field 'mButtonFilter'");
        target.mButtonFilter = finder.castView(view, 2131624082, "field 'mButtonFilter'");
        view = finder.findRequiredView(source, 2131624081, "field 'mButtonLandmarksFilter'");
        target.mButtonLandmarksFilter = finder.castView(view, 2131624081, "field 'mButtonLandmarksFilter'");
    }

    @Override
    public void reset(T target) {
        target.mToolbar = null;
        target.mButtonFilter = null;
        target.mButtonLandmarksFilter = null;
    }
}
