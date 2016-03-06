package com.travoca.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;
import com.squareup.otto.Subscribe;
import com.travoca.api.TravocaApi;
import com.travoca.api.contract.Sort;
import com.travoca.api.model.ErrorResponse;
import com.travoca.api.model.ResultsResponse;
import com.travoca.api.model.SearchRequest;
import com.travoca.app.App;
import com.travoca.app.TravocaApplication;
import com.travoca.app.R;
import com.travoca.app.activity.BaseActivity;
import com.travoca.app.activity.RecordListActivity;
import com.travoca.app.adapter.RecordListAdapter;
import com.travoca.app.adapter.RecordViewHolder;
import com.travoca.app.drawable.TriangleDrawable;
import com.travoca.app.member.MemberStorage;
import com.travoca.app.member.model.User;
import com.travoca.app.travocaapi.RetrofitCallback;
import com.travoca.app.travocaapi.RetrofitConverter;
import com.travoca.app.events.Events;
import com.travoca.app.events.SearchRequestEvent;
import com.travoca.app.events.SearchResultsEvent;
import com.travoca.app.utils.AppLog;
import com.travoca.app.widget.recyclerview.EndlessRecyclerView;
import com.travoca.app.widget.recyclerview.TopOffsetItemDecorator;

import java.io.IOException;
import java.security.InvalidParameterException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Response;

public class RecordListFragment extends BaseFragment implements View.OnClickListener {

    private static final int NUMBER_OF_RETRIES = 1;
    @Bind(android.R.id.list)
    EndlessRecyclerView mRecyclerView;
    @Bind(R.id.button_sort)
    TextView mButtonSort;
    @Bind(R.id.record_list_no_result)
    LinearLayout mNoResult;
    @Bind(R.id.panel_top)
    FrameLayout mTopPanel;
    @Bind(R.id.available_count)
    TextView mAvailableCountText;
    @Bind(R.id.loader_text)
    TextView mLoaderText;
    TravocaApi mTravocaApi;
    private LinearLayoutManager mLayoutManager;
    private RecordListAdapter mAdapter;
    private Listener mListener;
    private int mNumberRetries = 0;
    private RetrofitCallback<ResultsResponse> mResultsCallback = new RetrofitCallback<ResultsResponse>() {

        @Override
        public void success(final ResultsResponse apiResponse, Response response) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (getActivity() != null) {
                        ((RecordListActivity) getActivity()).hideLoaderImage();
                    }
                    mLoaderText.setVisibility(View.GONE);
                    if (mRecyclerView == null || mAdapter == null) {
                        return;
                    }

                    if (apiResponse.records == null || apiResponse.records.isEmpty() || apiResponse.records.size() != TravocaApi.LIMIT) {
                        mRecyclerView.setHasMoreData(false);
                    }

                    if (mAdapter.getItemCount() == 0) {
                        if (apiResponse.records == null || apiResponse.records.isEmpty()) {
                            mNoResult.setVisibility(View.VISIBLE);
                            mTopPanel.setVisibility(View.GONE);
                        } else {
                            mAvailableCountText.setVisibility(View.VISIBLE);
                            mButtonSort.setVisibility(View.VISIBLE);
                        }
                    }

                    mNumberRetries = 0;
                    mAdapter.addRecords(apiResponse.records);
                }
            }, 3000);


        }

        @Override
        public void failure(ResponseBody response, boolean isOffline) {
            if (getActivity() != null) {
                ((RecordListActivity) getActivity()).hideLoaderImage();
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

    public static RecordListFragment newInstance() {
        return new RecordListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mResultsCallback.attach(context);
        try {
            mListener = (Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement RecordListFragment.Listener");
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
        return inflater.inflate(R.layout.fragment_record_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new RecordListAdapter((BaseActivity) getActivity(), (RecordViewHolder.Listener) getActivity());

        mTravocaApi = TravocaApplication.provide(getActivity()).travocaApi();
        mRecyclerView.init(mLayoutManager, mAdapter, TravocaApi.LIMIT);
        mRecyclerView.addItemDecoration(new TopOffsetItemDecorator(getActivity().getResources().getDimensionPixelOffset(R.dimen.results_panel_top_height)));
        mRecyclerView.setOnLoadMoreListener(new EndlessRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadSearchResults(mAdapter.getItemCount());
            }
        });

        mButtonSort.setOnClickListener(this);
        mButtonSort.setCompoundDrawables(null, null, new TriangleDrawable(getActivity(), R.color.theme_primary), null);
        mButtonSort.setVisibility(View.GONE);

        refresh();
    }

    private void loadSearchResults(int offset) {
        SearchRequest searchRequest = getRequest();
        Events.post(new SearchRequestEvent(searchRequest, offset));
        MemberStorage memberStorage = App.provide(getActivity()).memberStorage();
        User user = memberStorage.loadUser();
        String userId;
        if(user==null){
            userId="";
        }else {
            userId = user.id;
        }
        try {
            searchRequest.setUserId(userId);
            searchRequest.setOffset(offset);
            mTravocaApi.records(searchRequest).enqueue(mResultsCallback);
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
        inflater.inflate(R.menu.menu_record_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void refresh() {
        ((RecordListActivity) getActivity()).showLoaderImage();
        SearchRequest recordsRequest = getRequest();

        mLoaderText.setVisibility(View.VISIBLE);
        mNoResult.setVisibility(View.GONE);
        mTopPanel.setVisibility(View.VISIBLE);

        if (mAdapter != null) {
            mAdapter.clear();
            mRecyclerView.setHasMoreData(true);
            loadSearchResults(0);
        }
        SearchRequest.Sort sort = recordsRequest.getSort();
        if (sort != null) {
            mButtonSort.setText(ResultsSortFragment.typeToText(sort.type).toString());
        } else {
            mButtonSort.setText(ResultsSortFragment.typeToText(Sort.Type.DISTANCE).toString());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_sort) {
            ((RecordListActivity) getActivity()).showSort();
        }
    }

    @Subscribe
    public void onSearchResults(SearchResultsEvent event) {
        mRecyclerView.setVisibility(View.VISIBLE);
//        if (!event.hasError()) {
        mAvailableCountText.setText(Html.fromHtml(getResources().getQuantityString(R.plurals.records_count_available, event.getCount(), event.getCount())));
//        }
    }

    public interface Listener {
        void onEditLocationClick();
    }

}
