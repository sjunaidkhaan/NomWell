package com.zai.nomwell.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;

import com.zai.nomwell.R;

/**
 * Created by chitta on 12/20/15.
 */
public class NomwellTab {

    private View view;

    private Context context;

    private View tab;
    private AppCompatTextView txtText;
    private AppCompatTextView txtBadge;
    private AppCompatImageView imvwIcon;

    private int selectedBackround;
    private int normalBackround;

    public NomwellTab(Context context, int selectedBackround, int normalBackround) {
        this.context = context;
        this.selectedBackround = selectedBackround;
        this.normalBackround = normalBackround;
        view = LayoutInflater.from(context).inflate(R.layout.tab, null);
        tab = view.findViewById(R.id.tab);
        txtText = (AppCompatTextView) view.findViewById(R.id.txtText);
        imvwIcon = (AppCompatImageView) view.findViewById(R.id.imvwIcon);
        txtBadge = (AppCompatTextView) view.findViewById(R.id.badge);
    }

    public void setBackground(int resource) {
        view.setBackgroundResource(resource);
    }

    public View getView() {
        return tab;
    }

    public void setText(String text) {
        txtText.setText(text);
    }

    public void setSelected(boolean selected) {
        if (selected) {
            tab.setBackgroundResource(selectedBackround);
            txtText.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            imvwIcon.setColorFilter(
                    ContextCompat.getColor(context, R.color.colorPrimaryDark));
        } else {
            tab.setBackgroundResource(normalBackround);
            txtText.setTextColor(ContextCompat.getColor(context, R.color.white));
            imvwIcon.setColorFilter(
                    ContextCompat.getColor(context, R.color.white));
        }
    }

    public void setBadge(String text) {
        if (text != null) {
            txtBadge.setVisibility(View.VISIBLE);
            txtBadge.setText(text);
        } else {
            txtBadge.setVisibility(View.GONE);
        }
    }

    public String getText() {
        return txtText.getText().toString();
    }

    public void setImageResource(int resource) {
        imvwIcon.setImageResource(resource);
    }
}
