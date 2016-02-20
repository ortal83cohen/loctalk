package com.travoca.app.widget;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import com.travoca.app.R;
import com.travoca.app.activity.BaseActivity;
import com.travoca.app.fragment.NavigationFragment;

/**
 * @author ortal
 * @date 2015-04-19
 */
public class NavigationDrawer {

    public static final int NAV_RECENT_SEARCH = 0;
    public static final int NAV_FAVORITES = 1;
    public static final int NAV_NEW_RECORD =2;
    public static final int NAV_SCANNER = 8;
    public static final int NAV_SETTING = 5;


    public static final String CARDS_SCREEN_FRAGMENT = "cards_screen_fragment";
    private final BaseActivity mActivity;
    private int mCurrentTab = -1;

    public NavigationDrawer(BaseActivity baseActivity) {
        mActivity = baseActivity;
        //setup the NavigationDrawer
        NavigationFragment drawerFragment = (NavigationFragment)
                baseActivity.getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_navigation_drawer);
        if (drawerFragment != null) {
            drawerFragment.setUp(R.id.fragment_navigation_drawer,
                    (DrawerLayout) baseActivity.findViewById(R.id.drawer_layout),
                    baseActivity.getToolbar());
        }
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public void setCurrentTab(int tab) {
        mCurrentTab = tab;
    }

    public void change(int tab, Bundle extras) {

        mCurrentTab = tab;
        mActivity.onPageChange(tab, extras);

    }


}
