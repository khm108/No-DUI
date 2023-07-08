package com.example.nodui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private MapFragment mapFragment;
    private CallFragment callFragment;
    private MyPageFragment myPageFragment;
    private BottomNavigationView bottomNavigationView;
    private LocationManager locationManager;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();
        callFragment = new CallFragment();
        myPageFragment = new MyPageFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm, homeFragment).commit();

        bottomNavigationView = findViewById(R.id.main_bnv);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm, homeFragment).commit();
                        item.setChecked(true); // 선택된 아이템에 대한 체크 상태 설정
                        return true;
                    case R.id.mapFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm, mapFragment).commit();
                        item.setChecked(true);
                        return true;
                    case R.id.callFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm, callFragment).commit();
                        item.setChecked(true);
                        return true;
                    case R.id.myFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm, myPageFragment).commit();
                        item.setChecked(true);
                        return true;
                }
                return false;
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkLocationPermission();
        startLocationService();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void startLocationService() {
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000,
                    0,
                    new GPSListener()
            );
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    class GPSListener implements LocationListener {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
        }

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            saveLocationToSharedPreferences(latitude, longitude);
        }

        private void saveLocationToSharedPreferences(double latitude, double longitude) {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("crntLatitude", String.valueOf(latitude));
            editor.putString("crntLongitude", String.valueOf(longitude));
            editor.apply();
        }
    }
}
