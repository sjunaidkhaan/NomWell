package com.zai.nomwell.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.TabbedMapActivity;
import com.zai.nomwell.adapter.ContactSpotsAdapter;
import com.zai.nomwell.adapter.DividerItemDecoration;
import com.zai.nomwell.db.MySpotsData;
import com.zai.nomwell.dialog.NomwellFilterDialog;
import com.zai.nomwell.dialog.NomwellMultipleChoiceListDialog;
import com.zai.nomwell.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySpotVisitedFragment extends BaseFragment {

    private static final String TAG = "my_spot_visited_fragment";

    private SuperRecyclerView rcvwSpots;
    private LinearLayoutManager layoutManager;
    private ContactSpotsAdapter adapter;

    public MySpotVisitedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_spot_gone, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity());
        rcvwSpots = (SuperRecyclerView) view.findViewById(R.id.rcvwSpots);
        rcvwSpots.addItemDecoration(itemDecoration);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvwSpots.setLayoutManager(layoutManager);

        MySpotsActivity activity = new MySpotsActivity();
        adapter = new ContactSpotsAdapter(activity.getFilteredDummyData(MySpotsData.STATUS_GONE,
                ContextCompat.getColor(getContext(), R.color.blue_filter)), false, null);
        rcvwSpots.setAdapter(adapter);
    }

    @Override
    public String getFragmentTitle(Context context) {
        return "";
    }

    @Override
    public int getAdapterTotal() {
        return adapter != null ? adapter.getItemCount() : 0;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu != null) {
            menu.clear();
        }
        inflater.inflate(R.menu.menu_my_spots, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_map:
                Intent map = new Intent(getActivity(), TabbedMapActivity.class);
                map.putExtra(TabbedMapActivity.EXTRA_MODE, TabbedMapActivity.MODE_NORMAL);
                startActivity(map);
                break;
            case R.id.action_filter:
                showFilterDialog();
                break;
        }
        return true;
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Spot Filter");
        NomwellFilterDialog nomwellFilterDialog = new NomwellFilterDialog(getContext());
        nomwellFilterDialog.setCuisineText("American (New) or Vegan");
        nomwellFilterDialog.setTagText("BYOB");
        nomwellFilterDialog.addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.txtCuisine:
                        showCuisinesDialog();
                        break;
                    case R.id.txtTag:
                        showTagsDialog();
                        break;
                }
            }
        });
        builder.setView(nomwellFilterDialog.getView());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                activity.getSupportActionBar().setTitle("Filter Results");
            }
        });
        builder.setNegativeButton("Back", null);
        builder.create().show();
    }

    private void showCuisinesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Cuisines");
        final NomwellMultipleChoiceListDialog nomwellListDialog = new NomwellMultipleChoiceListDialog(getContext());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                SparseBooleanArray array = nomwellListDialog.getCheckedItems();
                for (int i = 0; i < array.size(); i++) {
                    Util.log(TAG, "Item: " + i + " -> " + array.get(i));
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        nomwellListDialog.setMultipleOptions(new String[]{
                "American (New)", "American (Traditional)", "Caribbean", "Chinese", "Indian",
                "Polish", "Southern", "Vegan", "Vegetarian"
        }, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Util.log(TAG, "Item Checked: " + i + " -> " + nomwellListDialog.isChecked(i));
                nomwellListDialog.setChecked(i, nomwellListDialog.isChecked(i));
            }
        });
        builder.setView(nomwellListDialog.getView());
        builder.create().show();

    }

    private void showTagsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Tags");
        final NomwellMultipleChoiceListDialog nomwellListDialog = new NomwellMultipleChoiceListDialog(getContext());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                SparseBooleanArray array = nomwellListDialog.getCheckedItems();
                for (int i = 0; i < array.size(); i++) {
                    Util.log(TAG, "Item: " + i + " -> " + array.get(i));
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        nomwellListDialog.setMultipleOptions(new String[]{
                "Beer Gardens", "BYOB", "Clubs", "Date Spots", "Duke Bar",
                "Good For Groups", "Late Night", "MSU Bar", "Pool"
        }, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Util.log(TAG, "Item Checked: " + i + " -> " + nomwellListDialog.isChecked(i));
                nomwellListDialog.setChecked(i, nomwellListDialog.isChecked(i));
            }
        });
        builder.setView(nomwellListDialog.getView());
        builder.create().show();

    }
}
