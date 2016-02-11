package com.stg.app.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.socialtravelguide.api.model.Record;
import com.socialtravelguide.api.utils.ImageUtils;
import com.squareup.picasso.Picasso;
import com.stg.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecordViewHolder extends RecyclerView.ViewHolder {

    private final int mPictureWidth;
    private final int mPictureHeight;

    private final Context mContext;
    private final RecordViewHolder.Listener mListener;
    @Bind(R.id.image)
    public ImageView mImageView;
    @Bind(android.R.id.title)
    public TextView mTitleView;
    @Bind(R.id.likes)
    public TextView mLikes;
    @Bind(R.id.dis_likes)
    public TextView mDisLikes;
    @Bind(R.id.background)
    public FrameLayout mBackground;
    private Record mItem;
    private int mPosition;

    public RecordViewHolder(View v, Context context, int pictureWidth, int pictureHeight, RecordViewHolder.Listener listener) {
        super(v);
        mListener = listener;
        ButterKnife.bind(this, v);
        mPictureWidth = pictureWidth;
        mPictureHeight = pictureHeight;
        mContext = context;
    }

    public void assignItem(final Record item, int numberRooms, int position) {
        mPosition = position;
        mItem = item;
        final Resources r = mContext.getResources();
        if (mItem.imageUrl != null) {
            if (mPictureWidth > 0 && mPictureHeight > 0) {
                String imageUrl = ImageUtils.resizeUrl(mItem.imageUrl, mPictureWidth, mPictureHeight);
                Picasso.with(mContext)
                        .load(imageUrl)
                        .resize(mPictureWidth, mPictureHeight)
                        .centerCrop()
                        .placeholder(R.drawable.place_holder_img)
                        .into(mImageView);
            } else {
                Picasso.with(mContext)
                        .load(mItem.imageUrl)
                        .fit().centerCrop()
                        .placeholder(R.drawable.place_holder_img)
                        .into(mImageView);
            }
        } else {
            mImageView.setImageResource(R.drawable.place_holder_img);
        }
        mLikes.setText(String.valueOf(mItem.likes));
        mDisLikes.setText(String.valueOf(mItem.unLikes));
        mTitleView.setText(mItem.title);

        mTitleView.setTag(position);

        setViewEnabled(r);

    }

    private void setViewEnabled(Resources r) {
        mBackground.setEnabled(true);
        mTitleView.setTextColor(r.getColor(android.R.color.black));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRecordClick(mItem, mPosition);
            }
        });
        mImageView.setColorFilter(null);

    }

    private void setViewDisabled(Resources r) {

        mBackground.setEnabled(false);
        mTitleView.setTextColor(r.getColor(android.R.color.darker_gray));
        itemView.setOnClickListener(null);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        mImageView.setColorFilter(filter);
    }

    public interface Listener {
        void onRecordClick(Record acc, int position);
    }

}