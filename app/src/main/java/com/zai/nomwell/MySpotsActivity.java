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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.zai.nomwell.db.MyListsData;
import com.zai.nomwell.db.MySpotsData;
import com.zai.nomwell.dialog.NomwellListDialog;
import com.zai.nomwell.fragments.MyListsFragment;
import com.zai.nomwell.fragments.MySpotFragment;
import com.zai.nomwell.util.Util;

import java.util.ArrayList;

public class MySpotsActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MySpotsActivity";

    public static final int PERMISSIONS_REQUEST_LOCATION = 1;

    private MySpotFragment mySpotFragment;
    private MyListsFragment myListsFragment;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_spots);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startActivity(new Intent(this, IntroActivity.class));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_my_spots);

        int navigationBarHeight = Util.getNavBarHeight(this);
        Util.log(TAG, "NavigationBar Height: " + navigationBarHeight);
        if (navigationBarHeight > 0) {
            View bottomButtons = findViewById(R.id.content);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) bottomButtons.getLayoutParams();
            params.setMargins(0, 0, 0, navigationBarHeight);
            int padding = getResources().getDimensionPixelSize(R.dimen.padding);
            bottomButtons.setPadding(0, 0, 0, padding);
            bottomButtons.requestLayout();
        }

        if (mySpotFragment == null) {
            mySpotFragment = new MySpotFragment();
        }
        if (myListsFragment == null) {
            myListsFragment = new MyListsFragment();
        }

        setCurrentFragment(mySpotFragment);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNavigationDrawer();
    }

    private void updateNavigationDrawer() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (fragment == mySpotFragment) {
            navigationView.setCheckedItem(R.id.nav_my_spots);
        } else if (fragment == myListsFragment) {
            navigationView.setCheckedItem(R.id.nav_my_lists);
        }
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
            setCurrentFragment(mySpotFragment);
        } else if (id == R.id.nav_my_lists) {
            setCurrentFragment(myListsFragment);
        } else if (id == R.id.nav_switch_cities) {
            startActivity(new Intent(this, ChooseCityActivity.class));
        } else if (id == R.id.nav_import_an_existing_list) {
            showLetsStartedDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.content);
    }

    private void setCurrentFragment(Fragment fragment) {
        if (fragment != getCurrentFragment()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment, MyListsFragment.TAG)
                    .commit();
        }
    }

    /**
     * shows the first dialog
     */
    private void showLetsStartedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.now_lets_get_started));
        NomwellListDialog nomwellListDialog = new NomwellListDialog(this);
        nomwellListDialog.setMessage(getString(R.string.lets_get_started_message));
        nomwellListDialog.setOptions(getResources().getStringArray(R.array.lets_started_options));
        builder.setView(nomwellListDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                if (i == 0) {
                    showFantasticDialog();
                }
                updateNavigationDrawer();
            }
        });

        dialog.show();
    }

    /**
     * shows Fantastic dialog
     */

    private void showFantasticDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.fantastic_));
        NomwellListDialog nomwellListDialog = new NomwellListDialog(this);
        nomwellListDialog.setMessage(getString(R.string.where_is_your_list_located_));
        nomwellListDialog.setOptions(getResources().getStringArray(R.array.list_location_options));
        builder.setView(nomwellListDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    startActivity(new Intent(MySpotsActivity.this, ListImportActivity.class));
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public ArrayList<MySpotsData> getMySpotsDummyData() {
        ArrayList<MySpotsData> mySpotsData = new ArrayList<>();

        MySpotsData msd = new MySpotsData();
        msd.header = "Ada Street";
        msd.info = "$$ - American (New)";
        msd.subInfo = "Note: Try the quiche";
        msd.rating = 5;
        msd.status = MySpotsData.STATUS_GONE;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.header = "Alinea";
        msd.info = "$$ - Japanese";
        msd.subInfo = "";
        msd.rating = 0;
        msd.status = MySpotsData.STATUS_NO;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.header = "Au Cheval";
        msd.info = "$$ - American (New)";
        msd.subInfo = "Note: Burgers, Rec'd by Claydia w...";
        msd.rating = 0;
        msd.status = MySpotsData.STATUS_GONE;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.header = "Avec";
        msd.info = "$$ - Fusion";
        msd.subInfo = "Note: Don't come on the weekends...";
        msd.rating = 4;
        msd.status = MySpotsData.STATUS_WANT_TO_GO;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.header = "Balena";
        msd.info = "$$ - Oyster Bars";
        msd.subInfo = "Note: Oyster were mah";
        msd.rating = 3;
        msd.status = MySpotsData.STATUS_WANT_TO_GO;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.header = "Girls & the Goat";
        msd.info = "$$ - New American";
        msd.subInfo = "";
        msd.rating = 3;
        msd.status = MySpotsData.STATUS_GONE;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.header = "Haymarket Pub & Brewery";
        msd.info = "$$ - Brewery";
        msd.subInfo = "";
        msd.rating = 3;
        msd.status = MySpotsData.STATUS_WANT_TO_GO;
        mySpotsData.add(msd);

        return mySpotsData;
    }

    public ArrayList<MySpotsData> getFilteredDummyData(int status) {
        ArrayList<MySpotsData> all = getMySpotsDummyData();
        ArrayList<MySpotsData> filtered = new ArrayList<>();
        for (MySpotsData msd : all) {
            if (msd.status == status) {
                filtered.add(msd);
            }
        }
        return filtered;
    }

    public ArrayList<MyListsData> getMyListsDummyData() {
        ArrayList<MyListsData> mlds = new ArrayList<>();

        MyListsData mld = new MyListsData();
        mld.header = "Breakfast of Champions";
        mld.info = "22 spots, 13 followers";
        mlds.add(mld);

        mld = new MyListsData();
        mld.header = "Demo";
        mld.info = "11 spots";
        mlds.add(mld);

        mld = new MyListsData();
        mld.header = "Great Coffee";
        mld.info = "17 spots";
        mlds.add(mld);

        mld = new MyListsData();
        mld.header = "Mad Picks";
        mld.info = "14 spots, 13 followers";
        mlds.add(mld);

        mld = new MyListsData();
        mld.header = "Nab it and Go Eats";
        mld.info = "17 spots";
        mlds.add(mld);

        mld = new MyListsData();
        mld.header = "Taco Flavoured Kisses";
        mld.info = "22 spots (13 in Chicago, IL)";
        mlds.add(mld);

        return mlds;
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
