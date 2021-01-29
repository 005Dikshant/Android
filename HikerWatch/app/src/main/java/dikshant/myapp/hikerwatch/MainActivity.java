package dikshant.myapp.hikerwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    LocationManager locationManager;
    LocationListener locationListener;
    TextView latitudeTextView,longitudeTextView,accuracyTextView,altitudeTextView,addressTextView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,10,locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeTextView = findViewById(R.id.latitudeTextView);
        longitudeTextView = findViewById(R.id.longitudeTextView);
        accuracyTextView = findViewById(R.id.accuracyTextView);
        altitudeTextView = findViewById(R.id.altitudeTextView);
        addressTextView = findViewById(R.id.addressTextView);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> listAddress = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    String latitude,longitude,accuracy,altitude,address = "Address: \n";

                    if(listAddress != null && listAddress.size() > 0){
                        altitude = "Altitude: "+ location.getAltitude();
                        accuracy = "Accuracy: " + location.getAccuracy();

                        if(listAddress.get(0).getLocality() != null){
                            address += listAddress.get(0).getLocality() + " ";
                        }

                        if(listAddress.get(0).getAdminArea() != null){
                            address += listAddress.get(0).getAdminArea() + " ";
                        }

                        if(listAddress.get(0).getPostalCode() != null){
                            address += listAddress.get(0).getPostalCode();
                        }

                        latitude = "Latitude: "+ listAddress.get(0).getLatitude();
                        longitude = "Longitude: "+ listAddress.get(0).getLongitude();

                        longitudeTextView.setText(longitude);
                        latitudeTextView.setText(latitude);
                        accuracyTextView.setText(accuracy);
                        altitudeTextView.setText(altitude);
                        addressTextView.setText(address);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,10,locationListener);
        }
    }
}