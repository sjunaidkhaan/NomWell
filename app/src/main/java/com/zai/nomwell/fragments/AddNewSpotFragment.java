package com.zai.nomwell.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zai.nomwell.AddTagActivity;
import com.zai.nomwell.MapActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.SelectListActivity;
import com.zai.nomwell.dialog.NomwellStarsDialog;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddNewSpotFragment extends Fragment implements View.OnClickListener {

    private AppCompatImageView imvwMap;

    private AppCompatTextView txtTags;

    private AppCompatImageView imvwWantToGo;
    private AppCompatImageView imvwGone;

    private AppCompatTextView txtList;

    public static final int WANT_TO_GO = 1;
    public static final int GONE = 2;

    private int selectedButton = -1;

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

        view.findViewById(R.id.rlStars).setOnClickListener(this);

        txtList = (AppCompatTextView) view.findViewById(R.id.txtList);
        txtList.setOnClickListener(this);

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
            case R.id.imvwGone:
                updateBottomButtons(view);
                break;

            case R.id.imvwMap:
                Intent map = new Intent(getActivity(), MapActivity.class);
                map.putExtra(MapActivity.EXTRA_TITLE, "Anniston, AL");
                map.putExtra(MapActivity.EXTRA_LATITUDE, 33.58);
                map.putExtra(MapActivity.EXTRA_LONGITUDE, 85.85);
                startActivity(map);
                break;
            case R.id.rlStars:
                showStarsDialog();
                break;
            case R.id.txtList:
                Intent selectList = new Intent(getActivity(), SelectListActivity.class);
                selectList.putExtra(SelectListActivity.EXTRA_TITLE, "Select List");
                startActivity(selectList);
                break;
        }
    }

    private void updateBottomButtons(View view) {
        switch (view.getId()) {
            case R.id.imvwWantToGo:
                selectedButton = WANT_TO_GO;
                break;
            case R.id.imvwGone:
                selectedButton = GONE;
                break;
        }

        if (selectedButton == WANT_TO_GO) {
            imvwWantToGo.setImageResource(R.drawable.ic_bottom_bar_add_spots_want_to_go_selected);
            imvwGone.setImageResource(R.drawable.ic_bottom_bar_add_spots_gone);
        } else if (selectedButton == GONE) {
            imvwWantToGo.setImageResource(R.drawable.ic_bottom_bar_add_spots_want_to_go);
            imvwGone.setImageResource(R.drawable.ic_bottom_bar_add_spots_gone_selected);
        }
    }

    /**
     * page 77
     */
    private void showStarsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Congrats on trying ");
        final NomwellStarsDialog nomwellStarsDialog = new NomwellStarsDialog(getContext());
        nomwellStarsDialog.setMessage(getContext().getString(R.string.howd_you_like_it));
        nomwellStarsDialog.setNote("(Tap twice for full-stars)");
        builder.setView(nomwellStarsDialog.getView());
        final AlertDialog dialog = builder.create();

        nomwellStarsDialog.setNegative("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        nomwellStarsDialog.addStarsClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int stars = nomwellStarsDialog.getStars();
                if (stars > 0) {
                    nomwellStarsDialog.setPositive("Add More Details", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    nomwellStarsDialog.setPositive(null, null);
                }
            }
        });

        dialog.show();
    }

}
