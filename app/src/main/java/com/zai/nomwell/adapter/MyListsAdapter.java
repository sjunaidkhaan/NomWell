package com.zai.nomwell.adapter;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.zai.nomwell.R;
import com.zai.nomwell.adapter.holder.MyListsHolder;
import com.zai.nomwell.db.MyListsData;

import java.util.ArrayList;

/**
 * Created by chitta on 12/8/15.
 */
public class MyListsAdapter extends BaseSwipeAdapter<MyListsHolder> {

    private ArrayList<MyListsData> myListData;
    private String swipeButtonText = "Delete";

    public MyListsAdapter(ArrayList<MyListsData> mySpotsData, String swipeButtonText) {
        this.myListData = mySpotsData;
        this.swipeButtonText = swipeButtonText;
    }

    @Override
    public MyListsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyListsHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_all_lists, null), swipeButtonText);
    }

    @Override
    public void onBindViewHolder(MyListsHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MyListsData mld = myListData.get(position);
        holder.txtHeader.setText(mld.header);
        holder.txtInfo.setText(mld.info);
        holder.imvwIcon.setImageResource(mld.iconResource);
        holder.imvwIcon.setColorFilter(
                ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimaryDark));

    }

    @Override
    public int getItemCount() {
        return myListData.size();
    }
}
