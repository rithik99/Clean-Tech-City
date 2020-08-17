package com.example.subratha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class showall extends AppCompatActivity implements OnMapReadyCallback {

    ImageView i1,i2,i3;
    TextView txt1,txt2;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private  static final int REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showall);
        initfab();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }
    private void initfab() {
        txt1 = (TextView)findViewById(R.id.txt1);
        txt2 = (TextView)findViewById(R.id.txt2);
        i1 = findViewById(R.id.i1);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(showall.this,splash1.class);
                startActivity(i);
            }
        });
        i2 = findViewById(R.id.i2);
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(showall.this,splash2.class);
                startActivity(i);
            }
        });
        i3 = findViewById(R.id.i3);
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(showall.this,conta.class);
                startActivity(i);
            }
        });
    }


    private void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null){
                    currentLocation = location;
                    String h = 12.751324 + " , " + 80.1967690;
                    Toast.makeText(getApplicationContext(),h,Toast.LENGTH_LONG).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(showall.this);
                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(4);
        googleMap.setBuildingsEnabled(true);
        googleMap.setTrafficEnabled(true);

        LatLng latLng1;
        InputStream is = getResources().openRawResource(R.raw.locations);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line;
        try{
            while((line = reader.readLine())!= null){
                String[] tokens = line.split(",");
                latLng1 = new LatLng(Double.parseDouble(tokens[0]),Double.parseDouble(tokens[1]));
                MarkerOptions options = new MarkerOptions().position(latLng1).title(tokens[2]).snippet(tokens[3]);
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng1));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 16));
                googleMap.addMarker(options);
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String l = marker.getSnippet();
                        if(l.equals("Organic")){
                            txt2.setTextColor(Color.rgb(50,205,50));
                        }
                        else if(l.equals("Plastic")){
                            txt2.setTextColor(Color.rgb(0,0,255));
                        }
                        else if(l.equals("Paper")){
                            txt2.setTextColor(Color.rgb(204,204,0));
                        }
                        txt1.setText(marker.getTitle());
                        txt2.setText(marker.getSnippet());
                        return true;
                    }
                });

            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLastLocation();
            }
        }
    }
}
