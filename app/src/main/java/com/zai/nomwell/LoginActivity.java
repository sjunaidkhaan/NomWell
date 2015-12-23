package com.zai.nomwell;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.zai.nomwell.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MODE = "mode";

    public static final int MODE_SINGUP = 1;
    public static final int MODE_LOGIN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, loginFragment, LoginFragment.TAG)
                .commit();
    }
}
