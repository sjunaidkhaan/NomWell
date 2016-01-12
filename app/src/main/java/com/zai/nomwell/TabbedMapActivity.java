package com.zai.nomwell;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zai.nomwell.dialog.NomwellFilterDialog;
import com.zai.nomwell.dialog.NomwellMultipleChoiceListDialog;

import java.util.ArrayList;

public class TabbedMapActivity extends BaseActivity implements OnMapReadyCallback, OnMarkerClickListener {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_MODE = "mode";

    public static final int MODE_NORMAL = 1;
    public static final int MODE_FILTER = 2;
    public static final int MODE_CONTACTS = 3;

    public static final int CHOICE_ALL = 1;
    public static final int CHOICE_WANT_TO_GO = 2;
    public static final int CHOICE_GONE = 3;

    private TabLayout tabLayout;

    private String title;

    private GoogleMap mMap;

    private int mode = -1;

    private Marker prevMarker = null;

    private int choice = CHOICE_ALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            if (extra.containsKey(EXTRA_TITLE)) {
                title = extra.getString(EXTRA_TITLE);
            }
            if (extra.containsKey(EXTRA_MODE)) {
                mode = extra.getInt(EXTRA_MODE);
            }
        }
        if (mode == MODE_CONTACTS) {
            setTheme(R.style.ContactsAppThemeNoActionBar);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(title);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (mode == MODE_CONTACTS) {
            tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_filter));
        }
        tabLayout.addTab(tabLayout.newTab().setText("ALL"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_pin_24));
        tabLayout.getTabAt(1).getIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_done_white_24dp));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                choice = tab.getPosition() + 1;
                setMarkers();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        setMarkers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mode == MODE_FILTER) {
            getMenuInflater().inflate(R.menu.menu_tabbed_map, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                showFilterDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Spot Filter");
        NomwellFilterDialog nomwellFilterDialog = new NomwellFilterDialog(this);
        nomwellFilterDialog.setCuisineText("American (New) or Vegan");
        nomwellFilterDialog.setTagText("BYOB");
        nomwellFilterDialog.addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.txtCuisine:
                        showCuisinesDialog();
                        break;
                    case R.id.txtTag:
                        showTagsDialog();
                        break;
                }
            }
        });
        builder.setView(nomwellFilterDialog.getView());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Back", null);
        builder.create().show();
    }

    private void showCuisinesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Cuisines");
        final NomwellMultipleChoiceListDialog nomwellListDialog = new NomwellMultipleChoiceListDialog(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                SparseBooleanArray array = nomwellListDialog.getCheckedItems();

            }
        });
        builder.setNegativeButton("Cancel", null);
        nomwellListDialog.setMultipleOptions(new String[]{
                "American (New)", "American (Traditional)", "Caribbean", "Chinese", "Indian",
                "Polish", "Southern", "Vegan", "Vegetarian"
        }, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nomwellListDialog.setChecked(i, nomwellListDialog.isChecked(i));
            }
        });
        builder.setView(nomwellListDialog.getView());
        builder.create().show();

    }

    private void showTagsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Tags");
        final NomwellMultipleChoiceListDialog nomwellListDialog = new NomwellMultipleChoiceListDialog(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                SparseBooleanArray array = nomwellListDialog.getCheckedItems();
            }
        });
        builder.setNegativeButton("Cancel", null);
        nomwellListDialog.setMultipleOptions(new String[]{
                "Beer Gardens", "BYOB", "Clubs", "Date Spots", "Duke Bar",
                "Good For Groups", "Late Night", "MSU Bar", "Pool"
        }, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nomwellListDialog.setChecked(i, nomwellListDialog.isChecked(i));
            }
        });
        builder.setView(nomwellListDialog.getView());
        builder.create().show();

    }

    private void setMarkers() {

        mMap.clear();

        ArrayList<MapActivity.Spot> spots = MapActivity.getDummyLatLngs();

        int i = 0;

        for (MapActivity.Spot s : spots) {
            MarkerOptions options = new MarkerOptions()
                    .position(s.latLng)
                    .title(s.title)
                    .snippet(s.snippet);
            if (choice == CHOICE_ALL) {
                if (i % 2 == 0) {
                    options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_want_to_go));
                } else {
                    options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_gone));
                }
            } else if (choice == CHOICE_GONE) {
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_gone));
            } else if (choice == CHOICE_WANT_TO_GO) {
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_want_to_go));
            }
            i++;
            mMap.addMarker(options);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(spots.get(spots.size() - 1).latLng));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (prevMarker != null) {
            if (choice == CHOICE_WANT_TO_GO) {
                prevMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_want_to_go));
            } else {
                prevMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_gone));
            }
        }
        if (choice == CHOICE_WANT_TO_GO) {
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_want_to_go_selected));
        } else {
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_gone_selected));
        }
        prevMarker = marker;
        return false;
    }
}
