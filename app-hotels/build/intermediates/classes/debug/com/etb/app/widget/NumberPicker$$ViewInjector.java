// Generated code from Butter Knife. Do not modify!
package com.etb.app.widget;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class NumberPicker$$ViewInjector<T extends com.etb.app.widget.NumberPicker> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        target.mButtons = Finder.listOf(
                finder.<android.widget.Button>findRequiredView(source, 2131624316, "field 'mButtons'"),
                finder.<android.widget.Button>findRequiredView(source, 2131624317, "field 'mButtons'"),
                finder.<android.widget.Button>findRequiredView(source, 2131624318, "field 'mButtons'"),
                finder.<android.widget.Button>findRequiredView(source, 2131624319, "field 'mButtons'"),
                finder.<android.widget.Button>findRequiredView(source, 2131624320, "field 'mButtons'"),
                finder.<android.widget.Button>findRequiredView(source, 2131624321, "field 'mButtons'")
        );
    }

    @Override
    public void reset(T target) {
        target.mButtons = null;
    }
}
