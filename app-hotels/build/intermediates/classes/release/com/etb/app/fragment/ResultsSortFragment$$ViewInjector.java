// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ResultsSortFragment$$ViewInjector<T extends com.etb.app.fragment.ResultsSortFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624238, "field 'mRadioGroup'");
        target.mRadioGroup = finder.castView(view, 2131624238, "field 'mRadioGroup'");
        view = finder.findRequiredView(source, 2131624236, "method 'beck'");
        view.setOnClickListener(
                new butterknife.internal.DebouncingOnClickListener() {
                    @Override
                    public void doClick(
                            android.view.View p0
                    ) {
                        target.beck(p0);
                    }
                });
        view = finder.findRequiredView(source, 2131624237, "method 'doNothing'");
        view.setOnClickListener(
                new butterknife.internal.DebouncingOnClickListener() {
                    @Override
                    public void doClick(
                            android.view.View p0
                    ) {
                        target.doNothing(p0);
                    }
                });
    }

    @Override
    public void reset(T target) {
        target.mRadioGroup = null;
    }
}
