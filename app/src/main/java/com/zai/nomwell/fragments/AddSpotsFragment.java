package com.zai.nomwell.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zai.nomwell.AddNewSpotActivity;
import com.zai.nomwell.AddSpotsActivity;
import com.zai.nomwell.MapActivity;
import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.adapter.AddSpotAdapter;
import com.zai.nomwell.adapter.DividerItemDecoration;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.dialog.NomwellInfoDialog;
import com.zai.nomwell.dialog.NomwellStarsDialog;
import com.zai.nomwell.view.NomwellHalfClickableTextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddSpotsFragment extends Fragment implements OnRecyclerViewClickListener {

    private SuperRecyclerView rcvwSpots;
    private LinearLayoutManager layoutManager;
    private AddSpotAdapter adapter;

    private AddSpotsActivity activity;

    private NomwellHalfClickableTextView halfClickableTextView;

    public AddSpotsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        showLocationAccessDialog();
        return inflater.inflate(R.layout.fragment_add_spots, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity());
        rcvwSpots = (SuperRecyclerView) view.findViewById(R.id.rcvwContent);
        rcvwSpots.addItemDecoration(itemDecoration);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvwSpots.setLayoutManager(layoutManager);

        rcvwSpots.hideProgress();

        halfClickableTextView = new NomwellHalfClickableTextView(view.findViewById(R.id.layoutSuggestion));
        halfClickableTextView.setNormalText("near");
        halfClickableTextView.setClickableText("Chicago, IL");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_spots, menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AddSpotsActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        setEmptyViewVisibility();
    }

    private void setEmptyViewVisibility() {
        if (adapter != null) {
            getView().findViewById(R.id.emptyView).setVisibility(adapter.getItemCount() > 0
                    ? View.GONE : View.VISIBLE);
        } else {
            getView().findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                Intent map = new Intent(getActivity(), MapActivity.class);
                map.putExtra(MapActivity.EXTRA_TITLE, getString(R.string.papular_places_near_me));
                startActivity(map);
                break;
            case R.id.action_populate_list:
                adapter = new AddSpotAdapter(activity.getMyListsDummyData(), this);
                rcvwSpots.setAdapter(adapter);
                setEmptyViewVisibility();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLocationAccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getContext().getString(R.string.ask_location_tile));
        NomwellInfoDialog nomwellInfoDialog = new NomwellInfoDialog(getContext());
        nomwellInfoDialog.setMessage(getContext().getString(R.string.ask_location_description));
        builder.setView(nomwellInfoDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellInfoDialog.setPositive("Allow", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForLocation();
                dialog.dismiss();
            }
        });
        nomwellInfoDialog.setNegative("Don't Allow", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void askForLocation() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MySpotsActivity.PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.imvwWantToGo:
                break;
            case R.id.imvwGone:
                showStarsDialog();
                break;
            case R.id.imvwIcon:
                Intent intent = new Intent(getContext(), AddNewSpotActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    /**
     * page 77
     */
    private void showStarsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Congrats on trying ");
        final NomwellStarsDialog nomwellStarsDialog = new NomwellStarsDialog(getContext());
        nomwellStarsDialog.setMessage(getContext().getString(R.string.howd_you_like_it));
//        nomwellStarsDialog.setNote("(Tap twice for full-stars)");
        builder.setView(nomwellStarsDialog.getView());
        final AlertDialog dialog = builder.create();

        nomwellStarsDialog.setPositive("Done", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        nomwellStarsDialog.setNegative("Add More Details", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
