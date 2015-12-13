package com.zai.nomwell.dialog;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.zai.nomwell.R;

/**
 * Created by chitta on 12/6/15.
 */
public class NomwellImageDialog {

    private View view;

    private AppCompatTextView txtMessage;
    private AppCompatImageView imvwImage;

    private AppCompatButton btnPositive;
    private AppCompatButton btnNegative;

    public NomwellImageDialog(Context context) {
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_message_image, null);
        init();
    }

    public View getView() {
        return view;
    }

    private void init() {
        txtMessage = (AppCompatTextView) view.findViewById(R.id.txtMessage);
        imvwImage = (AppCompatImageView) view.findViewById(R.id.imvwImage);
        btnPositive = (AppCompatButton) view.findViewById(R.id.btnPositive);
        btnPositive.setVisibility(View.GONE);
        btnNegative = (AppCompatButton) view.findViewById(R.id.btnNegative);
        btnNegative.setVisibility(View.GONE);
    }

    public String getMessage() {
        return txtMessage.getText().toString();
    }

    public void setMessage(String message) {
        txtMessage.setText(message);
    }

    public void setImage(int resourceId) {
        Glide.with(view.getContext()).load(resourceId).into(imvwImage);
    }

    public void setPositive(String text, View.OnClickListener listener) {
        btnPositive.setVisibility(View.VISIBLE);
        btnPositive.setText(text);
        btnPositive.setOnClickListener(listener);
    }

    public void setNegative(String text, View.OnClickListener listener) {
        btnNegative.setVisibility(View.VISIBLE);
        btnNegative.setText(text);
        btnNegative.setOnClickListener(listener);
    }
}
