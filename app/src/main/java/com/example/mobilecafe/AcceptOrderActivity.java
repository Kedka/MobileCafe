package com.example.mobilecafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class AcceptOrderActivity extends AppCompatActivity {

    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_order);

        requestPermission();
        client  = LocationServices.getFusedLocationProviderClient(this);

        Button button = findViewById(R.id.get_location);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(ActivityCompat.checkSelfPermission(AcceptOrderActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(AcceptOrderActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!= null){
                            TextView latitudeTextView = findViewById(R.id.latitude_text_view);
                            TextView longtitudeTextView = findViewById(R.id.longtitude_text_view);
                            Log.d("LOCATION", location.toString());
//                            double latitude = location.getLatitude();
//                            double longtitude = location.getLongitude();
                            //String locationString = R.string.latitude + String.valueOf(latitude) + "\n" + R.string.longtitude + String.valueOf(longtitude);
                            latitudeTextView.setText(String.valueOf(location.getLatitude()));
                            longtitudeTextView.setText(String.valueOf(location.getLongitude()));
                        }
                    }
                });
            }
        });
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

}
