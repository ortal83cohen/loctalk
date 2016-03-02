package com.travoca.app.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.travoca.app.R;
import com.travoca.app.adapter.FavoritesAdapter;
import com.travoca.app.provider.DbContract;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author ortal
 * @date 2015-05-17
 */
public class FavoritesFragment extends BaseFragment {
    @Bind(android.R.id.list)
    ListView mRecyclerView;
    @Bind(R.id.hotel_list_no_result)
    LinearLayout mNoResult;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites_cities, container, false);

        ButterKnife.bind(this, view);
        Cursor cursor = getActivity().getContentResolver().query(DbContract.Favorites.CONTENT_URI.buildUpon().
                appendQueryParameter("group by", DbContract.FavoritesColumns.TITLE + "," + DbContract.FavoritesColumns.TEXT).build(), null, null, null, null);

        if (cursor == null) {
            mNoResult.setVisibility(View.VISIBLE);
            return view;
        }

        if (cursor.getCount() == 0) {
            mNoResult.setVisibility(View.VISIBLE);
            cursor.close();
        } else {
            mNoResult.setVisibility(View.GONE);
            mRecyclerView.setAdapter(new FavoritesAdapter(getActivity(), cursor));
            mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                }
            });
            getActivity().setTitle(R.string.favorites);
        }


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onResume() {
        super.onResume();
    }


}
