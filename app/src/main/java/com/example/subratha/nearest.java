package com.example.subratha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class nearest extends AppCompatActivity implements OnMapReadyCallback {

    ImageView i1,i2,i3;
    GoogleMap map;
    TextView txt1;
    Double plat1,plat2;
    Polyline currentPolyline;
    Location currentLocation;
    String h;
    TextView txt0;
    FusedLocationProviderClient fusedLocationProviderClient;
    private  static final int REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest);
        initfab();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    private void initfab() {
        txt1 = (TextView)findViewById(R.id.txt1);
        txt0 =(TextView)findViewById(R.id.revb) ;
        i1 = findViewById(R.id.i1);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(nearest.this,splash1.class);
                startActivity(i);
            }
        });
        i2 = findViewById(R.id.i2);
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(nearest.this,splash2.class);
                startActivity(i);
            }
        });
        i3 = findViewById(R.id.i3);
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(nearest.this,conta.class);
                startActivity(i);
            }
        });
        txt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h = txt1.getText().toString();
                Intent i  = new Intent(nearest.this,review.class);
                i.putExtra("bname",h);
                startActivity(i);
            }
        });

    }

    private String getUrl(LatLng latLng, LatLng latLng1, String mode) {
        String str_orgin = "origin="+latLng.latitude+","+latLng.longitude;
        String str_des = "destination="+latLng1.latitude+","+latLng1.longitude;
        String modee = "mode="+mode;
        String params = str_orgin+"&"+str_des+"&"+modee;
        String op = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"+op+"?"+params+"&key="+getString(R.string.ky);
        return url;
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
                    //Toast.makeText(getApplicationContext(),h,Toast.LENGTH_LONG).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(nearest.this);
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        googleMap.setMapType(4);
        googleMap.setBuildingsEnabled(true);
        googleMap.setTrafficEnabled(true);
        LatLng latLng1;
        LatLng latLng = new LatLng(12.751324,80.1967690);
        InputStream is = getResources().openRawResource(R.raw.locations);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        Double lat = 12.751324;
//        Double lat = 12.750319233422545;
        Double lon = 80.1967690;
//        Double lon = 80.20015387039035;
        Double newlat;
        Double newlon;
        Double reqlat = null;
        Double reqlon = null;
        Double dist;
        String line;
        String titl = null;
        try{
            line = reader.readLine();
            String[] h = line.split(",");
            Double var1 = Double.parseDouble(h[0]);
            Double var2 = Double.parseDouble(h[1]);
            Double varlat = Math.pow((lat - var1),2);
            Double varlon = Math.pow((lon - var2),2);
            Double mdist = Math.sqrt(varlat+varlon);
            reqlon = var2;
            reqlat = var1;
            titl  = h[2];
            Log.d("Initial ","well :  "+ mdist + "/" +titl);
            while((line = reader.readLine())!= null){
                String[] tokens = line.split(",");
                lat = 12.751324;
//                lat = 12.750319233422545;
                lon = 80.1967690;
//                lon = 80.20015387039035;
                newlat = Double.parseDouble(tokens[0]);
                newlon=Double.parseDouble(tokens[1]);
                varlat = Math.pow((lat - newlat),2);
                varlon = Math.pow((lon - newlon),2);
                dist = Math.sqrt(varlat+varlon);

                if(dist<mdist){
                    reqlat = newlat;
                    reqlon = newlon;
                    titl = tokens[2];
                    mdist = dist;
                    plat1 = reqlat;
                    plat2 = reqlon;
                }

                Log.d("Loop ","well :  "+ dist + "/" +titl+"/"+tokens[2]);

            }
            Log.d("Final ","MIN DIST: "+ mdist+"/"+ titl);

        }catch (IOException e){
            e.printStackTrace();
        }
        MarkerOptions options1 = new MarkerOptions().position(latLng).title("YOU ARE HERE").snippet("YOU ARE HERE");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        googleMap.addMarker(options1);
        latLng1 = new LatLng(reqlat,reqlon);
        MarkerOptions options = new MarkerOptions().position(latLng1).title(titl).snippet(titl);
        txt1.setText(titl);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng1));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 16));
        googleMap.addMarker(options);

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
