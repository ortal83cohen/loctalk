// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ResultsSortFragment$$ViewBinder<T extends com.etb.app.fragment.ResultsSortFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689786, "field 'mRadioGroup'");
    target.mRadioGroup = finder.castView(view, 2131689786, "field 'mRadioGroup'");
    view = finder.findRequiredView(source, 2131689784, "method 'beck'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.beck(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689785, "method 'doNothing'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.doNothing(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mRadioGroup = null;
  }
}
