package com.zai.nomwell;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.zai.nomwell.fragments.MyListsFragment;
import com.zai.nomwell.fragments.MySpotFragment;
import com.zai.nomwell.util.Util;

public class MySpotsActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MySpotsActivity";

    public static final int PERMISSIONS_REQUEST_LOCATION = 1;

    private MySpotFragment mySpotFragment;
    private MyListsFragment myListsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_spots);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_my_spots);

        int navigationBarHeight = Util.getNavBarHeight(this);
        if (navigationBarHeight > 0) {
            View bottomButtons = findViewById(R.id.content);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) bottomButtons.getLayoutParams();
            params.setMargins(0, 0, 0, navigationBarHeight);
            int padding = getResources().getDimensionPixelSize(R.dimen.padding);
            bottomButtons.setPadding(0, 0, 0, padding);
            bottomButtons.requestLayout();
        }

        mySpotFragment = new MySpotFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, mySpotFragment, MySpotFragment.TAG)
                .commit();

        myListsFragment = new MyListsFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_spots) {
            if (mySpotFragment != getCurrentFragment()) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, mySpotFragment, MySpotFragment.TAG)
                        .commit();
            }
        } else if (id == R.id.nav_my_lists) {
            if (myListsFragment != getCurrentFragment()) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, myListsFragment, MyListsFragment.TAG)
                        .commit();
            }
        } else if (id == R.id.nav_switch_cities) {
            startActivity(new Intent(this, ChooseCityActivity.class));
        } else if (id == R.id.nav_import_an_existing_list) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.content);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
