package com.zai.nomwell.view;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.zai.nomwell.R;

/**
 * Created by chitta on 12/7/15.
 */
public class NomwellHalfClickableTextView {

    private View view;

    private AppCompatTextView txtNormalText;
    private AppCompatTextView txtClickableText;

    public NomwellHalfClickableTextView(View container) {
        this.view = container;
        init();
    }

    private void init() {
        txtNormalText = (AppCompatTextView) view.findViewById(R.id.txtNormal);
        txtClickableText = (AppCompatTextView) view.findViewById(R.id.txtClickable);
    }

    public void setNormalText(String text) {
        txtNormalText.setText(text);
    }

    public String getNormalText() {
        return txtNormalText.getText().toString();
    }

    public void setClickableText(String text) {
        txtClickableText.setText(text);
    }

    public String getClickableText() {
        return txtClickableText.getText().toString();
    }


}
