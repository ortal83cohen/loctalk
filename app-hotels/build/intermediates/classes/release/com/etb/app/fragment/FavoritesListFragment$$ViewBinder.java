// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FavoritesListFragment$$ViewBinder<T extends com.etb.app.fragment.FavoritesListFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 16908298, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 16908298, "field 'mRecyclerView'");
    view = finder.findRequiredView(source, 2131689849, "field 'mNoResult'");
    target.mNoResult = finder.castView(view, 2131689849, "field 'mNoResult'");
    view = finder.findRequiredView(source, 2131689606, "field 'mLoaderImage'");
    target.mLoaderImage = finder.castView(view, 2131689606, "field 'mLoaderImage'");
    view = finder.findRequiredView(source, 2131689710, "field 'mLoaderText'");
    target.mLoaderText = finder.castView(view, 2131689710, "field 'mLoaderText'");
    view = finder.findRequiredView(source, 2131689851, "field 'mModifyPreferences'");
    target.mModifyPreferences = finder.castView(view, 2131689851, "field 'mModifyPreferences'");
    view = finder.findRequiredView(source, 2131689852, "field 'mResetFilters'");
    target.mResetFilters = finder.castView(view, 2131689852, "field 'mResetFilters'");
  }

  @Override public void unbind(T target) {
    target.mRecyclerView = null;
    target.mNoResult = null;
    target.mLoaderImage = null;
    target.mLoaderText = null;
    target.mModifyPreferences = null;
    target.mResetFilters = null;
  }
}
