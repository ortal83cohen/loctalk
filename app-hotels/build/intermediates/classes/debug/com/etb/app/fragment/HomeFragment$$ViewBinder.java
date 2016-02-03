// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HomeFragment$$ViewBinder<T extends com.etb.app.fragment.HomeFragment> implements ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624179, "field 'mAutocompleteView'");
        target.mAutocompleteView = finder.castView(view, 2131624179, "field 'mAutocompleteView'");
        view = finder.findRequiredView(source, 2131624180, "field 'mAutocompleteViewClear'");
        target.mAutocompleteViewClear = finder.castView(view, 2131624180, "field 'mAutocompleteViewClear'");
        view = finder.findRequiredView(source, 2131624178, "field 'mInstructions'");
        target.mInstructions = finder.castView(view, 2131624178, "field 'mInstructions'");
        view = finder.findRequiredView(source, 2131624177, "field 'mTopBox'");
        target.mTopBox = finder.castView(view, 2131624177, "field 'mTopBox'");
        view = finder.findRequiredView(source, 2131624175, "field 'mBackground'");
        target.mBackground = view;
        view = finder.findRequiredView(source, 2131624176, "field 'mGroupButtonsHolder'");
        target.mGroupButtonsHolder = finder.castView(view, 2131624176, "field 'mGroupButtonsHolder'");
        view = finder.findRequiredView(source, 2131624181, "field 'mSearchHotelsButton' and method 'onSearchHotelsClick'");
        target.mSearchHotelsButton = finder.castView(view, 2131624181, "field 'mSearchHotelsButton'");
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
        target.mTopBox = null;
        target.mBackground = null;
        target.mGroupButtonsHolder = null;
        target.mSearchHotelsButton = null;
    }
}
