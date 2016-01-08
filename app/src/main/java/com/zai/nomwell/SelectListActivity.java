package com.zai.nomwell;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class SelectListActivity extends BaseActivity {

    public static final String EXTRA_TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(EXTRA_TITLE)) {
            String title = getIntent().getExtras().getString(EXTRA_TITLE);
            getSupportActionBar().setTitle(title);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
