// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AddMoreBookingFragment$$ViewBinder<T extends com.etb.app.fragment.AddMoreBookingFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624143, "field 'mBookingNumber'");
    target.mBookingNumber = finder.castView(view, 2131624143, "field 'mBookingNumber'");
    view = finder.findRequiredView(source, 2131624126, "field 'mPassword'");
    target.mPassword = finder.castView(view, 2131624126, "field 'mPassword'");
    view = finder.findRequiredView(source, 2131624144, "field 'mSaveButton'");
    target.mSaveButton = finder.castView(view, 2131624144, "field 'mSaveButton'");
  }

  @Override public void unbind(T target) {
    target.mBookingNumber = null;
    target.mPassword = null;
    target.mSaveButton = null;
  }
}
