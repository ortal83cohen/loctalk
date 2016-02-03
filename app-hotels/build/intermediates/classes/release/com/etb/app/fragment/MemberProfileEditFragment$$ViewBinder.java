// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MemberProfileEditFragment$$ViewBinder<T extends com.etb.app.fragment.MemberProfileEditFragment> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131689758, "field 'mFirstName'");
        target.mFirstName = finder.castView(view, 2131689758, "field 'mFirstName'");
        view = finder.findRequiredView(source, 2131689759, "field 'mLastName'");
        target.mLastName = finder.castView(view, 2131689759, "field 'mLastName'");
        view = finder.findRequiredView(source, 2131689760, "field 'mDateOfBirth'");
        target.mDateOfBirth = finder.castView(view, 2131689760, "field 'mDateOfBirth'");
        view = finder.findRequiredView(source, 2131689761, "field 'mGenderFemale'");
        target.mGenderFemale = finder.castView(view, 2131689761, "field 'mGenderFemale'");
        view = finder.findRequiredView(source, 2131689762, "field 'mGenderMale'");
        target.mGenderMale = finder.castView(view, 2131689762, "field 'mGenderMale'");
        view = finder.findRequiredView(source, 2131689763, "field 'mGenderOther'");
        target.mGenderOther = finder.castView(view, 2131689763, "field 'mGenderOther'");
        view = finder.findRequiredView(source, 2131689680, "field 'mSaveButton'");
        target.mSaveButton = finder.castView(view, 2131689680, "field 'mSaveButton'");
    }

    @Override
    public void unbind(T target) {
        target.mFirstName = null;
        target.mLastName = null;
        target.mDateOfBirth = null;
        target.mGenderFemale = null;
        target.mGenderMale = null;
        target.mGenderOther = null;
        target.mSaveButton = null;
    }
}
