package com.zai.nomwell.adapter.holder;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.malinskiy.superrecyclerview.swipe.SwipeLayout;
import com.zai.nomwell.R;
import com.zai.nomwell.util.Util;

/**
 * Created by chitta on 12/8/15.
 */
public class MyListsHolder extends BaseSwipeAdapter.BaseSwipeableViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public AppCompatTextView lblDelete;
    public ImageView imvwIcon;
    public AppCompatTextView txtHeader;
    public AppCompatTextView txtInfo;
    private LinearLayout llSwipeContent;

    private OnRecyclerViewClickListener listener;

    public MyListsHolder(View itemView, String swipeButtonText, OnRecyclerViewClickListener listener) {
        super(itemView);

        this.listener = listener;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        lblDelete = (AppCompatTextView) itemView.findViewById(R.id.lblDelete);
        lblDelete.setText(swipeButtonText);
        lblDelete.setOnClickListener(this);

        imvwIcon = (ImageView) itemView.findViewById(R.id.imvwIcon);
        imvwIcon.setOnClickListener(this);
        txtHeader = (AppCompatTextView) itemView.findViewById(R.id.txtHeader);
        txtHeader.setOnClickListener(this);
        txtInfo = (AppCompatTextView) itemView.findViewById(R.id.txtInfo);

        llSwipeContent = (LinearLayout) itemView.findViewById(R.id.llSwipeContent);
        int totalWidth = Util.getScreenWidth(itemView.getContext());
        SwipeLayout.LayoutParams params = (SwipeLayout.LayoutParams) llSwipeContent.getLayoutParams();
        params.width = totalWidth / 3;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClick(view, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (listener != null) {
            listener.onItemLongClick(view, getAdapterPosition());
        }
        return false;
    }
}
