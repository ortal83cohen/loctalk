// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RoomListFragment$$ViewBinder<T extends com.etb.app.fragment.RoomListFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 16908298, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 16908298, "field 'mRecyclerView'");
  }

  @Override public void unbind(T target) {
    target.mRecyclerView = null;
  }
}
