// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MemberBookingsListFragment$$ViewInjector<T extends com.etb.app.fragment.MemberBookingsListFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 16908298, "field 'mRecyclerView'");
        target.mRecyclerView = finder.castView(view, 16908298, "field 'mRecyclerView'");
        view = finder.findRequiredView(source, 2131624217, "field 'mMoreBooking'");
        target.mMoreBooking = finder.castView(view, 2131624217, "field 'mMoreBooking'");
        view = finder.findRequiredView(source, 16908292, "field 'mEmptyView'");
        target.mEmptyView = view;
    }

    @Override
    public void reset(T target) {
        target.mRecyclerView = null;
        target.mMoreBooking = null;
        target.mEmptyView = null;
    }
}
