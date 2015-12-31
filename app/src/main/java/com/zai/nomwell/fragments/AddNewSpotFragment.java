package com.zai.nomwell.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zai.nomwell.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddNewSpotFragment extends Fragment {

    private AppCompatImageView imvwMap;

    public AddNewSpotFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_spot, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imvwMap = (AppCompatImageView) view.findViewById(R.id.imvwMap);

        Glide.with(getContext()).load("https://maps.googleapis.com/maps/api/staticmap?center=33.694245,%2073.064763&zoom=14&size=320x240")
                .into(imvwMap);
    }
}
