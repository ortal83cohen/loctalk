package com.travoca.app.adapter;


import android.content.Context;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.travoca.api.model.Record;
import com.travoca.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecordCardViewHolder extends RecyclerView.ViewHolder  {



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
    @Bind(R.id.likes)
    public TextView mLikes;
    @Bind(R.id.dis_likes)
    public TextView mDisLikes;


    private Context mContext;



    public RecordCardViewHolder(View v, Context context) {
        super(v);
        ButterKnife.bind(this, v);
        mContext = context;
    }

    public void assignItem(Record item) {

        mTitle.setText(item.title);
        mDescription.setText("Description: "+item.description);
        mLanguage.setText("Language: "+item.lang);
        mLocationName.setText("Location: "+item.locationName);
        mType.setText("Type: "+item.type);
        mLikes.setText(String.valueOf(item.likes));
        mDisLikes.setText(String.valueOf(item.unLikes));


    }


}