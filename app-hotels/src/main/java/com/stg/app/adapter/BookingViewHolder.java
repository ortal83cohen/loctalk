package com.stg.app.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.socialtravelguide.api.utils.DateRangeUtils;
import com.stg.app.R;
import com.stg.app.member.model.BookingEvent;
import com.stg.app.preferences.UserPreferences;
import com.stg.app.utils.AppLog;
import com.stg.app.utils.PriceRender;
import com.stg.app.widget.NumberBoxView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author ortal
 * @date 2015-08-19
 */
public class BookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final Context mContext;
    private final SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private final SimpleDateFormat mDayFormatter = new SimpleDateFormat("dd", Locale.getDefault());
    private final SimpleDateFormat mMonthFormatter = new SimpleDateFormat("MMM", Locale.getDefault());

    @Bind(R.id.image)
    ImageView mImageView;
    @Bind(R.id.snippet_title)
    TextView mSnippetTitle;
    @Bind(R.id.city)
    TextView mCity;
    @Bind(R.id.country)
    TextView mCountry;
    @Bind(R.id.check_in_date)
    NumberBoxView mCheckIn;
    @Bind(R.id.check_out_date)
    NumberBoxView mCheckOut;
    @Bind(R.id.number_nights)
    NumberBoxView mNights;
    @Bind(R.id.number_rooms)
    NumberBoxView mRooms;
    @Bind(R.id.btn_manage)
    Button mManageButton;
    @Bind(R.id.price)
    TextView mPrice;
    @Bind(R.id.room_name)
    TextView mRoomName;
    private String orderId;

    public BookingViewHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
        mManageButton.setOnClickListener(this);

    }

    public void attachBookingEvent(BookingEvent event) {
        BookingEvent.Booking booking = event.booking;
        orderId = booking.orderId;

        mCity.setText(booking.city);
        mCountry.setText(booking.country);

        Date arrivalDate = parseDate(booking.arrival);
        mCheckIn.setValue(mDayFormatter.format(arrivalDate));
        mCheckIn.setSubtitle(mMonthFormatter.format(arrivalDate));

        Date departureDate = parseDate(booking.departure);
        mCheckOut.setValue(mDayFormatter.format(departureDate));
        mCheckOut.setSubtitle(mMonthFormatter.format(departureDate));

        Resources r = mContext.getResources();

        mNights.setValue(1);
        mNights.setTitle(r.getQuantityString(R.plurals.nights_caps, 1));

        mRooms.setValue(booking.rooms);
        mRooms.setTitle(r.getQuantityString(R.plurals.rooms_caps, booking.rooms));

        PriceRender priceRender = createPriceRender(booking.currency, DateRangeUtils.days(arrivalDate.getTime(), departureDate.getTime()));

        mPrice.setText(priceRender.render(booking.totalValue));

        mRoomName.setText(booking.rateName);
    }

    public PriceRender createPriceRender(String currencyCode, int numOfDays) {
        return PriceRender.create(UserPreferences.PRICE_SHOW_TYPE_STAY, currencyCode, numOfDays, mContext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_manage:

                break;
        }

    }

    private Date parseDate(String dateString) {
        Date date = new Date();
        try {
            date = mFormatter.parse(dateString);
        } catch (ParseException e) {
            AppLog.e(e);
        }
        return date;
    }
}
