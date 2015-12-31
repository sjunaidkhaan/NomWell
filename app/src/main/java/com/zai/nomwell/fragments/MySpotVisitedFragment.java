package com.zai.nomwell.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.adapter.DividerItemDecoration;
import com.zai.nomwell.adapter.MySpotsAdapter;
import com.zai.nomwell.db.MySpotsData;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySpotVisitedFragment extends BaseFragment {


    private SuperRecyclerView rcvwSpots;
    private LinearLayoutManager layoutManager;
    private MySpotsAdapter adapter;

    private MySpotsActivity activity;

    public MySpotVisitedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_spot_gone, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MySpotsActivity) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity());
        rcvwSpots = (SuperRecyclerView) view.findViewById(R.id.rcvwSpots);
        rcvwSpots.addItemDecoration(itemDecoration);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvwSpots.setLayoutManager(layoutManager);

        adapter = new MySpotsAdapter(activity.getFilteredDummyData(MySpotsData.STATUS_GONE), false, null);
        rcvwSpots.setAdapter(adapter);
    }

    @Override
    public String getFragmentTitle(Context context) {
        return "";
    }
}
