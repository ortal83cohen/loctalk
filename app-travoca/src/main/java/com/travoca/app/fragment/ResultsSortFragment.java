package com.travoca.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.travoca.api.contract.Sort;
import com.travoca.api.model.SearchRequest;
import com.travoca.app.R;
import com.travoca.app.activity.RecordListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ortal
 * @date 2015-05-25
 */
public class ResultsSortFragment extends BaseFragment {

    @Bind(R.id.radio_sort_group)
    RadioGroup mRadioGroup;

    public static ResultsSortFragment newInstance() {
        return new ResultsSortFragment();
    }

    public static Spanned typeToText(String type) {
        switch (type) {
            case Sort.Type.DISTANCE:
                return Html.fromHtml("<b>Distance</b>");
            case Sort.Type.RATING_HIGH_TO_LOW:
                return Html.fromHtml("<b>Guest Reviews</b> - Best to worst");
            case Sort.Type.RATING_LOW_TO_HIGH:
                return Html.fromHtml("<b>Guest Reviews</b> - Worst to best");
            default:
                return Html.fromHtml("");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results_sort, container, false);
        ButterKnife.bind(this, view);
        SearchRequest recordsRequest = getRequest();
        if (recordsRequest.getSort() != null) {
            mRadioGroup.check(typeToId(recordsRequest.getSort().type));
        }
        addListenerOnButton();
        for (View v : mRadioGroup.getTouchables()) {
            ((RadioButton) v).setText(typeToText(v.getTag().toString()));
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private int typeToId(String type) {
        switch (type) {
//            case Sort.Type.FAVORITES:
//                return R.id.radio_popularity;
//            case Sort.Type.PRICE_HIGH_TO_LOW:
//                return R.id.radio_price_high_to_low;
//            case Sort.Type.PRICE_LOW_TO_HIGH:
//                return R.id.radio_price_low_to_high;
//            case Sort.Type.STARS_HIGH_TO_LOW:
//                return R.id.radio_stars_high_to_low;
//            case Sort.Type.STARS_LOW_TO_HIGH:
//                return R.id.radio_stars_low_to_high;
            case Sort.Type.RATING_HIGH_TO_LOW:
                return R.id.radio_guest_high_to_low;
            case Sort.Type.RATING_LOW_TO_HIGH:
                return R.id.radio_guest_low_to_high;
            case Sort.Type.DISTANCE:
                return R.id.radio_distance;
            default:
                return -1;
        }
    }

    private void addListenerOnButton() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
                // find the radiobutton by returned id
                RadioButton radiuselected = (RadioButton) rGroup.findViewById(checkedId);
                selectSort(radiuselected);
            }

        });
    }

    private void selectSort(RadioButton radiuselected) {
        if (radiuselected != null) {
            SearchRequest recordsRequest = getRequest();

            recordsRequest.setSort(radiuselected.getTag().toString());
            ((RecordListActivity) getActivity()).refreshList();
            ((RecordListActivity) getActivity()).remove(this);
        }
    }

    @OnClick(R.id.filter_frame)
    public void beck(View view) {
        ((RecordListActivity) getActivity()).remove(this);
    }

    @OnClick(R.id.sort_frame)
    public void doNothing(View view) {
    }
}
