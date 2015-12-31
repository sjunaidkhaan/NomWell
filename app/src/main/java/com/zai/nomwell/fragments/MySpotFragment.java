package com.zai.nomwell.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
import android.widget.AdapterView;
import android.widget.ImageView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zai.nomwell.AddSpotsActivity;
import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.TabbedMapActivity;
import com.zai.nomwell.adapter.DividerItemDecoration;
import com.zai.nomwell.adapter.MySpotsAdapter;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.dialog.NomwellInfoDialog;
import com.zai.nomwell.dialog.NomwellListDialog;
import com.zai.nomwell.dialog.NomwellStarsDialog;
import com.zai.nomwell.util.Util;
import com.zai.nomwell.view.NomwellHalfClickableTextView;
import com.zai.nomwell.view.NomwellTab;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySpotFragment extends Fragment implements View.OnClickListener, OnRecyclerViewClickListener {

    public static final String TAG = "my_spot_fragment";

    private TabLayout tabLayout;

    private NomwellHalfClickableTextView layoutSuggestion;
    private NomwellHalfClickableTextView layoutSortBy;

    public SuperRecyclerView rcvwSpots;
    private LinearLayoutManager layoutManager;
    private MySpotsAdapter adapter;

    private boolean dataSet = false;

    private MySpotsActivity activity;

    private NomwellTab tab1;
    private NomwellTab tab2;
    private NomwellTab tab3;


    public MySpotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MySpotsActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_spot, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setOnTabSelectedListener(tabSelectedListener);

        layoutSuggestion = new NomwellHalfClickableTextView(view.findViewById(R.id.layoutSuggestion));
        layoutSuggestion.setNormalText("0 near");
        layoutSuggestion.setClickableText("Seattle, WA");

        layoutSortBy = new NomwellHalfClickableTextView(view.findViewById(R.id.layoutSortBy));
        layoutSortBy.setNormalText("Sort by:");
        layoutSortBy.setClickableText("Name");

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity());
        rcvwSpots = (SuperRecyclerView) view.findViewById(R.id.rcvwSpots);
        rcvwSpots.addItemDecoration(itemDecoration);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvwSpots.setLayoutManager(layoutManager);

        rcvwSpots.hideProgress();

        setViews();

        view.findViewById(R.id.llShare).setOnClickListener(this);
        view.findViewById(R.id.llAddSpots).setOnClickListener(this);
        view.findViewById(R.id.llAddToLists).setOnClickListener(this);
    }


    private void setViews() {
        if (!dataSet) {
//            tab1 = new NomwellTab(getContext(), R.drawable.left_selected, R.drawable.left_normal);
//            tab1.setText("ALL");
//            tab2 = new NomwellTab(getContext(), R.drawable.center_selected, R.drawable.center_normal);
//            tab2.setImageResource(R.drawable.ic_pin_24);
//            tab3 = new NomwellTab(getContext(), R.drawable.right_selected, R.drawable.right_normal);
//            tab3.setImageResource(R.drawable.ic_done_white_24dp);
            tabLayout.addTab(tabLayout.newTab().setText("ALL"));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_pin_24));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_done_white_24dp));
//            tabLayout.addTab(tabLayout.newTab().setCustomView(tab1.getView()));
//            tabLayout.addTab(tabLayout.newTab().setCustomView(tab2.getView()));
//            tabLayout.addTab(tabLayout.newTab().setCustomView(tab3.getView()));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu != null) {
            menu.clear();
        }
        inflater.inflate(R.menu.menu_my_spots, menu);
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
//                    tab1.setSelected(true);
//                    tab2.setSelected(false);
//                    tab3.setSelected(false);
                    break;
                case 1:

                    break;
                case 2:

                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

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
                startActivity(map);
                break;
            case R.id.action_populate_list:
                adapter = new MySpotsAdapter(activity.getMySpotsDummyData(), false, this);
                rcvwSpots.setAdapter(adapter);
                setEmptyViewVisibility();
                break;

            case R.id.action_populate_list_with_check:
                adapter = new MySpotsAdapter(activity.getMySpotsDummyData(), true, this);
                rcvwSpots.setAdapter(adapter);
                setEmptyViewVisibility();
                break;
        }


        return true;
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

    public void showEmptyListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        NomwellListDialog nomwellListDialog = new NomwellListDialog(getContext());
        nomwellListDialog.setMessage(getContext().getString(R.string.must_add_places));
        nomwellListDialog.setOptions(new String[]{"OK"});
        builder.setView(nomwellListDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
    }

    @Override
    public void onClick(View view) {
        Util.log(TAG, "Clicked");
        switch (view.getId()) {
            case R.id.llShare:
                showSendToFriendDialog("Test", "Test");
                break;

            case R.id.llAddSpots:
                if (rcvwSpots.getAdapter() != null &&
                        rcvwSpots.getAdapter().getItemCount() > 0) {
                    startActivity(new Intent(getActivity(), AddSpotsActivity.class));
                } else {
                    showEmptyListDialog();
                }
                break;

            case R.id.llAddToLists:
                break;
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
            case R.id.imvwCheck:
                ((ImageView) view).setImageResource(R.drawable.ic_sendmode_checked_24);
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
        nomwellStarsDialog.setNote("(Tap twice for full-stars)");
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

    /**
     * page 132, 141
     *
     * @param info
     */
    private void showInfoDialog(String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(info);
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    /**
     * page 109
     */
    public void showSendToFriendDialog(String spotName, String option) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Save all these places to " + spotName);
        NomwellInfoDialog nomwellInfoDialog = new NomwellInfoDialog(getContext());
        nomwellInfoDialog.setMessage("They'll be added as " + option + " by default.");
        builder.setView(nomwellInfoDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellInfoDialog.setPositive("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showInfoDialog("Spots Sent");
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

    /**
     * page 62
     *
     * @param spotName
     */

    private void showSpotAddedDialog(String spotName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        NomwellListDialog nomwellListDialog = new NomwellListDialog(getContext());
        nomwellListDialog.setMessage("Spot added to " + spotName);
        nomwellListDialog.setOptions(new String[]{"OK"});
        builder.setView(nomwellListDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
