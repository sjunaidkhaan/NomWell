package com.zai.nomwell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class StartingActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatTextView txtLogin;
    private AppCompatTextView txtSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtLogin = (AppCompatTextView) findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(this);
        txtSignUp = (AppCompatTextView) findViewById(R.id.txtSignUp);
        txtSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.txtSignUp:
                break;
        }
    }
}
