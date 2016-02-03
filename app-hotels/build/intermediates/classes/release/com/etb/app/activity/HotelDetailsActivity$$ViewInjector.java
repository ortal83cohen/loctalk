// Generated code from Butter Knife. Do not modify!
package com.etb.app.activity;

import android.view.View;

import butterknife.ButterKnife.Finder;

public class HotelDetailsActivity$$ViewInjector<T extends com.etb.app.activity.HotelDetailsActivity> extends com.etb.app.activity.TabActivity$$ViewInjector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        super.inject(finder, target, source);

        View view;
        view = finder.findRequiredView(source, 2131624077, "field 'mAllRoomsButton' and method 'onClickAvailableRooms'");
        target.mAllRoomsButton = finder.castView(view, 2131624077, "field 'mAllRoomsButton'");
        view.setOnClickListener(
                new butterknife.internal.DebouncingOnClickListener() {
                    @Override
                    public void doClick(
                            android.view.View p0
                    ) {
                        target.onClickAvailableRooms(finder.<android.widget.Button>castParam(p0, "doClick", 0, "onClickAvailableRooms", 0));
                    }
                });
    }

    @Override
    public void reset(T target) {
        super.reset(target);

        target.mAllRoomsButton = null;
    }
}
