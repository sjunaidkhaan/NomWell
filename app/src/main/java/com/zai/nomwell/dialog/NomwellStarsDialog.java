package com.zai.nomwell.dialog;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;

import com.zai.nomwell.R;

/**
 * Created by chitta on 12/6/15.
 */
public class NomwellStarsDialog {

    private View view;

    private AppCompatTextView txtMessage;
    private AppCompatTextView txtNote;
    private AppCompatRatingBar ratingBar;

    private AppCompatButton btnPositive;
    private AppCompatButton btnNegative;

    public NomwellStarsDialog(Context context) {
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_stars, null);
        init();
    }

    public View getView() {
        return view;
    }

    private void init() {
        txtMessage = (AppCompatTextView) view.findViewById(R.id.txtMessage);
        txtNote = (AppCompatTextView) view.findViewById(R.id.txtNote);
        ratingBar = (AppCompatRatingBar) view.findViewById(R.id.ratingBar);
        btnPositive = (AppCompatButton) view.findViewById(R.id.btnPositive);
        btnNegative = (AppCompatButton) view.findViewById(R.id.btnNegative);
    }

    public String getMessage() {
        return txtMessage.getText().toString();
    }

    public void setMessage(String message) {
        txtMessage.setText(message);
    }

    public String getNote() {
        return txtNote.getText().toString();
    }

    public void setNote(String note) {
        txtNote.setText(note);
    }

    public int getStars() {
        return ratingBar.getNumStars();
    }

    public void setStars(int stars) {
        ratingBar.setNumStars(stars);
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
