package com.zai.nomwell;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TabbedMapActivity extends BaseActivity implements OnMapReadyCallback, OnMarkerClickListener {

    public static final String EXTRA_TITLE = "title";

    public static final int CHOICE_ALL = 1;
    public static final int CHOICE_WANT_TO_GO = 2;
    public static final int CHOICE_GONE = 3;

    private TabLayout tabLayout;

    private GoogleMap mMap;

    private Marker prevMarker = null;

    private int choice = CHOICE_ALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extra = getIntent().getExtras();
        if (extra != null && extra.containsKey(EXTRA_TITLE)) {
            getSupportActionBar().setTitle(extra.getString(EXTRA_TITLE));
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("ALL"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_pin_drop_white_24dp));
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

        setMarkers();
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
