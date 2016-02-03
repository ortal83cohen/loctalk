// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MemberProfileFragment$$ViewBinder<T extends com.etb.app.fragment.MemberProfileFragment> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131689806, "field 'mProfileImage'");
        target.mProfileImage = finder.castView(view, 2131689806, "field 'mProfileImage'");
        view = finder.findRequiredView(source, 2131689758, "field 'mFirstName'");
        target.mFirstName = finder.castView(view, 2131689758, "field 'mFirstName'");
        view = finder.findRequiredView(source, 2131689759, "field 'mLastName'");
        target.mLastName = finder.castView(view, 2131689759, "field 'mLastName'");
        view = finder.findRequiredView(source, 2131689658, "field 'mEmail'");
        target.mEmail = finder.castView(view, 2131689658, "field 'mEmail'");
        view = finder.findRequiredView(source, 2131689760, "field 'mBirthDate'");
        target.mBirthDate = finder.castView(view, 2131689760, "field 'mBirthDate'");
        view = finder.findRequiredView(source, 2131689754, "field 'mCountry'");
        target.mCountry = finder.castView(view, 2131689754, "field 'mCountry'");
        view = finder.findRequiredView(source, 2131689693, "field 'mPhoneNumber'");
        target.mPhoneNumber = finder.castView(view, 2131689693, "field 'mPhoneNumber'");
        view = finder.findRequiredView(source, 2131689756, "field 'mChangePasswordButton'");
        target.mChangePasswordButton = finder.castView(view, 2131689756, "field 'mChangePasswordButton'");
        view = finder.findRequiredView(source, 2131689757, "field 'mLogoutButton'");
        target.mLogoutButton = finder.castView(view, 2131689757, "field 'mLogoutButton'");
        view = finder.findRequiredView(source, 2131689807, "field 'mEditButton'");
        target.mEditButton = finder.castView(view, 2131689807, "field 'mEditButton'");
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
