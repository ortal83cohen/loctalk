// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;

public class SignUpFragment$$ViewBinder<T extends com.etb.app.fragment.SignUpFragment> extends com.etb.app.fragment.LoginFragment$$ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        super.bind(finder, target, source);

        View view;
        view = finder.findRequiredView(source, 2131624253, "field 'mPasswordConfirmView'");
        target.mPasswordConfirmView = finder.castView(view, 2131624253, "field 'mPasswordConfirmView'");
    }

    @Override
    public void unbind(T target) {
        super.unbind(target);

        target.mPasswordConfirmView = null;
    }
}
