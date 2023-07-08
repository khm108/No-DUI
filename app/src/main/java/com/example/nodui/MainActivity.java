package com.example.nodui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    }
}
