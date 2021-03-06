package com.travoca.app.fragment;

import com.travoca.app.App;
import com.travoca.app.activity.BaseActivity;
import com.travoca.app.model.RecordListRequest;
import com.travoca.app.preferences.UserPreferences;

import android.support.v4.app.Fragment;

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
        return ((BaseActivity) getActivity()).getRecordsRequest();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
