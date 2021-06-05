package com.example.proxecto;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends AppCompatActivity implements LocationListener, OnMapReadyCallback {


    LocationManager locManager;
    private MapFragment googleMap;
    private double lonx, lat;
    private GoogleMap mapaGoogleReal;
    Button aceptar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        setContentView(R.layout.mapa);
        permisoSD();
        checkPermiossion();

        googleMap = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        googleMap.getMapAsync(this);
    }


    public void checkPermiossion() {
        if (Build.VERSION.SDK_INT >= 23) {
            int permisoLocalizacion = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (permisoLocalizacion == PackageManager.PERMISSION_GRANTED) {
                accessLocation();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);
            }
        }
    }


    public void permisoSD() {


        if (Build.VERSION.SDK_INT >= 23) {
            int permisoCamara = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permisoCamara == PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);

            } else {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
        }
    }


    @SuppressLint("MissingPermission")
    public void accessLocation() {

        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        //criterios de precision el al
        Criteria filtro = new Criteria();

        filtro.setAccuracy(Criteria.ACCURACY_FINE);

        filtro.setAltitudeRequired(true);

        //nome do proveedor "optimo". tamén aqueles que están desactivados
        String provConFiltro = locManager.getBestProvider(filtro, false);

        //        if (!locManager.isProviderEnabled(provConFiltro)) {

        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getApplicationContext(), "O GPS non está activo. Activando...", Toast.LENGTH_LONG).show();
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        // Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //solicitando posicion
        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        //500 metros de distancia
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (locManager != null) {
            Log.i("POSICION", "Liberando Recursos de localización");
            locManager.removeUpdates(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapaGoogleReal = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng pos = new LatLng(lat, lonx);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 1));

    }

    public void amosaPuntosAvistamento() {
       // 42.26711147, -8.78709151
        if (mapaGoogleReal != null) {
            double[][] lonxLat = new double[][]{{42.26711147, -8.78709151}, {42.20711147, -8.78709151}, {42.26711147, -8.801}};
            LatLng posicion = null;
            for (int i = 0; i < lonxLat.length; i++) {
                posicion = new LatLng(lonxLat[i][0], lonxLat[i][1]);
                if (mapaGoogleReal != null) {
                    mapaGoogleReal.addMarker(new MarkerOptions()

                            //.position(mapaGoogleReal.getCameraPosition().target)
                            .position(posicion)


                            .snippet("Avistador " + posicion)

                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrowsmall)));
                }
            }
            mapaGoogleReal.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 10));
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lonx = location.getLongitude();
        Toast.makeText(getApplicationContext(), "Latitude:  " + lat + " Lonxitude: " + lonx, Toast.LENGTH_LONG).show();
        LatLng pos = new LatLng(lat, lonx);
        if (mapaGoogleReal != null) {
            mapaGoogleReal.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
            mapaGoogleReal.addMarker(new MarkerOptions()

                    .position(mapaGoogleReal.getCameraPosition().target)

                    .title("POSICIÓN")

                    .snippet("Manuel González " + mapaGoogleReal.getCameraPosition().target.toString())

                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrowsmall)));
        }
       // amosaPuntosAvistamento();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 3: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    accessLocation();
                } else {
                    Toast.makeText(getApplicationContext(), "Precisas conceder este permiso para usar a aplicación de forma correta", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    accessLocation();
                } else {
                    Toast.makeText(getApplicationContext(), "Precisas conceder este permiso para usar a aplicación de forma correta", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}