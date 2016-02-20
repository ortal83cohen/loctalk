package com.travoca.app.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.travoca.api.model.Record;
import com.travoca.app.R;
import com.travoca.app.activity.BaseActivity;
import com.travoca.app.model.RecordListRequest;
import com.travoca.app.utils.AppLog;
import com.travoca.app.widget.recyclerview.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecordListAdapter extends ArrayAdapter<Record, RecyclerView.ViewHolder> implements Filterable {
    protected final RecordViewHolder.Listener mListener;
    protected final int mPictureWidth;
    protected final int mPictureHeight;

    private final RecordListRequest mRequest;
    protected Context mContext;
    protected List<Record> mRecords = new ArrayList<>();

    public RecordListAdapter(BaseActivity activity, RecordViewHolder.Listener listener) {
        super(new ArrayList<Record>());
        mContext = activity;
        mListener = listener;
        Resources r = mContext.getResources();
        mPictureWidth = r.getDimensionPixelSize(R.dimen.listview_image_width);
        mPictureHeight = r.getDimensionPixelSize(R.dimen.listview_image_height);
        AppLog.d("Image size [" + mPictureWidth + "," + mPictureHeight + "]");
        mRequest = activity.getHotelsRequest();
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return items().get(position).id;
    }


    @Override
    public int getItemCount() {
        return items().size();
    }

    protected List<Record> items() {
        return mRecords;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.results_hotel_list_item, parent, false);
        return new RecordViewHolder(view, mContext, mPictureWidth, mPictureHeight, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Record item = items().get(position);
        if (item != null && holder instanceof RecordViewHolder) {
            ((RecordViewHolder) holder).assignItem(item, position);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                return new FilterResults();
            }
        };
    }

    @Override
    public void clear() {
        mRecords.clear();
        notifyDataSetChanged();
    }

    public void addHotels(List<Record> records) {
        int objectsCount = getItemCount();
        int accommodationsCount = records == null ? 0 : records.size();
        if (records != null) {
            mRecords.addAll(records);
        }
        notifyItemRangeInserted(objectsCount, accommodationsCount);
    }


}
