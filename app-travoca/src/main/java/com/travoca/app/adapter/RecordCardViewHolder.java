package com.travoca.app.adapter;


import com.travoca.api.model.Record;
import com.travoca.app.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecordCardViewHolder extends RecyclerView.ViewHolder {


    @Bind(R.id.likes)
    public TextView mLikes;

    @Bind(R.id.unlikes)
    public TextView mUnLikes;

    @Bind(android.R.id.title)
    TextView mTitle;

    @Bind(R.id.description)
    TextView mDescription;

    @Bind(R.id.language)
    TextView mLanguage;

    @Bind(R.id.locationName)
    TextView mLocationName;

    @Bind(R.id.type)
    TextView mType;

    private Context mContext;


    public RecordCardViewHolder(View v, Context context) {
        super(v);
        ButterKnife.bind(this, v);
        mContext = context;
    }

    public void assignItem(Record item) {

        mTitle.setText(item.title);
        mDescription.setText("Description: " + item.description);
        mLanguage.setText("Language: " + item.lang);
        mLocationName.setText("Location: " + item.locationName);
        mType.setText("Type: " + item.type);
        mLikes.setText(String.valueOf(item.likes));
        mUnLikes.setText(String.valueOf(item.unLikes));


    }


    public void addLike() {
        mLikes.setText(String.valueOf(Integer.valueOf(mLikes.getText().toString()) + 1));
    }

    public void addDislike() {
        mUnLikes.setText(String.valueOf(Integer.valueOf(mUnLikes.getText().toString()) + 1));
    }
}