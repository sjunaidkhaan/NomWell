package com.zai.nomwell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

public class StartingActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatTextView txtLogin;
    private AppCompatTextView txtSignUp;

    private AppCompatButton btnSignUpWithEmail;

    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        txtLogin = (AppCompatTextView) findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(this);
        txtSignUp = (AppCompatTextView) findViewById(R.id.txtSignUp);
        txtSignUp.setOnClickListener(this);

        btnSignUpWithEmail = (AppCompatButton) findViewById(R.id.btnSignUpWithEmail);
        btnSignUpWithEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtLogin: {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra(LoginActivity.EXTRA_MODE, LoginActivity.MODE_LOGIN);
                startActivity(intent);
            }
            break;
            case R.id.txtSignUp:
                break;
            case R.id.btnSignUpWithEmail: {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra(LoginActivity.EXTRA_MODE, LoginActivity.MODE_SINGUP);
                startActivity(intent);
            }
            break;
        }
    }
}
