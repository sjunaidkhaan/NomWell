package com.zai.nomwell.adapter;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.zai.nomwell.R;
import com.zai.nomwell.adapter.holder.MySpotsHolder;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.db.MySpotsData;

import java.util.ArrayList;

/**
 * Created by chitta on 12/8/15.
 */
public class MySpotsAdapter extends BaseSwipeAdapter<MySpotsHolder> {

    private ArrayList<MySpotsData> mySpotsData;

    private OnRecyclerViewClickListener clickListener;
    private boolean showCheck;

    public MySpotsAdapter(ArrayList<MySpotsData> mySpotsData, boolean showCheck, OnRecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
        this.mySpotsData = mySpotsData;
        this.showCheck = showCheck;
    }

    @Override
    public MySpotsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MySpotsHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_all_spot, null), showCheck, clickListener);
    }

    @Override
    public void onBindViewHolder(MySpotsHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MySpotsData msd = mySpotsData.get(position);
        holder.txtHeader.setText(msd.header);
        holder.txtSubInfo.setText(msd.subInfo);
        holder.txtInfo.setText(msd.info);
        int icon = R.drawable.ic_add_white_36dp;
        if (msd.status == MySpotsData.STATUS_GONE) {
            icon = R.drawable.ic_done_white_24dp;
        } else if (msd.status == MySpotsData.STATUS_WANT_TO_GO) {
            icon = R.drawable.ic_pin;
        } else if (msd.status == MySpotsData.STATUS_GONE) {
            icon = R.drawable.ic_add_white_36dp;
        }
        holder.imvwIcon.setImageResource(icon);
        holder.imvwIcon.setColorFilter(
                ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary));
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
