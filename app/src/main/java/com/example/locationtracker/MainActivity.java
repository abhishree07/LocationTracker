package com.example.locationtracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.net.BindException;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

public class MainActivity extends AppCompatActivity {
    //INITIALIZE VARIABLE
    Button btLocation;
    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning value to variable
        btLocation = findViewById(R.id.bt_location);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);
        // initialize flp
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //check permission
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{"Manifest.permission.ACCESS_FINE_LOCATION"}, 44);
                }
            }
        }); }
        @SuppressLint("MissingPermission")
        private void getLocation(){
       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }*/
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


                        textView1.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Latitude:</b><br></font>" + addresses.get(0).getLatitude(),1));
                        textView2.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Longitude:</b><br></font>" + addresses.get(0).getLongitude(),1));
                        textView3.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Country Name:</b><br></font>" + addresses.get(0).getCountryName(),1));
                        textView4.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Locality:</b><br></font>" + addresses.get(0).getLocality(),1));
                        textView5.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Address Line:</b><br></font>" + addresses.get(0).getAddressLine(0),1));


                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }
        });
        getLocation();
    };
        }