package com.zai.nomwell;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.zai.nomwell.dialog.NomwellInputDialog;
import com.zai.nomwell.dialog.NomwellListDialog;
import com.zai.nomwell.fragments.BaseFragment;
import com.zai.nomwell.fragments.MySpotAllFragment;
import com.zai.nomwell.fragments.MySpotPinnedFragment;
import com.zai.nomwell.fragments.MySpotVisitedFragment;

import java.util.ArrayList;

public class MySpotsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private ArrayList<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_spots);

        fragments.add(new MySpotAllFragment());
        fragments.add(new MySpotPinnedFragment());
        fragments.add(new MySpotVisitedFragment());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_my_spots);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_spots, menu);
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
            case R.id.action_lets_started:
                showLetsStartedDialog();
                break;
            case R.id.action_fantastic:
                showFantasticDialog();
                break;
            case R.id.action_confirm_email:
                showConfirmEmailDialog();
                break;
        }


        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_spots) {
            // Handle the camera action
        } else if (id == R.id.nav_my_lists) {

        } else if (id == R.id.nav_switch_cities) {

        } else if (id == R.id.nav_import_an_existing_list) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    startActivity(new Intent(getBaseContext(), ListImportActivity.class));
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showConfirmEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.confirm_email_address));
        final NomwellInputDialog nomwellInputDialog = new NomwellInputDialog(this);
        nomwellInputDialog.setMessage(getString(R.string.where_is_your_list_located_));
        nomwellInputDialog.setHint(getString(R.string.enter_email_address));
        builder.setView(nomwellInputDialog.getView());
        final AlertDialog dialog = builder.create();

        nomwellInputDialog.setPositive("Submit", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        nomwellInputDialog.setNegative("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).getFragmentTitle(getBaseContext());
        }
    }
}
