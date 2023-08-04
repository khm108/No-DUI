package com.example.nodui;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
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

    private Button btnCall;
    private Button btnTmap;
    private Button btnSub;
    private Button btnMap;
    private BottomNavigationView bottomNavigationView;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 버튼 초기화
        btnCall = view.findViewById(R.id.btnCall);
        btnTmap = view.findViewById(R.id.btnTmap);
        btnSub = view.findViewById(R.id.btnSubway);
        btnMap = view.findViewById(R.id.btnMap);


        // 하단 바 초기화
        bottomNavigationView = requireActivity().findViewById(R.id.main_bnv);

        // 버튼 클릭 이벤트 처리
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialer("01052108291");
                // 대리운전 전화로 부르기

            }
        });

        btnTmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCallFragment();
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapFragment();
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

    private void openDialer(String phoneNumber) {
        // 전화 걸기 Intent 생성
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + phoneNumber));

        // 전화 걸기 Intent 실행
        startActivity(dialIntent);
    }
}