package com.zai.nomwell.adapter.holder;

import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.zai.nomwell.R;

/**
 * Created by chitta on 12/8/15.
 */
public class MySpotsHolder extends BaseSwipeAdapter.BaseSwipeableViewHolder {

    public ImageView lblWantToGo;
    public ImageView lblGone;
    public ImageView imvwIcon;
    public AppCompatTextView txtHeader;
    public AppCompatTextView txtInfo;
    public AppCompatTextView txtSubInfo;
    public AppCompatRatingBar ratingBar;

    public MySpotsHolder(View itemView) {
        super(itemView);

        lblWantToGo = (ImageView) itemView.findViewById(R.id.imvwWantToGo);
        lblGone = (ImageView) itemView.findViewById(R.id.imvwGone);

        imvwIcon = (ImageView) itemView.findViewById(R.id.imvwIcon);
        txtHeader = (AppCompatTextView) itemView.findViewById(R.id.txtHeader);
        txtInfo = (AppCompatTextView) itemView.findViewById(R.id.txtInfo);
        txtSubInfo = (AppCompatTextView) itemView.findViewById(R.id.txtSubInfo);
        ratingBar = (AppCompatRatingBar) itemView.findViewById(R.id.ratingBar);
    }
}
