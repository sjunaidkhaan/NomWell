package com.zai.nomwell.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private View emptyView;

    public ChooseCityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_choose_city, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listCities = (ListView) view.findViewById(R.id.listCities);
        listCities.setOnItemClickListener(this);
        searchView = new NomwellSearchView(view.findViewById(R.id.vwSearch));

        emptyView = view.findViewById(R.id.txtEmpty);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(getActivity(), MySpotsActivity.class));
    }

    @Override
    public String getFragmentTitle(Context context) {
        return context.getString(R.string.choose_city);
    }

    private void setData() {
        listCities.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                cities));
        updateEmptyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu != null) {
            menu.clear();
        }
        inflater.inflate(R.menu.menu_my_lists, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setData();
        return super.onOptionsItemSelected(item);
    }

    private void updateEmptyView() {
        if (listCities.getAdapter() != null && listCities.getCount() > 0) {
            emptyView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getAdapterTotal() {
        return 0;
    }
}
