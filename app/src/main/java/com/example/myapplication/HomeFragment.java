package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    private Button btnMap;
    private Button btnCall;
    private Button btnMy;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 버튼 초기화
        btnMap = view.findViewById(R.id.btnMap);
        btnCall = view.findViewById(R.id.btnCall);
        btnMy = view.findViewById(R.id.btnMy);

        // 버튼 클릭 이벤트 처리
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapFragment();
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCallFragment();
            }
        });

        btnMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyPageFragment();
            }
        });

        return view;
    }

    private void openMapFragment() {
        // MapFragment 열기
        MapFragment mapFragment = new MapFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frm, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openCallFragment() {
        // CallFragment 열기
        CallFragment callFragment = new CallFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frm, callFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openMyPageFragment() {
        // MyPageFragment 열기
        MyPageFragment myPageFragment = new MyPageFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frm, myPageFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
