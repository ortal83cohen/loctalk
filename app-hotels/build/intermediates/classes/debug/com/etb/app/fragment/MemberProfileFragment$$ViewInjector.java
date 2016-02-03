// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MemberProfileFragment$$ViewInjector<T extends com.etb.app.fragment.MemberProfileFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624267, "field 'mProfileImage'");
        target.mProfileImage = finder.castView(view, 2131624267, "field 'mProfileImage'");
        view = finder.findRequiredView(source, 2131624222, "field 'mFirstName'");
        target.mFirstName = finder.castView(view, 2131624222, "field 'mFirstName'");
        view = finder.findRequiredView(source, 2131624223, "field 'mLastName'");
        target.mLastName = finder.castView(view, 2131624223, "field 'mLastName'");
        view = finder.findRequiredView(source, 2131624127, "field 'mEmail'");
        target.mEmail = finder.castView(view, 2131624127, "field 'mEmail'");
        view = finder.findRequiredView(source, 2131624224, "field 'mBirthDate'");
        target.mBirthDate = finder.castView(view, 2131624224, "field 'mBirthDate'");
        view = finder.findRequiredView(source, 2131624218, "field 'mCountry'");
        target.mCountry = finder.castView(view, 2131624218, "field 'mCountry'");
        view = finder.findRequiredView(source, 2131624160, "field 'mPhoneNumber'");
        target.mPhoneNumber = finder.castView(view, 2131624160, "field 'mPhoneNumber'");
        view = finder.findRequiredView(source, 2131624220, "field 'mChangePasswordButton'");
        target.mChangePasswordButton = finder.castView(view, 2131624220, "field 'mChangePasswordButton'");
        view = finder.findRequiredView(source, 2131624221, "field 'mLogoutButton'");
        target.mLogoutButton = finder.castView(view, 2131624221, "field 'mLogoutButton'");
        view = finder.findRequiredView(source, 2131624268, "field 'mEditButton'");
        target.mEditButton = finder.castView(view, 2131624268, "field 'mEditButton'");
    }

    @Override
    public void reset(T target) {
        target.mProfileImage = null;
        target.mFirstName = null;
        target.mLastName = null;
        target.mEmail = null;
        target.mBirthDate = null;
        target.mCountry = null;
        target.mPhoneNumber = null;
        target.mChangePasswordButton = null;
        target.mLogoutButton = null;
        target.mEditButton = null;
    }
}
