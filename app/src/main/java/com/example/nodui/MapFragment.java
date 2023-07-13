package com.example.nodui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

public class MapFragment extends Fragment{

    private TextView tvLatitude;
    private TextView tvLongitude;
    private TextView tvCurrentAddress;
    private TextView tvMyHomeLocation;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        tvLatitude = view.findViewById(R.id.tvLatitude);
        tvLongitude = view.findViewById(R.id.tvLongitude);
        tvCurrentAddress = view.findViewById(R.id.tvCurrentAddress);
        tvMyHomeLocation = view.findViewById(R.id.tvMyHomeLocation);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String crntLatitude = sharedPreferences.getString("crntLatitude", "");
        String crntLongitude = sharedPreferences.getString("crntLongitude", "");

        tvLatitude.setText("위도 : " + crntLatitude);
        tvLongitude.setText("경도 : " + crntLongitude);
        return view;
    }

}
