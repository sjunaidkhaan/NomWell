package com.zai.nomwell.adapter;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.zai.nomwell.R;
import com.zai.nomwell.adapter.holder.ListSpotsHolder;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.db.MySpotsData;

import java.util.ArrayList;

/**
 * Created by chitta on 12/8/15.
 */
public class ListSpotAdapter extends BaseSwipeAdapter<ListSpotsHolder> {

    private ArrayList<MySpotsData> mySpotsData;

    private OnRecyclerViewClickListener clickListener;
    private boolean showCheck;

    public ListSpotAdapter(ArrayList<MySpotsData> mySpotsData, boolean showCheck, OnRecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
        this.mySpotsData = mySpotsData;
        this.showCheck = showCheck;
    }

    @Override
    public ListSpotsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListSpotsHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_all_list_spot, null), showCheck, clickListener);
    }

    @Override
    public void onBindViewHolder(ListSpotsHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MySpotsData msd = mySpotsData.get(position);
        holder.txtHeader.setText(msd.header);
        holder.txtSubInfo.setText(msd.subInfo);
        holder.txtInfo.setText(msd.info);
        int icon = R.drawable.ic_add_white_36dp;
        holder.imvwIcon.setImageResource(icon);
        holder.imvwIcon.setColorFilter(
                ContextCompat.getColor(holder.itemView.getContext(), R.color.blue));
        holder.ratingBar.setProgress(msd.rating);
        if (msd.status == MySpotsData.STATUS_GONE) {
            holder.ratingBar.setVisibility(View.VISIBLE);
        } else {
            holder.ratingBar.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mySpotsData.size();
    }
}
