package com.stg.app.fragment;

import android.support.v4.app.Fragment;

import com.stg.app.App;
import com.stg.app.activity.BaseActivity;
import com.stg.app.model.RecordListRequest;
import com.stg.app.preferences.UserPreferences;

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

    public RecordListRequest getRequest() {
        return ((BaseActivity) getActivity()).getHotelsRequest();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
