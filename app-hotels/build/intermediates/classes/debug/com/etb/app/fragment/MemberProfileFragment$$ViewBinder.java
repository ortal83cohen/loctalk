// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MemberProfileFragment$$ViewBinder<T extends com.etb.app.fragment.MemberProfileFragment> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624264, "field 'mProfileImage'");
        target.mProfileImage = finder.castView(view, 2131624264, "field 'mProfileImage'");
        view = finder.findRequiredView(source, 2131624216, "field 'mFirstName'");
        target.mFirstName = finder.castView(view, 2131624216, "field 'mFirstName'");
        view = finder.findRequiredView(source, 2131624217, "field 'mLastName'");
        target.mLastName = finder.castView(view, 2131624217, "field 'mLastName'");
        view = finder.findRequiredView(source, 2131624122, "field 'mEmail'");
        target.mEmail = finder.castView(view, 2131624122, "field 'mEmail'");
        view = finder.findRequiredView(source, 2131624218, "field 'mBirthDate'");
        target.mBirthDate = finder.castView(view, 2131624218, "field 'mBirthDate'");
        view = finder.findRequiredView(source, 2131624212, "field 'mCountry'");
        target.mCountry = finder.castView(view, 2131624212, "field 'mCountry'");
        view = finder.findRequiredView(source, 2131624157, "field 'mPhoneNumber'");
        target.mPhoneNumber = finder.castView(view, 2131624157, "field 'mPhoneNumber'");
        view = finder.findRequiredView(source, 2131624214, "field 'mChangePasswordButton'");
        target.mChangePasswordButton = finder.castView(view, 2131624214, "field 'mChangePasswordButton'");
        view = finder.findRequiredView(source, 2131624215, "field 'mLogoutButton'");
        target.mLogoutButton = finder.castView(view, 2131624215, "field 'mLogoutButton'");
        view = finder.findRequiredView(source, 2131624265, "field 'mEditButton'");
        target.mEditButton = finder.castView(view, 2131624265, "field 'mEditButton'");
    }

    @Override
    public void unbind(T target) {
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
