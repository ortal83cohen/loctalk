// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MemberProfileEditFragment$$ViewInjector<T extends com.etb.app.fragment.MemberProfileEditFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624213, "field 'mFirstName'");
        target.mFirstName = finder.castView(view, 2131624213, "field 'mFirstName'");
        view = finder.findRequiredView(source, 2131624214, "field 'mLastName'");
        target.mLastName = finder.castView(view, 2131624214, "field 'mLastName'");
        view = finder.findRequiredView(source, 2131624215, "field 'mDateOfBirth'");
        target.mDateOfBirth = finder.castView(view, 2131624215, "field 'mDateOfBirth'");
        view = finder.findRequiredView(source, 2131624216, "field 'mGenderFemale'");
        target.mGenderFemale = finder.castView(view, 2131624216, "field 'mGenderFemale'");
        view = finder.findRequiredView(source, 2131624217, "field 'mGenderMale'");
        target.mGenderMale = finder.castView(view, 2131624217, "field 'mGenderMale'");
        view = finder.findRequiredView(source, 2131624218, "field 'mGenderOther'");
        target.mGenderOther = finder.castView(view, 2131624218, "field 'mGenderOther'");
        view = finder.findRequiredView(source, 2131624138, "field 'mSaveButton'");
        target.mSaveButton = finder.castView(view, 2131624138, "field 'mSaveButton'");
    }

    @Override
    public void reset(T target) {
        target.mFirstName = null;
        target.mLastName = null;
        target.mDateOfBirth = null;
        target.mGenderFemale = null;
        target.mGenderMale = null;
        target.mGenderOther = null;
        target.mSaveButton = null;
    }
}
