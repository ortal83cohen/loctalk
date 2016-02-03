// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class AddMoreBookingFragment$$ViewInjector<T extends com.etb.app.fragment.AddMoreBookingFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624146, "field 'mBookingNumber'");
        target.mBookingNumber = finder.castView(view, 2131624146, "field 'mBookingNumber'");
        view = finder.findRequiredView(source, 2131624131, "field 'mPassword'");
        target.mPassword = finder.castView(view, 2131624131, "field 'mPassword'");
        view = finder.findRequiredView(source, 2131624147, "field 'mSaveButton'");
        target.mSaveButton = finder.castView(view, 2131624147, "field 'mSaveButton'");
    }

    @Override
    public void reset(T target) {
        target.mBookingNumber = null;
        target.mPassword = null;
        target.mSaveButton = null;
    }
}
