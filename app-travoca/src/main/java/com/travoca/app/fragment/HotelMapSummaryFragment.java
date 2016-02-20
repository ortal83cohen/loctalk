package com.travoca.app.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travoca.api.model.Record;
import com.travoca.app.R;
import com.travoca.app.adapter.RecordViewHolder;
import com.travoca.app.model.RecordListRequest;

import butterknife.ButterKnife;

/**
 * @author ortal
 * @date 2015-05-25
 */
public class HotelMapSummaryFragment extends BaseFragment {

    private static final String EXTRA_DATA = "snippet";
    private Record mRecord;
    private RecordViewHolder.Listener mListener;

    public static HotelMapSummaryFragment newInstance(Record record) {
        HotelMapSummaryFragment fragment = new HotelMapSummaryFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_DATA, record);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_hotel_summary, container, false);
        ButterKnife.bind(this, view);

        mRecord = getArguments().getParcelable(EXTRA_DATA);

        Resources r = getActivity().getResources();
        int pictureWidth = r.getDimensionPixelSize(R.dimen.listview_image_width);
        int pictureHeight = r.getDimensionPixelSize(R.dimen.listview_image_height);

        RecordViewHolder recordViewHolder = new RecordViewHolder(view, getActivity(), pictureWidth, pictureHeight, mListener);
        RecordListRequest request = getRequest();
        recordViewHolder.assignItem(mRecord, 0);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (RecordViewHolder.Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement HotelMapSummaryFragment.Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_DATA, mRecord);
        super.onSaveInstanceState(outState);
    }

}
