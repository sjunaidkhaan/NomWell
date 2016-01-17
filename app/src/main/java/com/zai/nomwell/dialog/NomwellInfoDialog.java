package com.zai.nomwell.dialog;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;

import com.zai.nomwell.R;

/**
 * Created by chitta on 12/6/15.
 */
public class NomwellInfoDialog {

    private View view;

    private CharSequence message;

    private AppCompatButton btnPositive;
    private AppCompatButton btnNegative;

    private AppCompatTextView txtMessage;
    private AppCompatTextView txtBoldMessage;

    public NomwellInfoDialog(Context context) {
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_message_info, null);
        init();
    }

    public View getView() {
        return view;
    }

    private void init() {
        txtMessage = (AppCompatTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setVisibility(View.GONE);
        txtBoldMessage = (AppCompatTextView) view.findViewById(R.id.txtBoldMessage);
        txtBoldMessage.setVisibility(View.GONE);
        btnPositive = (AppCompatButton) view.findViewById(R.id.btnPositive);
        btnNegative = (AppCompatButton) view.findViewById(R.id.btnNegative);
    }

    public CharSequence getMessage() {
        return message;
    }

    public void setMessage(CharSequence message) {
        txtMessage.setText(message.toString());
        txtMessage.setVisibility(message != null ? View.VISIBLE : View.INVISIBLE);
        this.message = message;
    }

    public void setBoldMessage(CharSequence message) {
        txtBoldMessage.setText(message.toString());
        txtBoldMessage.setVisibility(message != null ? View.VISIBLE : View.INVISIBLE);
    }

    public void setPositive(String text, View.OnClickListener listener) {
        btnPositive.setText(text);
        btnPositive.setOnClickListener(listener);
    }

    public void setNegative(String text, View.OnClickListener listener) {
        btnNegative.setText(text);
        btnNegative.setOnClickListener(listener);
    }
}
