package com.zai.nomwell.adapter.holder;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zai.nomwell.R;

/**
 * Created by chitta on 12/8/15.
 */
public class NomwellContactsInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private OnRecyclerViewClickListener clickListener;

    public AppCompatTextView txtInfo;

    public NomwellContactsInfoHolder(View itemView, OnRecyclerViewClickListener clickListener) {
        super(itemView);

        this.clickListener = clickListener;

        itemView.setOnClickListener(this);

        txtInfo = (AppCompatTextView) itemView.findViewById(R.id.txtInfo);
    }

    @Override
    public void onClick(View view) {
        if (clickListener != null) {
            clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (clickListener != null) {
            clickListener.onItemLongClick(view, getAdapterPosition());
        }
        return false;
    }
}
