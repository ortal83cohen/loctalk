package com.stg.app.fragment;

import android.support.v4.app.Fragment;

import com.stg.app.App;
import com.stg.app.activity.BaseActivity;
import com.stg.app.model.HotelListRequest;
import com.stg.app.preferences.UserPreferences;
import com.stg.app.utils.PriceRender;

/**
 * @author ortal
 * @date 2015-07-01
 */
public abstract class BaseFragment extends Fragment {

    public UserPreferences getUserPrefs() {
        return App.provide(getActivity()).getUserPrefs();
    }

    public String getCurrencyCode() {
        return getUserPrefs().getCurrencyCode();
    }

    public PriceRender getPriceRender() {
        return ((BaseActivity) getActivity()).getPriceRender();
    }

    public HotelListRequest getHotelsRequest() {
        return ((BaseActivity) getActivity()).getHotelsRequest();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        RefWatcher refWatcher = HotelsApplication.get(getActivity()).getRefWatcher();
//        refWatcher.watch(this);
    }
}
