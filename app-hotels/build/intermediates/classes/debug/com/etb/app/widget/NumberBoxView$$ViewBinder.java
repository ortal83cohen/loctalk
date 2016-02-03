// Generated code from Butter Knife. Do not modify!
package com.etb.app.widget;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NumberBoxView$$ViewBinder<T extends com.etb.app.widget.NumberBoxView> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 16908294, "field 'mIcon'");
    target.mIcon = finder.castView(view, 16908294, "field 'mIcon'");
    view = finder.findRequiredView(source, 2131624317, "field 'mValue'");
    target.mValue = finder.castView(view, 2131624317, "field 'mValue'");
    view = finder.findRequiredView(source, 16908308, "field 'mTitle'");
    target.mTitle = finder.castView(view, 16908308, "field 'mTitle'");
    view = finder.findRequiredView(source, 16908309, "field 'mSubtitle'");
    target.mSubtitle = finder.castView(view, 16908309, "field 'mSubtitle'");
  }

  @Override public void unbind(T target) {
    target.mIcon = null;
    target.mValue = null;
    target.mTitle = null;
    target.mSubtitle = null;
  }
}
