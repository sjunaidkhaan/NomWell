package com.zai.nomwell.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.SelectListActivity;
import com.zai.nomwell.adapter.DividerItemDecoration;
import com.zai.nomwell.adapter.MyListsAdapter;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.db.MyListsData;
import com.zai.nomwell.dialog.NomwellInfoDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyListsSpotRecsFragment extends BaseFragment implements OnRecyclerViewClickListener {

    private SuperRecyclerView rcvwSpots;
    private LinearLayoutManager layoutManager;
    private MyListsAdapter adapter;

    private MySpotsActivity activity;

    public MyListsSpotRecsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spot_recs, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MySpotsActivity) context;
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

        rcvwSpots.hideProgress();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_my_lists, menu);
    }

    /**
     * page 93
     */
    private void showUnfollowDialog(String option) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        NomwellInfoDialog nomwellInfoDialog = new NomwellInfoDialog(getContext());
        nomwellInfoDialog.setMessage("Are you sure you want to " + option
                + " this list?");
        builder.setView(nomwellInfoDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellInfoDialog.setPositive("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        nomwellInfoDialog.setNegative("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_populate_list:
                adapter = new MyListsAdapter(getMyListsDummyData(), "Delete", this);
                rcvwSpots.setAdapter(adapter);
                setEmptyViewVisibility();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getFragmentTitle(Context context) {
        return context.getString(R.string.spot_recs);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.lblDelete:
                showUnfollowDialog("Delete");
                break;
            default:
                Intent intent = new Intent(getActivity(), SelectListActivity.class);
                intent.putExtra(SelectListActivity.EXTRA_TITLE, adapter.getTitle(position));
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public int getAdapterTotal() {
        return adapter != null ? adapter.getItemCount() : 0;
    }

    public ArrayList<MyListsData> getMyListsDummyData() {
        ArrayList<MyListsData> mlds = new ArrayList<>();

        MyListsData mld = new MyListsData();
        mld.header = "Received 09/19/15";
        mld.info = "by Sebastian Stankiewicz; 3 spots";
        mlds.add(mld);

        return mlds;
    }
}
