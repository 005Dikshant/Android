package dikshant.myapp.memorableplacesone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    LocationManager locationManager;
    LocationListener locationListener;

    private GoogleMap mMap;

    public void centerMapOnLocation(Location location, String title) {
        if (location != null) {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
//            Log.i("Latitude",Double.toString(location.getLatitude()));
//            Log.i("Longitude",Double.toString(location.getLongitude()));
//            Log.i("Hello",title);
            mMap.addMarker(new MarkerOptions().position(userLocation).title(title));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
                Location lastKnowLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Log.i("Hello", "Me");
                centerMapOnLocation(lastKnowLocation, "Your Locati74on");
            }
        }
    }

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

        mMap.setOnMapLongClickListener(this);
        Intent intent = getIntent();
        if (intent.getIntExtra("placeNumber", 0) == 0) {
            // Zoom in on user location
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    centerMapOnLocation(location, "Your Location");
                }
            };

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
                Location lastKnowLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Log.i("Hello", "World");
                centerMapOnLocation(lastKnowLocation, "Your Locati45on");
            } else {
                Log.i("Hello", "Us");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }else{
            Location placeLocation = new Location(LocationManager.GPS_PROVIDER);
            placeLocation.setLatitude(MainActivity.locations.get(intent.getIntExtra("placeNumber",0)).latitude);
            placeLocation.setLongitude(MainActivity.locations.get(intent.getIntExtra("placeNumber",0)).longitude);

            centerMapOnLocation(placeLocation,MainActivity.places.get(intent.getIntExtra("placeNumber",0)));
        }

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String address = "";

        try {
            List<Address> listAddress = geocoder.getFromLocation(latLng.latitude, latLng.latitude,1);

            if(listAddress != null && listAddress.size() > 0){
                if(listAddress.get(0).getThoroughfare() != null){
                    if(listAddress.get(0).getSubThoroughfare() != null){
                        address += listAddress.get(0).getSubThoroughfare() + " ";
                        Log.i("ADdress:", address);
                    }
                    address += listAddress.get(0).getThoroughfare();
                    Log.i("ADdresks", address);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(address.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            address += sdf.format(new Date());
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title(address));
        MainActivity.places.add(address);
        MainActivity.locations.add(latLng);

        MainActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = this.getSharedPreferences("dikshant.myapp.memorableplacesone",Context.MODE_PRIVATE);

        try {
            ArrayList<String> latitudes = new ArrayList<>();
            ArrayList<String> longitudes = new ArrayList<>();

            for(LatLng latLng1 : MainActivity.locations){
                latitudes.add(Double.toString(latLng1.latitude));
                longitudes.add(Double.toString(latLng1.longitude));
            }

            sharedPreferences.edit().putString("places",ObjectSerializer.serialize(MainActivity.places)).apply();

            sharedPreferences.edit().putString("lats",ObjectSerializer.serialize(latitudes)).apply();
            sharedPreferences.edit().putString("longs",ObjectSerializer.serialize(longitudes)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Location Saved", Toast.LENGTH_SHORT).show();
    }
}