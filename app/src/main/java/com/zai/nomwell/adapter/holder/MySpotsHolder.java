package com.zai.nomwell.adapter.holder;

import android.support.v7.widget.AppCompatRatingBar;
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
public class MySpotsHolder extends BaseSwipeAdapter.BaseSwipeableViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private OnRecyclerViewClickListener clickListener;

    public ImageView imvwWanToGo;
    public ImageView imvwGone;
    public ImageView imvwIcon;
    public ImageView imvwCheck;
    public AppCompatTextView txtHeader;
    public AppCompatTextView txtInfo;
    public AppCompatTextView txtSubInfo;
    public AppCompatRatingBar ratingBar;
    private LinearLayout llSwipeContent;

    public MySpotsHolder(View itemView, boolean showCheck, OnRecyclerViewClickListener clickListener) {
        super(itemView);

        this.clickListener = clickListener;

        imvwWanToGo = (ImageView) itemView.findViewById(R.id.imvwWantToGo);
        imvwWanToGo.setOnClickListener(this);
        imvwGone = (ImageView) itemView.findViewById(R.id.imvwGone);
        imvwGone.setOnClickListener(this);
        imvwCheck = (ImageView) itemView.findViewById(R.id.imvwCheck);
        imvwCheck.setOnClickListener(this);

        imvwIcon = (ImageView) itemView.findViewById(R.id.imvwIcon);
        txtHeader = (AppCompatTextView) itemView.findViewById(R.id.txtHeader);
        txtInfo = (AppCompatTextView) itemView.findViewById(R.id.txtInfo);
        txtSubInfo = (AppCompatTextView) itemView.findViewById(R.id.txtSubInfo);
        ratingBar = (AppCompatRatingBar) itemView.findViewById(R.id.ratingBar);

        llSwipeContent = (LinearLayout) itemView.findViewById(R.id.llSwipeContent);
        int totalWidth = Util.getScreenWidth(itemView.getContext());
        SwipeLayout.LayoutParams params = (SwipeLayout.LayoutParams) llSwipeContent.getLayoutParams();
        params.width = totalWidth / 2;

        imvwCheck.setVisibility(showCheck ? View.VISIBLE : View.GONE);
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
