// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PoisFilterFragment$$ViewBinder<T extends com.etb.app.fragment.PoisFilterFragment> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131689778, "field 'mPoisTypesList'");
        target.mPoisTypesList = finder.castView(view, 2131689778, "field 'mPoisTypesList'");
        view = finder.findRequiredView(source, 2131689749, "field 'mApplyButton'");
        target.mApplyButton = finder.castView(view, 2131689749, "field 'mApplyButton'");
        view = finder.findRequiredView(source, 2131689777, "field 'mPoiMassage'");
        target.mPoiMassage = finder.castView(view, 2131689777, "field 'mPoiMassage'");
    }

    @Override
    public void unbind(T target) {
        target.mPoisTypesList = null;
        target.mApplyButton = null;
        target.mPoiMassage = null;
    }
}
