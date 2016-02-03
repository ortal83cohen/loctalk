// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class LoginFragment$$ViewInjector<T extends com.etb.app.fragment.LoginFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624214, "field 'mFacebookLoginButton'");
        target.mFacebookLoginButton = finder.castView(view, 2131624214, "field 'mFacebookLoginButton'");
        view = finder.findRequiredView(source, 2131624215, "field 'mGoogleLoginButton'");
        target.mGoogleLoginButton = finder.castView(view, 2131624215, "field 'mGoogleLoginButton'");
        view = finder.findRequiredView(source, 2131624127, "field 'mEmailView'");
        target.mEmailView = finder.castView(view, 2131624127, "field 'mEmailView'");
        view = finder.findRequiredView(source, 2131624131, "field 'mPasswordView'");
        target.mPasswordView = finder.castView(view, 2131624131, "field 'mPasswordView'");
        view = finder.findRequiredView(source, 2131624157, "field 'mLoginButton'");
        target.mLoginButton = finder.castView(view, 2131624157, "field 'mLoginButton'");
    }

    @Override
    public void reset(T target) {
        target.mFacebookLoginButton = null;
        target.mGoogleLoginButton = null;
        target.mEmailView = null;
        target.mPasswordView = null;
        target.mLoginButton = null;
    }
}
