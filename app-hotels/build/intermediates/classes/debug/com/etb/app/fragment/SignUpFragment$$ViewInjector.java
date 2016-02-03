// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;

public class SignUpFragment$$ViewInjector<T extends com.etb.app.fragment.SignUpFragment> extends com.etb.app.fragment.LoginFragment$$ViewInjector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        super.inject(finder, target, source);

        View view;
        view = finder.findRequiredView(source, 2131624256, "field 'mPasswordConfirmView'");
        target.mPasswordConfirmView = finder.castView(view, 2131624256, "field 'mPasswordConfirmView'");
    }

    @Override
    public void reset(T target) {
        super.reset(target);

        target.mPasswordConfirmView = null;
    }
}
