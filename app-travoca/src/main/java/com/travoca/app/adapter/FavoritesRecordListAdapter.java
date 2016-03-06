package com.travoca.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travoca.api.model.Record;
import com.travoca.app.R;
import com.travoca.app.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ortal
 * @date 2015-10-26
 */
public class FavoritesRecordListAdapter extends RecordListAdapter {
    private static final int HEADER = 0;
    private static final int OTHER = 1;
    List<Record> mAccommodationsWithoutDates = new ArrayList<>();

    public FavoritesRecordListAdapter(BaseActivity context, RecordViewHolder.Listener listener) {
        super(context, listener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == HEADER) {
            return new ViewHolder(
                    inflater.inflate(R.layout.results_record_list_header, parent, false)
            );
        }

        View view = inflater.inflate(R.layout.results_record_list_item, parent, false);
        return new RecordViewHolder(view, mContext, mPictureWidth, mPictureHeight, mListener);

    }

    @Override
    public int getItemViewType(int position) {
        if (position == mRecords.size())
            return HEADER;
        else
            return OTHER;
    }

    @Override
    public int getItemCount() {
        if (items().size() > mRecords.size()) {
            return items().size() + 1;
        }
        return items().size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position > mRecords.size()) {
            position = position - 1;
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public long getItemId(int position) {
        if (position > mRecords.size()) {
            position = position - 1;
        }
        return super.getItemId(position);
    }

    protected List<Record> items() {

        if (mRecords.isEmpty() || mAccommodationsWithoutDates.isEmpty()) {
            return mRecords;
        }
        List<Record> items = new ArrayList<>();
        items.addAll(mRecords);
        for (Record accWithoutDates : mAccommodationsWithoutDates) {
            boolean nonExistFlag = true;
            for (Record acc : items) {
                if (accWithoutDates.id == acc.id) {
                    nonExistFlag = false;
                    break;
                }
            }
            if (nonExistFlag) {
//                accWithoutDates.markUnavailable();
                items.add(accWithoutDates);
            }
        }
        return items;
    }

    public void addRecords(List<Record> recordses, boolean withoutDates) {
        int objectsCount = getItemCount();
        int accommodationsCount = recordses == null ? 0 : recordses.size();
        if (recordses != null) {
            mRecords.addAll(recordses);
            if (withoutDates) {
                mAccommodationsWithoutDates.addAll(recordses);
            }
        }
        notifyItemRangeInserted(objectsCount, accommodationsCount);
    }

}
