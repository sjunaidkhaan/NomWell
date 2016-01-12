package com.zai.nomwell;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_LATITUDE = "latitude";
    public static final String EXTRA_LONGITUDE = "latitude";


    private GoogleMap mMap;

    private Marker prevMarker = null;

    private double latitude = 0;
    private double longitude = 0;

    private Snackbar snackbar;

    private View layoutBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extra = getIntent().getExtras();
        if (extra != null && extra.containsKey(EXTRA_TITLE)) {
            getSupportActionBar().setTitle(extra.getString(EXTRA_TITLE));
            if (extra.containsKey(EXTRA_LATITUDE)) {
                latitude = extra.getDouble(EXTRA_LATITUDE);
            }

            if (extra.containsKey(EXTRA_LONGITUDE)) {
                longitude = extra.getDouble(EXTRA_LONGITUDE);
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        layoutBottom = findViewById(R.id.layoutBottom);
        layoutBottom.setVisibility(View.GONE);
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMarkerClickListener(this);
        if(latitude < 1 && longitude < 1) {
            setMarkers();
        }
        else {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_gone)));
        }
    }

    private void setMarkers() {
        ArrayList<Spot> spots = getDummyLatLngs();

        for (Spot s : spots) {
            mMap.addMarker(new MarkerOptions()
                    .position(s.latLng)
                    .title(s.title)
                    .snippet(s.snippet)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_none)));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(spots.get(spots.size() - 1).latLng));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (prevMarker != null) {
            prevMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_none));
        }
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_none_selected));
        prevMarker = marker;
        layoutBottom.setVisibility(View.VISIBLE);
        return false;
    }

    public static ArrayList<Spot> getDummyLatLngs() {
        ArrayList<Spot> spots = new ArrayList<>();

        Spot s = new Spot();
        s.latLng = new LatLng(33.58, 85.85);
        s.title = "Anniston, AL";
        spots.add(s);

        s = new Spot();
        s.latLng = new LatLng(32.67, 85.44);
        s.title = "Auburn, AL";
        spots.add(s);

        s = new Spot();
        s.latLng = new LatLng(32.67, 85.44);
        s.title = "Birmingham, AL";
        spots.add(s);

        s = new Spot();
        s.latLng = new LatLng(32.67, 85.44);
        s.title = "Dothan, AL";
        spots.add(s);

        s = new Spot();
        s.latLng = new LatLng(32.90, 87.25);
        s.title = "Fort Rucker, AL";
        spots.add(s);

        s = new Spot();
        s.latLng = new LatLng(32.90, 87.25);
        s.title = "Gadsden, AL";
        spots.add(s);

        s = new Spot();
        s.latLng = new LatLng(31.32, 85.45);
        s.title = "Hunstville, AL";
        spots.add(s);

        s = new Spot();
        s.latLng = new LatLng(31.28, 85.72);
        s.title = "Maxwell AFB, AL";
        spots.add(s);

        s = new Spot();
        s.latLng = new LatLng(33.97, 86.09);
        s.title = "Mobile, AL";
        spots.add(s);

        s = new Spot();
        s.latLng = new LatLng(34.65, 86.77);
        s.title = "Mobile Aeros, AL";
        spots.add(s);

        s = new Spot();
        s.latLng = new LatLng(32.38, 86.37);
        s.title = "Dothan, AL";
        spots.add(s);

        return spots;
    }

    public static class Spot {
        public LatLng latLng;
        public String title;
        public String snippet;
    }
}
