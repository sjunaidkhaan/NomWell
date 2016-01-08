package com.zai.nomwell;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class SelectSpotActivity extends BaseActivity {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_SHOW_BOTTOM_BUTTONS = "show_bottom_buttons";

    public boolean showBottomButtons = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_spot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(EXTRA_TITLE)) {
            String title = getIntent().getExtras().getString(EXTRA_TITLE);
            getSupportActionBar().setTitle(title);
            if (getIntent().getExtras().containsKey(EXTRA_SHOW_BOTTOM_BUTTONS)) {
                showBottomButtons = getIntent().getExtras().getBoolean(EXTRA_SHOW_BOTTOM_BUTTONS);
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_spot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_select:
                break;
            case R.id.action_cancel:
                finish();
                break;
        }

        return true;
    }
}
