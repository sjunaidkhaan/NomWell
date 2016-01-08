package com.zai.nomwell.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zai.nomwell.AddTagActivity;
import com.zai.nomwell.MapActivity;
import com.zai.nomwell.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddNewSpotFragment extends Fragment implements View.OnClickListener {

    private AppCompatImageView imvwMap;

    private AppCompatTextView txtTags;

    private AppCompatImageView imvwWantToGo;
    private AppCompatImageView imvwGone;

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
        imvwMap.setOnClickListener(this);

        Glide.with(getContext()).load("https://maps.googleapis.com/maps/api/staticmap?center=33.58,%2085.85&zoom=14&size=480x240")
                .into(imvwMap);

        imvwWantToGo = (AppCompatImageView) view.findViewById(R.id.imvwWantToGo);
        imvwWantToGo.setOnClickListener(this);
        imvwGone = (AppCompatImageView) view.findViewById(R.id.imvwGone);
        imvwGone.setOnClickListener(this);


        txtTags = (AppCompatTextView) view.findViewById(R.id.txtTags);
        txtTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddTagActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imvwWantToGo:
                imvwWantToGo.setSelected(!imvwWantToGo.isSelected());
                if (imvwWantToGo.isSelected()) {
                    imvwWantToGo.setImageResource(R.drawable.ic_bottom_bar_add_spots_want_to_go_selected);
                } else {
                    imvwWantToGo.setImageResource(R.drawable.ic_bottom_bar_add_spots_want_to_go);
                }
                break;

            case R.id.imvwGone:
                imvwGone.setSelected(!imvwGone.isSelected());
                if (imvwGone.isSelected()) {
                    imvwGone.setImageResource(R.drawable.ic_bottom_bar_add_spots_gone_selected);
                } else {
                    imvwGone.setImageResource(R.drawable.ic_bottom_bar_add_spots_gone);
                }
                break;

            case R.id.imvwMap: {
                Intent intent = new Intent(getContext(), MapActivity.class);
                intent.putExtra(MapActivity.EXTRA_TITLE, "Anniston, AL");
                intent.putExtra(MapActivity.EXTRA_LATITUDE, 33.58);
                intent.putExtra(MapActivity.EXTRA_LONGITUDE, 85.85);
                startActivity(intent);
            }
            break;
        }
    }
}
