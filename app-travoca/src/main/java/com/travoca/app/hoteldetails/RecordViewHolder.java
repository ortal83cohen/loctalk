package com.travoca.app.recorddetails;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.travoca.api.model.Record;
import com.travoca.app.R;
import com.travoca.app.adapter.ImagesPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author ortal
 * @date 2015-04-28
 */
public class RecordViewHolder {
    private final Context mContext;
    private final LayoutInflater mInflater;

    @Bind(R.id.pager)
    ViewPager mSnippetImagePager;
    @Bind(R.id.snippet_title)
    TextView mSnippetTitle;
    @Bind(R.id.number_images)
    TextView mNumberImages;


    public RecordViewHolder(View container, Context context) {
        mContext = context;
        ButterKnife.bind(this, container);
        mInflater = LayoutInflater.from(context);
    }

    public void render(Record record) {
        mSnippetTitle.setText(record.title);


        if (record.imageUrl != null) {
            final ImagesPagerAdapter imagesPagerAdapter = new ImagesPagerAdapter(mContext);
            imagesPagerAdapter.addItem(record.imageUrl);
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
