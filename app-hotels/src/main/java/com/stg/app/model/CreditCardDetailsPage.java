package com.stg.app.model;

import android.support.v4.app.Fragment;

import com.stg.app.analytics.AnalyticsCalls;
import com.stg.app.fragment.CreditCardDetailsFragment;
import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.ReviewItem;

import java.util.ArrayList;

/**
 * @author alex
 * @date 2015-05-03
 */
public class CreditCardDetailsPage extends AbstractPage {
    public static final String KEY = "card_details";
    public static final String DATA_CARD_NUMBER = "ccnum";
    public static final String DATA_CARD_NAME_FIRST = "ccnamefirst";
    public static final String DATA_CARD_NAME_LAST = "ccnamelast";
    public static final String DATA_CARD_CCV = "cccvc";
    public static final String DATA_CARD_EXP_MONTH = "ccexpmonth";
    public static final String DATA_CARD_EXP_YEAR = "ccexpyear";

    public CreditCardDetailsPage(ModelCallbacks callbacks) {
        super(callbacks, KEY);
        setRequired(true);
    }

    @Override
    public Fragment createFragment() {
        return CreditCardDetailsFragment.newInstance();
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> arrayList) {

    }

    @Override
    public void onPageSelected() {
        AnalyticsCalls.get().trackBookingFormPayment();
    }
}
