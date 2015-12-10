package com.zai.nomwell.adapter.holder;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.zai.nomwell.R;

/**
 * Created by chitta on 12/8/15.
 */
public class MyListsHolder extends BaseSwipeAdapter.BaseSwipeableViewHolder {

    public AppCompatTextView lblDelete;
    public ImageView imvwIcon;
    public AppCompatTextView txtHeader;
    public AppCompatTextView txtInfo;

    public MyListsHolder(View itemView, String swipeButtonText) {
        super(itemView);

        lblDelete = (AppCompatTextView) itemView.findViewById(R.id.lblDelete);
        lblDelete.setText(swipeButtonText);

        imvwIcon = (ImageView) itemView.findViewById(R.id.imvwIcon);
        txtHeader = (AppCompatTextView) itemView.findViewById(R.id.txtHeader);
        txtInfo = (AppCompatTextView) itemView.findViewById(R.id.txtInfo);
    }
}
