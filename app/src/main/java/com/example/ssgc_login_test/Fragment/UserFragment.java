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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ssgc_login_test.ApiService;
import com.example.ssgc_login_test.ClassSelect;
import com.example.ssgc_login_test.Lecture;
import com.example.ssgc_login_test.PersonalSchedule;
import com.example.ssgc_login_test.R;
import com.example.ssgc_login_test.RetrofitInstance;
import com.example.ssgc_login_test.Settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import org.json.JSONObject;
import org.json.JSONException;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private ApiService apiService;
    private TextView lecturesListTextView;
    private String TAG = "프래그먼트";
    EditText Grade;
    EditText Outclass;
    EditText Inclass;
    EditText Major;
    EditText General;
    EditText Emptytime;

    Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        lecturesListTextView = view.findViewById(R.id.lectures_list_text_view);

        Grade = view.findViewById(R.id.edt_grade);
        Inclass = view.findViewById(R.id.edt_inclass);
        Outclass = view.findViewById(R.id.edt_outclass);
        Major = view.findViewById(R.id.edt_major);
        General = view.findViewById(R.id.edt_general);
        Emptytime = view.findViewById(R.id.edt_emptytime);

        submitButton = view.findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPostRequest();
            }
        });


        ImageButton btnSettings = view.findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Settings.class);
            startActivity(intent);
        });

        return view;
    }

    private void sendPostRequest() {
        ApiService apiService = RetrofitInstance.getApiService();

        // 데이터를 JSON으로 변환하고 RequestBody 생성
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("year_input", Grade.getText());// 여기에 POST 요청에 필요한 데이터를 추가하세요.
            jsonObject.put("exclude_courses_input", Outclass.getText());
            jsonObject.put("include_courses_input", Inclass.getText());
            jsonObject.put("free_times_input", Emptytime.getText());
            jsonObject.put("major_count_input", Major.getText());
            jsonObject.put("liberal_arts_count_input", General.getText());
        } catch (Exception e) {
            Log.e(TAG, "JSON Error: " + e.getMessage());
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), String.valueOf(jsonObject));

        apiService.postFilteredAndSortedLectures(requestBody).enqueue(new Callback<List<Lecture>>() {
            @Override
            public void onResponse(Call<List<Lecture>> call, Response<List<Lecture>> response) {
                if (response.isSuccessful()) {
                    // POST 요청에 대한 응답을 처리하는 로직을 여기에 추가하세요.
                    List<Lecture> lectures = response.body();
                    StringBuilder lecturesText = new StringBuilder();

                    // 강의 목록을 텍스트 형태로 변환
                    for (Lecture lecture : lectures) {
                        lecturesText.append(lecture.get강의명() + "\n");
                        lecturesText.append(lecture.get교수명() + "\n");
                        lecturesText.append(lecture.get강의시간() + "\n");
                    }
                    // TextView에 강의 목록 설정
                    lecturesListTextView.setText(lecturesText.toString());

                } else {
                    try {
                        Log.e(TAG, "Response error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "Error message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Lecture>> call, Throwable t) {
                Log.e(TAG, "Request failed: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
