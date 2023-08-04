package com.example.nodui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.naver.maps.geometry.LatLng;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

public class WalkFragment extends Fragment {

    private TextView tvCurrentAddress;
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        tvCurrentAddress = view.findViewById(R.id.tvCurrentAddress);

        preferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String address = preferences.getString("address", "");

        tvCurrentAddress.setText("주소 : " + address);

        // 주소를 위도와 경도로 변환하는 메소드 호출
        getLatLngFromAddress(address);

        openHomeFragment();

        return view;
    }

    private void openHomeFragment() {
        // HomeFragment로 이동
        HomeFragment HomeFragment = new HomeFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frm, HomeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getLatLngFromAddress(String address) {
        new GetLatLngTask().execute(address);
    }

    private class GetLatLngTask extends AsyncTask<String, Void, LatLng> {

        @Override
        protected LatLng doInBackground(String... params) {
            String address = params[0];
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocationName(address, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address firstAddress = addresses.get(0);
                    double latitude = firstAddress.getLatitude();
                    double longitude = firstAddress.getLongitude();
                    return new LatLng(latitude, longitude);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(LatLng latLng) {
            super.onPostExecute(latLng);
            if (latLng != null) {
                // 위도와 경도를 String으로 저장하는 예시
                String latitudeStr = String.valueOf(latLng.latitude);
                String longitudeStr = String.valueOf(latLng.longitude);

                // 아래는 임시로 토스트 메시지로 위도와 경도를 출력하는 예시입니다.
//                String message = "위도: " + latitudeStr + ", 경도: " + longitudeStr;
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                Toast.makeText(getActivity(), "네이버 지도를 실행합니다", Toast.LENGTH_SHORT).show();


                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("latitude", latitudeStr);
                editor.putString("longitude", longitudeStr);
                editor.apply();

                // 내비게이션 인텐트 시작
                String address = preferences.getString("address", "");
                String uniAddress = "";
                try {
                    uniAddress = URLEncoder.encode(address, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }

                String url = "nmap://route/walk?dlat=" + latitudeStr + "&dlng=" + longitudeStr + "&dname=" + uniAddress + "&appname=com.example.nodui";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

            } else {
                Toast.makeText(getActivity(), "주소를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
