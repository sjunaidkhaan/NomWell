package com.zai.nomwell.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zai.nomwell.ChooseCityActivity;
import com.zai.nomwell.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private AppCompatButton btnSignUp;

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

        btnSignUp = (AppCompatButton) view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
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
}
