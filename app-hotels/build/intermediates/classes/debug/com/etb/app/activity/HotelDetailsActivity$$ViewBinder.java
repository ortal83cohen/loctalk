// Generated code from Butter Knife. Do not modify!
package com.etb.app.activity;

import android.view.View;

import butterknife.ButterKnife.Finder;

public class HotelDetailsActivity$$ViewBinder<T extends com.etb.app.activity.HotelDetailsActivity> extends com.etb.app.activity.TabActivity$$ViewBinder<T> {
    @Override
    public void bind(final Finder finder, final T target, Object source) {
        super.bind(finder, target, source);

        View view;
        view = finder.findRequiredView(source, 2131624081, "field 'mAllRoomsButton' and method 'onClickAvailableRooms'");
        target.mAllRoomsButton = finder.castView(view, 2131624081, "field 'mAllRoomsButton'");
        view.setOnClickListener(
                new butterknife.internal.DebouncingOnClickListener() {
                    @Override
                    public void doClick(
                            android.view.View p0
                    ) {
                        target.onClickAvailableRooms(finder.<android.widget.Button>castParam(p0, "doClick", 0, "onClickAvailableRooms", 0));
                    }
                });
        view = finder.findRequiredView(source, 2131624080, "field 'mShowLandmarks' and method 'onClickShowLandmarks'");
        target.mShowLandmarks = finder.castView(view, 2131624080, "field 'mShowLandmarks'");
        view.setOnClickListener(
                new butterknife.internal.DebouncingOnClickListener() {
                    @Override
                    public void doClick(
                            android.view.View p0
                    ) {
                        target.onClickShowLandmarks(finder.<android.widget.Button>castParam(p0, "doClick", 0, "onClickShowLandmarks", 0));
                    }
                });
    }

    @Override
    public void unbind(T target) {
        super.unbind(target);

        target.mAllRoomsButton = null;
        target.mShowLandmarks = null;
    }
}
