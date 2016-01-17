package com.zai.nomwell.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zai.nomwell.R;
import com.zai.nomwell.adapter.holder.NomwellContactsHolder;
import com.zai.nomwell.adapter.holder.NomwellContactsInfoHolder;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.db.MySpotsData;

import java.util.ArrayList;

/**
 * Created by chitta on 12/8/15.
 */
public class NomwellContactsAdapter extends RecyclerView.Adapter<NomwellContactsInfoHolder> {

    private ArrayList<MySpotsData> mySpotsData;

    private OnRecyclerViewClickListener clickListener;

    public NomwellContactsAdapter(ArrayList<MySpotsData> mySpotsData, OnRecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
        this.mySpotsData = mySpotsData;
    }

    @Override
    public NomwellContactsInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new NomwellContactsInfoHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_contact_type_info, null), clickListener);
        }
        return new NomwellContactsHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_nomwell_contacts, null), clickListener);
    }

    @Override
    public void onBindViewHolder(NomwellContactsInfoHolder holder, int position) {
        MySpotsData msd = mySpotsData.get(position);
        if (getItemViewType(position) == 0) {
            holder.txtInfo.setText(msd.info);
        } else {
            NomwellContactsHolder holder1 = (NomwellContactsHolder) holder;
            holder1.txtHeader.setText(msd.header);
            holder1.txtSubInfo.setText(msd.subInfo);
            holder.txtInfo.setText(msd.info);
            holder1.imvwIcon.setImageResource(msd.icon);
            holder1.imvwIcon.setColorFilter(msd.colorFilter);
        }
    }

    public MySpotsData getItemAt(int position) {
        return mySpotsData.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return mySpotsData.get(position).type;
    }

    @Override
    public int getItemCount() {
        return mySpotsData.size();
    }
}
