package com.zai.nomwell;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Base activity provides generic methods in it
 * Created by chitta on 12/6/15.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
