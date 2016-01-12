package com.zai.nomwell.fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
import android.widget.AdapterView;
import android.widget.ImageView;

import com.baoyz.actionsheet.ActionSheet;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zai.nomwell.AddSpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.SelectSpotActivity;
import com.zai.nomwell.TabbedMapActivity;
import com.zai.nomwell.adapter.DividerItemDecoration;
import com.zai.nomwell.adapter.MySpotsAdapter;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.db.MySpotsData;
import com.zai.nomwell.dialog.NomwellListDialog;
import com.zai.nomwell.dialog.NomwellStarsDialog;
import com.zai.nomwell.util.Util;
import com.zai.nomwell.view.NomwellHalfClickableTextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactListSpotFragment extends Fragment implements View.OnClickListener, OnRecyclerViewClickListener {

    public static final String TAG = "contact_list_spot_fragment";

    private TabLayout tabLayout;

    private NomwellHalfClickableTextView layoutSuggestion;
    private NomwellHalfClickableTextView layoutSortBy;

    public SuperRecyclerView rcvwSpots;
    private LinearLayoutManager layoutManager;
    private MySpotsAdapter adapter;

    private boolean dataSet = false;

    public ContactListSpotFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_contact_list_spot, container, false);
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
        layoutSortBy.addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIOSDialog(new String[]{"Name", "Rating (Gone Only)", "Near Me"}, new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                    }
                });
            }
        });
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
        view.findViewById(R.id.llFollowList).setOnClickListener(this);
    }

    public void showIOSDialog(String topItems[], ActionSheet.ActionSheetListener listener) {
        ActionSheet.createBuilder(getContext(), getFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles(topItems)
                .setCancelableOnTouchOutside(true)
                .setListener(listener).show();
    }

    private void setViews() {
        if (!dataSet) {
            tabLayout.addTab(tabLayout.newTab().setText("ALL"));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_pin_24));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_done_white_24dp));

            tabLayout.getTabAt(1).getIcon().setColorFilter(ContextCompat.getColor(getContext(), R.color.white), PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu != null) {
            menu.clear();
        }
        inflater.inflate(R.menu.menu_contact_list_spot, menu);
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    adapter = new MySpotsAdapter(getMySpotsDummyData(), false, ContactListSpotFragment.this);
                    break;
                case 1:
                    adapter = new MySpotsAdapter(getFilteredDummyData(MySpotsData.STATUS_WANT_TO_GO), false, ContactListSpotFragment.this);
                    break;
                case 2:
                    adapter = new MySpotsAdapter(getFilteredDummyData(MySpotsData.STATUS_GONE), false, ContactListSpotFragment.this);
                    break;
            }
            rcvwSpots.setAdapter(adapter);
            setEmptyViewVisibility();
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
                map.putExtra(TabbedMapActivity.EXTRA_TITLE, adapter.getItemAt(0).header);
                map.putExtra(TabbedMapActivity.EXTRA_MODE, TabbedMapActivity.MODE_CONTACTS);
                startActivity(map);
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
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setOptions(new String[]{"OK"}, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
            }
        });
        builder.setView(nomwellListDialog.getView());

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

                Intent intent = new Intent(getActivity(), SelectSpotActivity.class);
                intent.putExtra(SelectSpotActivity.EXTRA_TITLE, "Select Spot");
                intent.putExtra(SelectSpotActivity.EXTRA_SHOW_BOTTOM_BUTTONS, false);
                startActivity(intent);
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
                boolean checked = false;
                if (view.getTag() != null) {
                    checked = (boolean) view.getTag();
                }
                if (checked) {
                    ((ImageView) view).setImageResource(R.drawable.ic_sendmode_unchecked_24);
                    view.setTag(false);
                } else {
                    ((ImageView) view).setImageResource(R.drawable.ic_sendmode_checked_24);
                    view.setTag(true);
                }
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


    public ArrayList<MySpotsData> getMySpotsDummyData() {
        ArrayList<MySpotsData> mySpotsData = new ArrayList<>();

        MySpotsData msd = new MySpotsData();
        msd.header = "Ada Street";
        msd.icon = R.drawable.ic_add_white_36dp;
        msd.colorFilter = ContextCompat.getColor(getContext(), R.color.blue_filter);
        msd.info = "$$ - American (New)";
        msd.subInfo = "Note: Try the quiche";
        msd.rating = 5;
        msd.status = MySpotsData.STATUS_GONE;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.icon = R.drawable.ic_add_white_36dp;
        msd.colorFilter = ContextCompat.getColor(getContext(), R.color.blue_filter);
        msd.header = "Alinea";
        msd.info = "$$ - Japanese";
        msd.subInfo = "";
        msd.rating = 0;
        msd.status = MySpotsData.STATUS_NO;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.icon = R.drawable.ic_add_white_36dp;
        msd.colorFilter = ContextCompat.getColor(getContext(), R.color.blue_filter);
        msd.header = "Au Cheval";
        msd.info = "$$ - American (New)";
        msd.subInfo = "Note: Burgers, Rec'd by Claydia w...";
        msd.rating = 0;
        msd.status = MySpotsData.STATUS_GONE;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.icon = R.drawable.ic_add_white_36dp;
        msd.colorFilter = ContextCompat.getColor(getContext(), R.color.blue_filter);
        msd.header = "Avec";
        msd.info = "$$ - Fusion";
        msd.subInfo = "Note: Don't come on the weekends...";
        msd.rating = 4;
        msd.status = MySpotsData.STATUS_WANT_TO_GO;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.icon = R.drawable.ic_add_white_36dp;
        msd.colorFilter = ContextCompat.getColor(getContext(), R.color.blue_filter);
        msd.header = "Balena";
        msd.info = "$$ - Oyster Bars";
        msd.subInfo = "Note: Oyster were mah";
        msd.rating = 3;
        msd.status = MySpotsData.STATUS_WANT_TO_GO;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.icon = R.drawable.ic_add_white_36dp;
        msd.colorFilter = ContextCompat.getColor(getContext(), R.color.blue_filter);
        msd.header = "Girls & the Goat";
        msd.info = "$$ - New American";
        msd.subInfo = "";
        msd.rating = 3;
        msd.status = MySpotsData.STATUS_GONE;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.icon = R.drawable.ic_add_white_36dp;
        msd.colorFilter = ContextCompat.getColor(getContext(), R.color.blue_filter);
        msd.header = "Haymarket Pub & Brewery";
        msd.info = "$$ - Brewery";
        msd.subInfo = "";
        msd.rating = 3;
        msd.status = MySpotsData.STATUS_WANT_TO_GO;
        mySpotsData.add(msd);

        return mySpotsData;
    }

    public ArrayList<MySpotsData> getFilteredDummyData(int status) {
        ArrayList<MySpotsData> all = getMySpotsDummyData();
        ArrayList<MySpotsData> filtered = new ArrayList<>();
        for (MySpotsData msd : all) {
            if (msd.status == status) {
                filtered.add(msd);
            }
        }
        return filtered;
    }
}
