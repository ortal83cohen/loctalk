// Generated code from Butter Knife. Do not modify!
package com.etb.app.widget;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NumberPicker$$ViewBinder<T extends com.etb.app.widget.NumberPicker> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    target.mButtons = Finder.listOf(
        finder.<android.widget.Button>findRequiredView(source, 2131624318, "field 'mButtons'"),
        finder.<android.widget.Button>findRequiredView(source, 2131624319, "field 'mButtons'"),
        finder.<android.widget.Button>findRequiredView(source, 2131624320, "field 'mButtons'"),
        finder.<android.widget.Button>findRequiredView(source, 2131624321, "field 'mButtons'"),
        finder.<android.widget.Button>findRequiredView(source, 2131624322, "field 'mButtons'"),
        finder.<android.widget.Button>findRequiredView(source, 2131624323, "field 'mButtons'")
    );
  }

  @Override public void unbind(T target) {
    target.mButtons = null;
  }
}
