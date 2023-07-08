package com.example.nodui;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment{

    private ImageButton btnMap;
    private ImageButton btnCall;
    private ImageButton btnMy;
    private BottomNavigationView bottomNavigationView;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 버튼 초기화
        btnMap = view.findViewById(R.id.btnMap);
        btnCall = view.findViewById(R.id.btnCall);
        btnMy = view.findViewById(R.id.btnMy);

        // 하단 바 초기화
        bottomNavigationView = requireActivity().findViewById(R.id.main_bnv);

        // 버튼 클릭 이벤트 처리
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapFragment();
                selectBottomNavigationItem(R.id.mapFragment);
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCallFragment();
                selectBottomNavigationItem(R.id.callFragment);
            }
        });

        btnMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyPageFragment();
                selectBottomNavigationItem(R.id.myFragment);
            }
        });

        return view;
    }

    private void openMapFragment() {
        // MapFragment로 이동
        MapFragment mapFragment = new MapFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frm, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openCallFragment() {
        // CallFragment로 이동
        CallFragment callFragment = new CallFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frm, callFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openMyPageFragment() {
        // MyPageFragment로 이동
        MyPageFragment myPageFragment = new MyPageFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frm, myPageFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void selectBottomNavigationItem(int itemId) {
        bottomNavigationView.post(new Runnable() {
            @Override
            public void run() {
                bottomNavigationView.setSelectedItemId(itemId);
            }
        });
    }
}