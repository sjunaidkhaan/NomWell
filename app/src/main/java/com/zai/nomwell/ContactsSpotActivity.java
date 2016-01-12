package com.zai.nomwell;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ContactsSpotActivity extends BaseActivity {

    public static final String EXTRA_TITLE = "title";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_spot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extra = getIntent().getExtras();
        if (extra != null && extra.containsKey(EXTRA_TITLE)) {
            getSupportActionBar().setTitle(extra.getString(EXTRA_TITLE));
        }
    }

}
