package com.zai.nomwell.view;

import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        txtClickableText.setText(content);
    }

    public String getClickableText() {
        return txtClickableText.getText().toString();
    }

    public void addClickListener(View.OnClickListener clickListener) {
        txtClickableText.setOnClickListener(clickListener);
    }


}
