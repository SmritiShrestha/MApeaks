package com.example.simon.nevagationbar;

import com.example.simon.nevagationbar.mapactivity.Dharara;
import com.example.simon.nevagationbar.mapactivity.Kasthamandap;
import com.example.simon.nevagationbar.mapactivity.Natapolo;
import com.example.simon.nevagationbar.mapactivity.ascol;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements
        GoogleMap.OnInfoWindowClickListener,
        OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private Map<Marker,Class>allMarkersMap = new HashMap<Marker, Class>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*for zoom and zoom out and compass*/
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        /*for lat and long*/
        final LatLng dharara = new LatLng(27.700598,85.311890);
        final LatLng pulchowk = new LatLng(27.6782363,85.3168528);
        final LatLng natapolo = new LatLng( 27.671382,85.4294032);
        final LatLng kasthmandap = new LatLng(27.70394313,85.30583918);


        Marker marker1 = mMap.addMarker(new MarkerOptions()
                .position(pulchowk)
                .title("Welcome to pulchowk campus")
                .snippet("Tap me for deatils")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                allMarkersMap.put(marker1,ascol.class);

        Marker marker2 = mMap.addMarker(new MarkerOptions()
                .position(natapolo)
                .title("Welcome to Natapolo")
                .snippet("Tap me for deatils")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        allMarkersMap.put(marker2,Natapolo.class);



        Marker marker3 = mMap.addMarker(new MarkerOptions()
                .position(dharara)
                .title("Welcome to Dharara")
                .snippet("Tap me for deatils")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        allMarkersMap.put(marker3, Dharara.class);


        Marker marker4 = mMap.addMarker(new MarkerOptions()
                .position(kasthmandap)
                .title("Welcome to kasthmadap")
                .snippet("Tap me for deatils")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        allMarkersMap.put(marker4, Kasthamandap.class);





        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //permission to access if the location is missing
            PermissionUtils.requestPermission(this,  LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }else if(mMap != null){
            //access to the location has been granted
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button Clicked", Toast.LENGTH_SHORT).show();
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments(){
        super.onResumeFragments();
        if(mPermissionDenied){
            //if permisision not granted. show error
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
    /*marker ko title ma click garexe arko layout ma jane call back*/
    @Override
    public void onInfoWindowClick(Marker marker) {
        Class cls = allMarkersMap.get(marker);
        Intent intent = new Intent(MapsActivity.this, cls);
        startActivity(intent);
    }
}

