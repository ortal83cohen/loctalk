// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HomeFragment$$ViewBinder<T extends com.etb.app.fragment.HomeFragment> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131689715, "field 'mAutocompleteView'");
        target.mAutocompleteView = finder.castView(view, 2131689715, "field 'mAutocompleteView'");
        view = finder.findRequiredView(source, 2131689716, "field 'mAutocompleteViewClear'");
        target.mAutocompleteViewClear = finder.castView(view, 2131689716, "field 'mAutocompleteViewClear'");
        view = finder.findRequiredView(source, 2131689714, "field 'mInstructions'");
        target.mInstructions = finder.castView(view, 2131689714, "field 'mInstructions'");
        view = finder.findRequiredView(source, 2131689721, "field 'mNumberPersonsSelector'");
        target.mNumberPersonsSelector = finder.castView(view, 2131689721, "field 'mNumberPersonsSelector'");
        view = finder.findRequiredView(source, 2131689722, "field 'mNumberRoomsSelector'");
        target.mNumberRoomsSelector = finder.castView(view, 2131689722, "field 'mNumberRoomsSelector'");
        view = finder.findRequiredView(source, 2131689720, "field 'mRoomsButton'");
        target.mRoomsButton = finder.castView(view, 2131689720, "field 'mRoomsButton'");
        view = finder.findRequiredView(source, 2131689717, "field 'mCheckInButton'");
        target.mCheckInButton = finder.castView(view, 2131689717, "field 'mCheckInButton'");
        view = finder.findRequiredView(source, 2131689718, "field 'mCheckOutButton'");
        target.mCheckOutButton = finder.castView(view, 2131689718, "field 'mCheckOutButton'");
        view = finder.findRequiredView(source, 2131689719, "field 'mPersonsButton'");
        target.mPersonsButton = finder.castView(view, 2131689719, "field 'mPersonsButton'");
        view = finder.findRequiredView(source, 2131689708, "field 'mDatePicker'");
        target.mDatePicker = finder.castView(view, 2131689708, "field 'mDatePicker'");
        view = finder.findRequiredView(source, 2131689713, "field 'mTopBox'");
        target.mTopBox = finder.castView(view, 2131689713, "field 'mTopBox'");
        view = finder.findRequiredView(source, 2131689711, "field 'mBackground'");
        target.mBackground = view;
        view = finder.findRequiredView(source, 2131689712, "field 'mGroupButtonsHolder'");
        target.mGroupButtonsHolder = finder.castView(view, 2131689712, "field 'mGroupButtonsHolder'");
        view = finder.findRequiredView(source, 2131689723, "field 'mSearchHotelsButton' and method 'onSearchHotelsClick'");
        target.mSearchHotelsButton = finder.castView(view, 2131689723, "field 'mSearchHotelsButton'");
        view.setOnClickListener(
                new butterknife.internal.DebouncingOnClickListener() {
                    @Override
                    public void doClick(
                            android.view.View p0
                    ) {
                        target.onSearchHotelsClick(p0);
                    }
                });
    }

    @Override
    public void unbind(T target) {
        target.mAutocompleteView = null;
        target.mAutocompleteViewClear = null;
        target.mInstructions = null;
        target.mNumberPersonsSelector = null;
        target.mNumberRoomsSelector = null;
        target.mRoomsButton = null;
        target.mCheckInButton = null;
        target.mCheckOutButton = null;
        target.mPersonsButton = null;
        target.mDatePicker = null;
        target.mTopBox = null;
        target.mBackground = null;
        target.mGroupButtonsHolder = null;
        target.mSearchHotelsButton = null;
    }
}
