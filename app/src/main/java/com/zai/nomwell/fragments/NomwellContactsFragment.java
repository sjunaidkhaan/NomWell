package com.zai.nomwell.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zai.nomwell.ContactsSpotActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.adapter.DividerItemDecoration;
import com.zai.nomwell.adapter.NomwellContactsAdapter;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.db.MySpotsData;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class NomwellContactsFragment extends Fragment implements OnRecyclerViewClickListener {

    private static final String TAG = "nomwell_contacts";

    private SuperRecyclerView rcvwSpots;
    private LinearLayoutManager layoutManager;
    private NomwellContactsAdapter adapter;

    public NomwellContactsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nomwell_contacts, container, false);
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
        setListData();
    }

    private void setListData() {
        adapter = new NomwellContactsAdapter(getDummyData(), this);
        rcvwSpots.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.imvwIcon:
                MySpotsData msd = adapter.getItemAt(position);
                if (msd.icon == R.drawable.ic_friend_stars) {
                    msd.icon = R.drawable.ic_friend_star_filled;
                } else {
                    msd.icon = R.drawable.ic_friend_stars;
                }
                adapter.notifyItemChanged(position);
                break;
            default:
                Intent intent = new Intent(getActivity(), ContactsSpotActivity.class);
                intent.putExtra(ContactsSpotActivity.EXTRA_TITLE, adapter.getItemAt(position).header);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    private ArrayList<MySpotsData> getDummyData() {
        ArrayList<MySpotsData> msds = new ArrayList<>();

        MySpotsData msd = new MySpotsData();
        msd.icon = R.drawable.ic_friend_stars;
        msd.header = "Tyler Graves";
        msd.info = "67 spots, 3 lists";
        msd.subInfo = "Chicago (47), Detroit (19), San Francisco";
        msds.add(msd);

        msd = new MySpotsData();
        msd.icon = R.drawable.ic_friend_stars;
        msd.header = "Action Bronson";
        msd.info = "282 spots, 19 lists";
        msd.subInfo = "New York (282)";
        msds.add(msd);

        msd = new MySpotsData();
        msd.icon = R.drawable.ic_friend_stars;
        msd.header = "Matt Burgess";
        msd.info = "63 spots, 3 lists";
        msd.subInfo = "Chicago (48), Detroit (10), Seattle (3)";
        msds.add(msd);

        msd = new MySpotsData();
        msd.icon = R.drawable.ic_friend_stars;
        msd.header = "Jim Kass";
        msd.info = "62 spots, 3 lists";
        msd.subInfo = "Seattle (34), Nashville (28)";
        msds.add(msd);

        msd = new MySpotsData();
        msd.icon = R.drawable.ic_friend_stars;
        msd.header = "Maghan McVary";
        msd.info = "192 spots, 13 lists";
        msd.subInfo = "Chicago (101), New York (55), San Francisco";
        msds.add(msd);

        return msds;
    }
}
