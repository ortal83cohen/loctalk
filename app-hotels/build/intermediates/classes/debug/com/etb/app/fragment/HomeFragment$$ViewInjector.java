// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HomeFragment$$ViewInjector<T extends com.etb.app.fragment.HomeFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624180, "field 'mAutocompleteView'");
        target.mAutocompleteView = finder.castView(view, 2131624180, "field 'mAutocompleteView'");
        view = finder.findRequiredView(source, 2131624181, "field 'mAutocompleteViewClear'");
        target.mAutocompleteViewClear = finder.castView(view, 2131624181, "field 'mAutocompleteViewClear'");
        view = finder.findRequiredView(source, 2131624179, "field 'mInstructions'");
        target.mInstructions = finder.castView(view, 2131624179, "field 'mInstructions'");
        view = finder.findRequiredView(source, 2131624186, "field 'mNumberPersonsSelector'");
        target.mNumberPersonsSelector = finder.castView(view, 2131624186, "field 'mNumberPersonsSelector'");
        view = finder.findRequiredView(source, 2131624187, "field 'mNumberRoomsSelector'");
        target.mNumberRoomsSelector = finder.castView(view, 2131624187, "field 'mNumberRoomsSelector'");
        view = finder.findRequiredView(source, 2131624185, "field 'mRoomsButton'");
        target.mRoomsButton = finder.castView(view, 2131624185, "field 'mRoomsButton'");
        view = finder.findRequiredView(source, 2131624182, "field 'mCheckInButton'");
        target.mCheckInButton = finder.castView(view, 2131624182, "field 'mCheckInButton'");
        view = finder.findRequiredView(source, 2131624183, "field 'mCheckOutButton'");
        target.mCheckOutButton = finder.castView(view, 2131624183, "field 'mCheckOutButton'");
        view = finder.findRequiredView(source, 2131624184, "field 'mPersonsButton'");
        target.mPersonsButton = finder.castView(view, 2131624184, "field 'mPersonsButton'");
        view = finder.findRequiredView(source, 2131624173, "field 'mDatePicker'");
        target.mDatePicker = finder.castView(view, 2131624173, "field 'mDatePicker'");
        view = finder.findRequiredView(source, 2131624178, "field 'mTopBox'");
        target.mTopBox = finder.castView(view, 2131624178, "field 'mTopBox'");
        view = finder.findRequiredView(source, 2131624176, "field 'mBackground'");
        target.mBackground = finder.castView(view, 2131624176, "field 'mBackground'");
        view = finder.findRequiredView(source, 2131624177, "field 'mGroupButtonsHolder'");
        target.mGroupButtonsHolder = finder.castView(view, 2131624177, "field 'mGroupButtonsHolder'");
        view = finder.findRequiredView(source, 2131624188, "field 'mSearchHotelsButton' and method 'onSearchHotelsClick'");
        target.mSearchHotelsButton = finder.castView(view, 2131624188, "field 'mSearchHotelsButton'");
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
    public void reset(T target) {
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
