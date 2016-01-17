package com.zai.nomwell;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.zai.nomwell.fragments.SelectListFragment;

public class SelectListActivity extends BaseActivity {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_SHARE_BUTTON_VISIBILITY = "share_button_visibility";
    public static final String EXTRA_CREATE_LIST_BUTTON_VISIBILITY = "create_list_button_visibility";
    public static final String EXTRA_ADD_TO_LIST_BUTTON_VISIBILITY = "add_to_list_button_visibility";

    public static final String EXTRA_CREATE_LIST_TITLE = "add_button_title";

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

        SelectListFragment selectListFragment = new SelectListFragment();
        selectListFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, selectListFragment, SelectListFragment.TAG)
                .commit();
    }
}
