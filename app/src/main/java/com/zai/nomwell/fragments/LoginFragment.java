package com.zai.nomwell.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zai.nomwell.ChooseCityActivity;
import com.zai.nomwell.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private AppCompatEditText txtFirstName;
    private AppCompatEditText txtLastName;
    private AppCompatEditText txtEmail;
    private AppCompatEditText txtPassword;

    private AppCompatButton btnSignUp;

    private boolean isFirstNameEmpty = true;
    private boolean isLastNameEmpty = true;
    private boolean isEmailEmpty = true;
    private boolean isPasswordEmpty = true;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtFirstName = (AppCompatEditText) view.findViewById(R.id.txtFirstName);
        txtFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isFirstNameEmpty = editable.length() < 1;
                showHideSignUp(!isFirstNameEmpty && !isLastNameEmpty
                        && !isEmailEmpty && !isPasswordEmpty);
            }
        });
        txtLastName = (AppCompatEditText) view.findViewById(R.id.txtLastName);
        txtLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isLastNameEmpty = editable.length() < 1;
                showHideSignUp(!isFirstNameEmpty && !isLastNameEmpty
                        && !isEmailEmpty && !isPasswordEmpty);
            }
        });
        txtEmail = (AppCompatEditText) view.findViewById(R.id.txtEmailAddress);
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isEmailEmpty = editable.length() < 1;
                showHideSignUp(!isFirstNameEmpty && !isLastNameEmpty
                        && !isEmailEmpty && !isPasswordEmpty);
            }
        });
        txtPassword = (AppCompatEditText) view.findViewById(R.id.txtPassword);
        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isPasswordEmpty = editable.length() < 1;
                showHideSignUp(!isFirstNameEmpty && !isLastNameEmpty
                        && !isEmailEmpty && !isPasswordEmpty);
            }
        });

        btnSignUp = (AppCompatButton) view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);

        showHideSignUp(!isFirstNameEmpty && !isLastNameEmpty
                && !isEmailEmpty && !isPasswordEmpty);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                startActivity(new Intent(getActivity(), ChooseCityActivity.class));
                break;
        }
    }

    @Override
    public String getFragmentTitle(Context context) {
        return context.getString(R.string.login);
    }

    private void showHideSignUp(boolean show) {
        btnSignUp.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}
