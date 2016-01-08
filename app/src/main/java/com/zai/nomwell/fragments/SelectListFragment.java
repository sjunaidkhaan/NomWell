package com.zai.nomwell.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.adapter.DividerItemDecoration;
import com.zai.nomwell.adapter.ListSpotAdapter;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.db.MySpotsData;
import com.zai.nomwell.view.NomwellHalfClickableTextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class SelectListFragment extends Fragment implements View.OnClickListener, OnRecyclerViewClickListener {

    public static final String TAG = "select_list_fragment";

    private TabLayout tabLayout;

    private NomwellHalfClickableTextView layoutSuggestion;
    private NomwellHalfClickableTextView layoutSortBy;

    public SuperRecyclerView rcvwSpots;
    private LinearLayoutManager layoutManager;
    private ListSpotAdapter adapter;

    private boolean dataSet = false;

    private MySpotsActivity activity;

    public SelectListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = new MySpotsActivity();

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setOnTabSelectedListener(tabSelectedListener);

        layoutSuggestion = new NomwellHalfClickableTextView(view.findViewById(R.id.layoutSuggestion));
        layoutSuggestion.setNormalText("0 near Chicago, IL ");
        layoutSuggestion.setClickableText("Select All");

        layoutSortBy = new NomwellHalfClickableTextView(view.findViewById(R.id.layoutSortBy));
        layoutSortBy.setNormalText("Sort by:");
        layoutSortBy.setClickableText("Name");

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity());
        rcvwSpots = (SuperRecyclerView) view.findViewById(R.id.rcvwSpots);
        rcvwSpots.addItemDecoration(itemDecoration);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvwSpots.setLayoutManager(layoutManager);

        setViews();

        rcvwSpots.hideProgress();

        adapter = new ListSpotAdapter(activity.getMySpotsDummyData(), false, this);
        rcvwSpots.setAdapter(adapter);
        setEmptyViewVisibility();

    }

    private void setViews() {
        if (!dataSet) {
            tabLayout.addTab(tabLayout.newTab().setText("ALL"));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_pin_24));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_done_white_24dp));
        }
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    adapter = new ListSpotAdapter(activity.getMySpotsDummyData(), false, SelectListFragment.this);
                    break;
                case 1:
                    adapter = new ListSpotAdapter(activity.getFilteredDummyData(MySpotsData.STATUS_WANT_TO_GO), false, SelectListFragment.this);
                    break;
                case 2:
                    adapter = new ListSpotAdapter(activity.getFilteredDummyData(MySpotsData.STATUS_GONE), false, SelectListFragment.this);
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

    private void setEmptyViewVisibility() {
        if (adapter != null) {
            getView().findViewById(R.id.emptyView).setVisibility(adapter.getItemCount() > 0
                    ? View.GONE : View.VISIBLE);
        } else {
            getView().findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setEmptyViewVisibility();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.imvwWantToGo:
                break;
            case R.id.imvwGone:
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
}
