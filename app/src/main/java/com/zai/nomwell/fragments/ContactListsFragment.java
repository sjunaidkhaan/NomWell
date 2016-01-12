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
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zai.nomwell.ContactListSpotActivity;
import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.adapter.DividerItemDecoration;
import com.zai.nomwell.adapter.MyListsAdapter;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.dialog.NomwellInfoDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListsFragment extends BaseFragment implements OnRecyclerViewClickListener {

    private SuperRecyclerView rcvwSpots;
    private LinearLayoutManager layoutManager;
    private MyListsAdapter adapter;

    public ContactListsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
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

        MySpotsActivity activity = new MySpotsActivity();
        adapter = new MyListsAdapter(activity.getMyListsDummyData(), "Delete", this);
        rcvwSpots.setAdapter(adapter);
        setEmptyViewVisibility();
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
    public String getFragmentTitle(Context context) {
        return "Lists";
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.lblDelete:
                showUnfollowDialog("Delete");
                break;
            default:
                Intent intent = new Intent(getActivity(), ContactListSpotActivity.class);
                intent.putExtra(ContactListSpotActivity.EXTRA_TITLE, adapter.getTitle(position));
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
}
