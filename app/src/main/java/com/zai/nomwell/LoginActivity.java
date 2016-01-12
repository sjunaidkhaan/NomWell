package com.zai.nomwell;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.zai.nomwell.fragments.LoginFragment;

public class LoginActivity extends BaseActivity {

    public static final String EXTRA_MODE = "mode";

    public static final int MODE_SINGUP = 1;
    public static final int MODE_LOGIN = 2;
    public static final int MODE_EDIT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extra = getIntent().getExtras();
        if (extra != null && extra.containsKey(LoginActivity.EXTRA_MODE)) {
            int mode = extra.getInt(LoginActivity.EXTRA_MODE);
            if(mode == MODE_EDIT) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }


        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, loginFragment, LoginFragment.TAG)
                .commit();
    }
}
