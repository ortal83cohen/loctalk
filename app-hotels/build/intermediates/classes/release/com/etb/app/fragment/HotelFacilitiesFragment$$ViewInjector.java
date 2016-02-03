// Generated code from Butter Knife. Do not modify!
package com.etb.app.fragment;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HotelFacilitiesFragment$$ViewInjector<T extends com.etb.app.fragment.HotelFacilitiesFragment> implements Injector<T> {
    @Override
    public void inject(final Finder finder, final T target, Object source) {
        View view;
        view = finder.findRequiredView(source, 2131624190, "field 'mHotelDetailsText'");
        target.mHotelDetailsText = finder.castView(view, 2131624190, "field 'mHotelDetailsText'");
        view = finder.findRequiredView(source, 2131624194, "field 'mPaymentMethodsText'");
        target.mPaymentMethodsText = finder.castView(view, 2131624194, "field 'mPaymentMethodsText'");
        view = finder.findRequiredView(source, 2131624195, "field 'mServices'");
        target.mServices = finder.castView(view, 2131624195, "field 'mServices'");
        view = finder.findRequiredView(source, 2131624196, "field 'mGeneralFacilities'");
        target.mGeneralFacilities = finder.castView(view, 2131624196, "field 'mGeneralFacilities'");
        view = finder.findRequiredView(source, 2131624192, "field 'mFacilities'");
        target.mFacilities = finder.castView(view, 2131624192, "field 'mFacilities'");
        view = finder.findRequiredView(source, 2131624193, "field 'mPaymentMethodsImages'");
        target.mPaymentMethodsImages = finder.castView(view, 2131624193, "field 'mPaymentMethodsImages'");
        view = finder.findRequiredView(source, 2131624191, "method 'onReadMoreDescriptionPressed'");
        view.setOnClickListener(
                new butterknife.internal.DebouncingOnClickListener() {
                    @Override
                    public void doClick(
                            android.view.View p0
                    ) {
                        target.onReadMoreDescriptionPressed(finder.<android.widget.Button>castParam(p0, "doClick", 0, "onReadMoreDescriptionPressed", 0));
                    }
                });
    }

    @Override
    public void reset(T target) {
        target.mHotelDetailsText = null;
        target.mPaymentMethodsText = null;
        target.mServices = null;
        target.mGeneralFacilities = null;
        target.mFacilities = null;
        target.mPaymentMethodsImages = null;
    }
}
