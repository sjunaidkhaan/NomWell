package com.zai.nomwell.adapter.holder;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.zai.nomwell.R;

/**
 * Created by chitta on 12/8/15.
 */
public class NomwellContactsHolder extends NomwellContactsInfoHolder implements View.OnClickListener, View.OnLongClickListener {

    private OnRecyclerViewClickListener clickListener;

    public ImageView imvwIcon;
    public AppCompatTextView txtHeader;
    public AppCompatTextView txtSubInfo;

    public NomwellContactsHolder(View itemView, OnRecyclerViewClickListener clickListener) {
        super(itemView, clickListener);

        this.clickListener = clickListener;

        itemView.setOnClickListener(this);

        imvwIcon = (ImageView) itemView.findViewById(R.id.imvwIcon);
        imvwIcon.setOnClickListener(this);
        txtHeader = (AppCompatTextView) itemView.findViewById(R.id.txtHeader);
        txtSubInfo = (AppCompatTextView) itemView.findViewById(R.id.txtSubInfo);
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
