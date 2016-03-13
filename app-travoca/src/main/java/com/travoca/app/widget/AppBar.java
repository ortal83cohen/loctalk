package com.travoca.app.widget;

import com.travoca.api.model.SearchRequest;
import com.travoca.api.model.search.Type;
import com.travoca.app.R;
import com.travoca.app.model.CurrentLocation;
import com.travoca.app.model.LocationWithTitle;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;


public class AppBar extends Toolbar {

    public AppBar(Context context) {
        this(context, null);
    }

    public AppBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    public AppBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLocation(SearchRequest request) {
        Type loc = request.getType();
        if (loc instanceof CurrentLocation) {
            setTitle(R.string.current_location);
        } else if (loc instanceof LocationWithTitle) {
            setTitle(((LocationWithTitle) loc).getTitle());
        } else {
            setTitle("My list");
        }

//        String subtitle = "dddddd";
//        setSubtitle(subtitle);
    }

    public void showLogo() {
        super.setTitle("");
        setLogo(R.drawable.travoca_actionbar_logo);
    }

    @Override
    public void setTitle(int resId) {
        super.setTitle(resId);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }
}
