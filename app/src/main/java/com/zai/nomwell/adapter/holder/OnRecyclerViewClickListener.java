package com.zai.nomwell.adapter.holder;

import android.view.View;

/**
 * Created by chitta on 12/14/15.
 */
public interface OnRecyclerViewClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}
