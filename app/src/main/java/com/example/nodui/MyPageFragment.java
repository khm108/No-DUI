package com.example.nodui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyPageFragment extends Fragment {

//    private EditText etName;
    private EditText etAddress;
    private Button btnSave;

    private TextView point;

    private SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = requireActivity().getSharedPreferences("MyPreferences", 0);

    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

//        etName = view.findViewById(R.id.etName);
        etAddress = view.findViewById(R.id.etAddress);
        btnSave = view.findViewById(R.id.btnSave);

        // 포인트 값 수정할 텍스트뷰 (예정)
        // point = view.findViewById(R.id.point);


        // 저장된 정보 불러오기
//        String savedName = preferences.getString("name", "");
        String savedAddress = preferences.getString("address", "");

//        etName.setText(savedName);
        etAddress.setText(savedAddress);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInformation();
            }
        });
        return view;
    }

    private void saveInformation() {
//        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        // 정보 저장
        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("name", name);
        editor.putString("address", address);
        editor.apply();

        Toast.makeText(requireContext(), "정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

}
