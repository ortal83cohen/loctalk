package com.stg.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.easytobook.api.contract.Language;
import com.stg.app.R;
import com.stg.app.adapter.HotelReviewsAdapter;
import com.stg.app.analytics.AnalyticsCalls;
import com.stg.app.core.CoreInterface;
import com.stg.app.core.model.Ratings;
import com.stg.app.core.model.ReviewResponse;
import com.stg.app.etbapi.RetrofitCallback;
import com.stg.app.hoteldetails.HotelSnippet;
import com.stg.app.widget.recyclerview.EndlessRecyclerView;
import com.squareup.okhttp.ResponseBody;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;

/**
 * @author alex
 * @date 2015-06-14
 */
public class HotelReviewsFragment extends BaseFragment {
    @Bind(android.R.id.list)
    EndlessRecyclerView mReviewsList;
    @Bind(android.R.id.empty)
    View mEmptyView;
    private HotelSnippet mHotelSnippet;
    private HotelReviewsAdapter mAdapter;
    private int mReviewsPage;
    private CoreInterface.Service mCoreInterface;
    private RetrofitCallback<Ratings> mRatingsCallback = new RetrofitCallback<Ratings>() {

        @Override
        protected void failure(ResponseBody response, boolean isOffline) {

        }

        @Override
        protected void success(Ratings ratings, Response<Ratings> response) {
            mAdapter.setRatings(ratings);
        }

    };

    public static HotelReviewsFragment newInstance(HotelSnippet hotelSnippet) {
        HotelReviewsFragment fragment = new HotelReviewsFragment();
        Bundle args = new Bundle();
        args.putParcelable("snippet", hotelSnippet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoteldetails_reviews, container, false);
        ButterKnife.bind(this, view);

        AnalyticsCalls.get().trackHotelReviews(getHotelsRequest());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mHotelSnippet = getArguments().getParcelable("snippet");

        mCoreInterface = CoreInterface.create(getActivity());


        mAdapter = new HotelReviewsAdapter(getActivity());
        mAdapter.setHasStableIds(true);
        mAdapter.setHotelSnippet(mHotelSnippet);
        mEmptyView.setVisibility(View.GONE);
        mReviewsPage = 0;
        mReviewsList.init(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false), mAdapter, 20);

        mReviewsList.setOnLoadMoreListener(new EndlessRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadReviews(mReviewsPage);
            }
        });

        loadRatings();
        loadReviews(mReviewsPage);
    }

    private void loadRatings() {
//        mCoreInterface.hotelRatings(mHotelSnippet.geId()).enqueue(mRatingsCallback);
        mCoreInterface.hotelRatings().enqueue(mRatingsCallback);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRatingsCallback.attach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mRatingsCallback.detach();
    }

    private void loadReviews(int page) {
        String lang = Language.getDefault();

//        Call<ReviewResponse> call = mCoreInterface.hotelReviews(mHotelSnippet.geId(), lang, page);
        Call<ReviewResponse> call = mCoreInterface.hotelReviews(lang, page);
        call.enqueue(new RetrofitCallback<ReviewResponse>() {

            @Override
            protected void failure(ResponseBody response, boolean isOffline) {
                if (getActivity() != null) {
                    mReviewsList.setHasMoreData(false);
                    Toast.makeText(getActivity(), R.string.receive_reviews_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void success(ReviewResponse reviewResponse, Response<ReviewResponse> response) {
                boolean hasMoreReviews = reviewResponse.nextPageNum > 0;
                mAdapter.addAll(reviewResponse.reviews);

                mReviewsList.setHasMoreData(hasMoreReviews);
                if (hasMoreReviews) {
                    mReviewsPage++;
                }
                updateEmptyList();
            }

        });
    }

    private void updateEmptyList() {
        if (mAdapter.isEmpty()) {
            mReviewsList.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }
}
