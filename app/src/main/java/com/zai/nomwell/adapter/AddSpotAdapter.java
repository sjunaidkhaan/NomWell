package com.zai.nomwell.adapter;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.zai.nomwell.R;
import com.zai.nomwell.adapter.holder.AddSpotHolder;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.db.MyListsData;

import java.util.ArrayList;

/**
 * Created by chitta on 12/8/15.
 */
public class AddSpotAdapter extends BaseSwipeAdapter<AddSpotHolder> {

    private ArrayList<MyListsData> myListData;

    private OnRecyclerViewClickListener clickListener;

    public AddSpotAdapter(ArrayList<MyListsData> mySpotsData, OnRecyclerViewClickListener clickListener) {
        this.myListData = mySpotsData;
        this.clickListener = clickListener;
    }

    @Override
    public AddSpotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddSpotHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_add_spot, null), clickListener);
    }

    @Override
    public void onBindViewHolder(AddSpotHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MyListsData mld = myListData.get(position);
        holder.txtHeader.setText(mld.header);
        holder.txtInfo.setText(mld.info);
        holder.imvwIcon.setImageResource(R.drawable.ic_add_white_36dp);
        holder.imvwIcon.setColorFilter(
                ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));

    }

    @Override
    public int getItemCount() {
        return myListData.size();
    }
}
