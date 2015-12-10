package com.zai.nomwell.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.adapter.MyListsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyListsMineFragment extends BaseFragment {

    private SuperRecyclerView rcvwSpots;
    private LinearLayoutManager layoutManager;
    private MyListsAdapter adapter;

    private MySpotsActivity activity;

    public MyListsMineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MySpotsActivity) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvwSpots = (SuperRecyclerView) view.findViewById(R.id.rcvwSpots);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvwSpots.setLayoutManager(layoutManager);

        adapter = new MyListsAdapter(activity.getMyListsDummyData(), "Delete");
        rcvwSpots.setAdapter(adapter);
    }

    @Override
    public String getFragmentTitle(Context context) {
        return context.getString(R.string.mine);
    }
}
