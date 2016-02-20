package com.travoca.app.adapter;

import android.content.Context;

import com.travoca.api.contract.CreditCard;
import com.travoca.app.R;

/**
 * @author ortal
 * @date 2015-05-04
 */
public class CreditCardAdapter extends SpinnerAdapter {
    private static final String[] CODES = new String[]{
            CreditCard.VISA,
            CreditCard.MASTERCARD,
            CreditCard.DINERS_CLUB,
            CreditCard.JCB,
            CreditCard.AMEX,
            CreditCard.DISCOVER
    };

    public CreditCardAdapter(Context context) {
        super(context, R.array.cctypes_array);
    }

    public String getCardCode(int position) {
        if (position < CODES.length) {
            return CODES[position];
        }
        return null;
    }
}
