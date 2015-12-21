package com.zai.nomwell.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zai.nomwell.MapActivity;
import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.dialog.NomwellInfoDialog;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddSpotsFragment extends Fragment {

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_my_spots, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                Intent map = new Intent(getActivity(), MapActivity.class);
                map.putExtra(MapActivity.EXTRA_TITLE, getString(R.string.papular_places_near_me));
                startActivity(map);
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
}
