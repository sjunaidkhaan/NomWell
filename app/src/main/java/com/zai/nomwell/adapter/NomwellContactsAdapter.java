package com.zai.nomwell.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zai.nomwell.R;
import com.zai.nomwell.adapter.holder.NomwellContactsHolder;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.db.MySpotsData;

import java.util.ArrayList;

/**
 * Created by chitta on 12/8/15.
 */
public class NomwellContactsAdapter extends RecyclerView.Adapter<NomwellContactsHolder> {

    private ArrayList<MySpotsData> mySpotsData;

    private OnRecyclerViewClickListener clickListener;

    public NomwellContactsAdapter(ArrayList<MySpotsData> mySpotsData, OnRecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
        this.mySpotsData = mySpotsData;
    }

    @Override
    public NomwellContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NomwellContactsHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_nomwell_contacts, null), clickListener);
    }

    @Override
    public void onBindViewHolder(NomwellContactsHolder holder, int position) {
        MySpotsData msd = mySpotsData.get(position);
        holder.txtHeader.setText(msd.header);
        holder.txtSubInfo.setText(msd.subInfo);
        holder.txtInfo.setText(msd.info);
        holder.imvwIcon.setImageResource(msd.icon);
    }

    public MySpotsData getItemAt(int position) {
        return mySpotsData.get(position);
    }

    @Override
    public int getItemCount() {
        return mySpotsData.size();
    }
}
