package com.stg.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.stg.app.R;
import com.squareup.picasso.Picasso;

/**
 * @author user
 * @date 2015-09-03
 */
public class SummaryImageViewHolder {
    private final Context mContext;
    ImageView mImage;
    TextView mSnippetTitle;
    RatingBar mRatingBar;

    public SummaryImageViewHolder(Context context, RatingBar ratingBar, TextView snippetTitle, ImageView image) {
        mContext = context;
        mImage = image;
        mSnippetTitle = snippetTitle;
        mRatingBar = ratingBar;
    }


    public void bind(String url, String name, int rating) {

        if (!TextUtils.isEmpty(url)) {
            Picasso.with(mContext)
                    .load(url)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.place_holder_img)
                    .into(mImage);
        }

        mSnippetTitle.setText(name);
        mRatingBar.setRating(rating);
    }

}