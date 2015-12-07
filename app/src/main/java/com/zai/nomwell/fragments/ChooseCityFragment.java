package com.zai.nomwell.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.view.NomwellSearchView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChooseCityFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static final String cities[] = {"New York", "Los Angeles", "Birmingham"};

    private ListView listCities;
    private NomwellSearchView searchView;

    public ChooseCityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_city, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listCities = (ListView) view.findViewById(R.id.listCities);
        listCities.setOnItemClickListener(this);
        listCities.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                cities));
        searchView = new NomwellSearchView(view.findViewById(R.id.vwSearch));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(getActivity(), MySpotsActivity.class));
    }

    @Override
    public String getFragmentTitle(Context context) {
        return context.getString(R.string.choose_city);
    }
}
