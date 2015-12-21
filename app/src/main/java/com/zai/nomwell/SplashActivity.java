package com.zai.nomwell;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;

public class SplashActivity extends AppCompatActivity {

    private AppCompatImageView imvwIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imvwIcon = (AppCompatImageView) findViewById(R.id.imvwIcon);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), StartingActivity.class));
                finish();
            }
        }, 1000);
    }
}
