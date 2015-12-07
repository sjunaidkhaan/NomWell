package com.zai.nomwell.dialog;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;

import com.zai.nomwell.R;

/**
 * Created by chitta on 12/6/15.
 */
public class NomwellInputDialog {

    private View view;

    private String message;
    private String hint;
    private String inputText;

    private AppCompatButton btnPositive;
    private AppCompatButton btnNegative;

    private AppCompatTextView txtMessage;
    private AppCompatEditText txtInput;

    public NomwellInputDialog(Context context) {
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_message_input, null);
        init();
    }

    public View getView() {
        return view;
    }

    private void init() {
        txtMessage = (AppCompatTextView) view.findViewById(R.id.txtMessage);
        txtInput = (AppCompatEditText) view.findViewById(R.id.txtInput);
        btnPositive = (AppCompatButton) view.findViewById(R.id.btnPositive);
        btnNegative = (AppCompatButton) view.findViewById(R.id.btnNegative);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        txtMessage.setText(message);
        this.message = message;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
        txtInput.setHint(hint);
    }

    public String getInputText() {
        inputText = txtInput.getText().toString();
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
        txtInput.setText(inputText);
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
