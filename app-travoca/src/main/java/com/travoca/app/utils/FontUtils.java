package com.travoca.app.utils;

import com.travoca.app.R;

import android.content.Context;
import android.graphics.Typeface;

import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * @author ortal
 * @date 2015-05-10
 */
public class FontUtils {

    public static Typeface loadFontReqular(Context context) {
        return TypefaceUtils
                .load(context.getAssets(), context.getResources().getString(R.string.font_reqular));
    }

    public static Typeface loadFontMedium(Context context) {
        return TypefaceUtils
                .load(context.getAssets(), context.getResources().getString(R.string.font_medium));
    }

    public static Typeface loadFontBold(Context context) {
        return TypefaceUtils
                .load(context.getAssets(), context.getResources().getString(R.string.font_bold));
    }
}
