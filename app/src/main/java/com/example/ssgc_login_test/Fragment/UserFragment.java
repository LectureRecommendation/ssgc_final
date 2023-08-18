package com.example.ssgc_login_test.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ssgc_login_test.ClassSelect;
import com.example.ssgc_login_test.PersonalSchedule;
import com.example.ssgc_login_test.R;
import com.example.ssgc_login_test.Settings;


public class UserFragment extends Fragment {

    String[] grades = {"15", "16", "17", "18", "19", "20", "21"};
    String[] schedule = {"공강 유무", "점심시간 확보", "오전 수업 위주", "오후 수업 위주"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        Spinner spinner1 = rootView.findViewById(R.id.sp_grade);
        Spinner spinner2 = rootView.findViewById(R.id.sp_schedule);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, grades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), grades[i] + "가 선택되었습니다", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, schedule);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), schedule[i] + "가 선택되었습니다", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btnClass = rootView.findViewById(R.id.btnClass);
        btnClass.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ClassSelect.class);
            startActivity(intent);
        });

        Button btnSchedule = rootView.findViewById(R.id.btnSchedule);
        btnSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PersonalSchedule.class);
            startActivity(intent);
        });

        ImageButton btnSettings = rootView.findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Settings.class);
            startActivity(intent);
        });

        return rootView;
    }
}
