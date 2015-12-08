package com.zai.nomwell.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zai.nomwell.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyListsSpotRecsFragment extends BaseFragment {


    public MyListsSpotRecsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spot_recs, container, false);
    }

    @Override
    public String getFragmentTitle(Context context) {
        return context.getString(R.string.spot_recs);
    }
}
