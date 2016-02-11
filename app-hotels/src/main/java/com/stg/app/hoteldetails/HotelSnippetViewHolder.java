package com.stg.app.hoteldetails;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.stg.app.R;
import com.stg.app.adapter.ImagesPagerAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author ortal
 * @date 2015-04-28
 */
public class HotelSnippetViewHolder {
    private final Context mContext;
    private final LayoutInflater mInflater;

    @Bind(R.id.pager)
    ViewPager mSnippetImagePager;
    @Bind(R.id.snippet_title)
    TextView mSnippetTitle;
    @Bind(R.id.reviewers)
    TextView mReviewers;
    @Bind(R.id.reviews)
    TextView mReviews;
    @Bind(R.id.number_images)
    TextView mNumberImages;
    @Bind(R.id.tripadvisor_bar_small)
    View mTripadvisorBar;
    @Bind(R.id.facilities)
    LinearLayout mFacilities;
    @Bind(R.id.facilities_bar)
    View mFacilitiesBar;

    public HotelSnippetViewHolder(View container, Context context) {
        mContext = context;
        ButterKnife.bind(this, container);
        mInflater = LayoutInflater.from(context);
    }

    public void render(HotelSnippet hotelSnippet) {
        mSnippetTitle.setText(hotelSnippet.getName());


        if (hotelSnippet.getImageUrl() != null) {
            final ImagesPagerAdapter imagesPagerAdapter = new ImagesPagerAdapter(mContext);
            imagesPagerAdapter.addItem(hotelSnippet.getImageUrl());
            mSnippetImagePager.setAdapter(imagesPagerAdapter);
            mSnippetImagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mNumberImages.setText(new StringBuilder().append(String.valueOf(position + 1)).append("/").append(String.valueOf(imagesPagerAdapter.getCount())).toString());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            mNumberImages.setText(new StringBuilder().append("1/").append(String.valueOf(imagesPagerAdapter.getCount())).toString());
        }


    }


}
