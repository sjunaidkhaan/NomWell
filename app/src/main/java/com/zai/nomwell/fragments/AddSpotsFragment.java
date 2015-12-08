package com.zai.nomwell.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zai.nomwell.MapsActivity;
import com.zai.nomwell.R;

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
                Intent map = new Intent(getActivity(), MapsActivity.class);
                map.putExtra(MapsActivity.EXTRA_TITLE, getString(R.string.papular_places_near_me));
                startActivity(map);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
