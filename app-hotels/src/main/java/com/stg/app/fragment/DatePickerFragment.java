package com.stg.app.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.stg.app.R;

import butterknife.ButterKnife;

/**
 * @author ortal
 * @date 2015-04-22
 */
public class DatePickerFragment extends BaseFragment {
    Button mSelectButton;


    public static DatePickerFragment newInstance() {
        return new DatePickerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datepicker, container, false);
        injectViews(view);

        mSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();


            }
        });

        return view;
    }

    private void injectViews(View view) {
        mSelectButton = ButterKnife.findById(view, R.id.date_select_button);

    }

    public interface Listener {

    }


}
