package com.zai.nomwell.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.zai.nomwell.R;

/**
 * Created by chitta on 12/21/15.
 */
public class NavigationArrayAdapter extends ArrayAdapter<String> {

    public NavigationArrayAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (position == 0) {
            Drawable drawable = ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_home);
//            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.);
            AppCompatTextView text1 = (AppCompatTextView) view.findViewById(android.R.id.text1);
            text1.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
        return view;
    }
}
