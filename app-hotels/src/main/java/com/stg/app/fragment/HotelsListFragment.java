package com.stg.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.socialtravelguide.api.EtbApi;
import com.socialtravelguide.api.contract.Sort;
import com.socialtravelguide.api.model.ErrorResponse;
import com.socialtravelguide.api.model.ResultsResponse;
import com.socialtravelguide.api.model.SearchRequest;
import com.stg.app.HotelsApplication;
import com.stg.app.R;
import com.stg.app.activity.BaseActivity;
import com.stg.app.activity.HotelListActivity;
import com.stg.app.adapter.HotelListAdapter;
import com.stg.app.adapter.HotelViewHolder;
import com.stg.app.etbapi.RetrofitCallback;
import com.stg.app.etbapi.RetrofitConverter;
import com.stg.app.events.Events;
import com.stg.app.events.SearchRequestEvent;
import com.stg.app.events.SearchResultsEvent;
import com.stg.app.utils.AppLog;
import com.stg.app.widget.recyclerview.EndlessRecyclerView;
import com.stg.app.widget.recyclerview.TopOffsetItemDecorator;
import com.squareup.okhttp.ResponseBody;
import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.security.InvalidParameterException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Response;

public class HotelsListFragment extends BaseFragment implements View.OnClickListener {

    private static final int NUMBER_OF_RETRIES = 1;
    @Bind(android.R.id.list)
    EndlessRecyclerView mRecyclerView;
    @Bind(R.id.button_sort)
    TextView mButtonSort;
    @Bind(R.id.hotel_list_no_result)
    LinearLayout mNoResult;
    @Bind(R.id.panel_top)
    FrameLayout mTopPanel;
    @Bind(R.id.available_count)
    TextView mAvailableCountText;
    @Bind(R.id.loader_text)
    TextView mLoaderText;
    @Bind(R.id.modify_preferences)
    Button mModifyPreferences;
    EtbApi mEtbApi;
    private LinearLayoutManager mLayoutManager;
    private HotelListAdapter mAdapter;
    private Listener mListener;
    private int mNumberRetries = 0;
    private RetrofitCallback<ResultsResponse> mResultsCallback = new RetrofitCallback<ResultsResponse>() {

        @Override
        public void success(ResultsResponse apiResponse, Response response) {
            if (getActivity() != null) {
                ((HotelListActivity) getActivity()).hideLoaderImage();
            }
            mLoaderText.setVisibility(View.GONE);
            if (mRecyclerView == null || mAdapter == null) {
                return;
            }

//            if (apiResponse.accommodations == null || apiResponse.accommodations.isEmpty() || apiResponse.accommodations.size() != EtbApi.LIMIT) {
//                mRecyclerView.setHasMoreData(false);
//            }
//
//            if (mAdapter.getItemCount() == 0) {
//                if (apiResponse.accommodations == null || apiResponse.accommodations.isEmpty()) {
//                    mNoResult.setVisibility(View.VISIBLE);
//                    mTopPanel.setVisibility(View.GONE);
//                } else {
//                    mAvailableCountText.setVisibility(View.VISIBLE);
//                    mButtonSort.setVisibility(View.VISIBLE);
//                }
//            }

            mNumberRetries = 0;
        }

        @Override
        public void failure(ResponseBody response, boolean isOffline) {
            if (getActivity() != null) {
                ((HotelListActivity) getActivity()).hideLoaderImage();
                if (getActivity() == null) {
                    return;
                }

                if (response != null) {
                    try {
                        ErrorResponse body = (ErrorResponse) RetrofitConverter.getBodyAs(response, ErrorResponse.class);
                        if (body != null && body.meta != null) {
                            fail(body.meta.errorMessage, mAdapter.getItemCount() == 0);
                        } else {
                            retry();
                        }
                    } catch (IOException e) {
                        AppLog.e(e);
                        retry();
                    }
                } else {
                    retry();
                }
                Events.post(new SearchResultsEvent(true, mAdapter.getItemCount()));
            }
        }

        private void retry() {
            if (mAdapter != null) {
                if (mNumberRetries++ < NUMBER_OF_RETRIES) {
                    Toast.makeText(getActivity(), R.string.retry_connecting_server, Toast.LENGTH_SHORT).show();
                    loadSearchResults(mAdapter.getItemCount());
                } else {
                    fail(getActivity().getString(R.string.unable_connect_server), mAdapter.getItemCount() == 0);
                }
            }
        }

        private void fail(String message, boolean isFinish) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            if (isFinish) {
                getActivity().finish();
            }
        }
    };

    public static HotelsListFragment newInstance() {
        return new HotelsListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mResultsCallback.attach(context);
        try {
            mListener = (Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement HotelsListFragment.Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mResultsCallback.detach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new HotelListAdapter((BaseActivity) getActivity(), (HotelViewHolder.Listener) getActivity());

        mEtbApi = HotelsApplication.provide(getActivity()).etbApi();
        mRecyclerView.init(mLayoutManager, mAdapter, EtbApi.LIMIT);
        mRecyclerView.addItemDecoration(new TopOffsetItemDecorator(getActivity().getResources().getDimensionPixelOffset(R.dimen.results_panel_top_height)));
        mRecyclerView.setOnLoadMoreListener(new EndlessRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadSearchResults(mAdapter.getItemCount());
            }
        });

        mButtonSort.setOnClickListener(this);
        mButtonSort.setVisibility(View.GONE);

        mModifyPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditLocationClick();
            }
        });

        refresh();
    }

    private void loadSearchResults(int offset) {
        SearchRequest hotelsRequest = getHotelsRequest();
        Events.post(new SearchRequestEvent(hotelsRequest, offset));
        try {
            mEtbApi.records(hotelsRequest, offset).enqueue(mResultsCallback);
        } catch (InvalidParameterException e) {
            getActivity().finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Events.register(this);
        // Refresh favorites
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        Events.unregister(this);
        super.onPause();

    }

    @Override
    public void onDestroyView() {
        if (mRecyclerView != null) {
            mRecyclerView.setItemAnimator(null);
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
        }
        mAdapter = null;
        mLayoutManager = null;
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_hotel_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void refresh() {
        ((HotelListActivity) getActivity()).showLoaderImage();
        SearchRequest hotelsRequest = getHotelsRequest();

        mLoaderText.setVisibility(View.VISIBLE);
        mNoResult.setVisibility(View.GONE);
        mTopPanel.setVisibility(View.VISIBLE);

        if (mAdapter != null) {
            mAdapter.clear();
            mRecyclerView.setHasMoreData(true);
            loadSearchResults(0);
        }
        SearchRequest.Sort sort = hotelsRequest.getSort();
        if (sort != null) {
            mButtonSort.setText(ResultsSortFragment.typeToText(sort.type).toString());
        } else {
            mButtonSort.setText(ResultsSortFragment.typeToText(Sort.Type.DISTANCE).toString());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_sort) {
            ((HotelListActivity) getActivity()).showSort();
        }
    }

    @Subscribe
    public void onSearchResults(SearchResultsEvent event) {
        mRecyclerView.setVisibility(View.VISIBLE);
        if (!event.hasError()) {
            mAvailableCountText.setText(Html.fromHtml(getResources().getQuantityString(R.plurals.hotels_count_available, event.getCount(), event.getCount())));
        }
    }

    public interface Listener {
        void onEditLocationClick();
    }

}
