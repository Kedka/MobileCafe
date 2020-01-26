package com.example.mobilecafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.text.Line;

import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.mobilecafe.MainActivity.order;


public class AcceptOrderActivity extends AppCompatActivity {

    private FusedLocationProviderClient client;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_order);

        requestPermission();
        client  = LocationServices.getFusedLocationProviderClient(this);
         geocoder = new Geocoder(this, Locale.getDefault());

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
                            //TextView longtitudeTextView = findViewById(R.id.longtitude_text_view);
                            String strAdd ="";
                            try {
                                List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if(addressList!=null) {
                                    Address returnedAddress = addressList.get(0);
                                    StringBuilder strReturnedAddress = new StringBuilder("");

                                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                                    }
                                    strAdd = strReturnedAddress.toString();
                                    Log.d("LOCATION",strAdd);
                                }

                            } catch (Exception e){}

                            Log.d("LOCATION", location.toString());
//                            double latitude = location.getLatitude();
//                            double longtitude = location.getLongitude();
                            //String locationString = R.string.latitude + String.valueOf(latitude) + "\n" + R.string.longtitude + String.valueOf(longtitude);
                            //latitudeTextView.setText(String.valueOf(location.getLatitude()));
                            //longtitudeTextView.setText(String.valueOf(location.getLongitude()));
                            latitudeTextView.setText(strAdd);
                        }
                    }
                });
            }
        });

        Button buttonGoToPayment = findViewById(R.id.go_to_payment);
        buttonGoToPayment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                popupView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        popupWindow.dismiss();
                        order.deleteAll();
                        finish();
                        return true;
                    }
                });
            }

        });
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

}
