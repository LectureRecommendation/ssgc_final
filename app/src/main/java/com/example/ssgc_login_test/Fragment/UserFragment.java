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

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private TextView anotherTextView;
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
        View anotherLayout = inflater.inflate(R.layout.fragment_home, container, false);
        TextView anotherTextView = anotherLayout.findViewById(R.id.textView);



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
                loadLectures();
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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://<YOUR_FLASK_SERVER_IP>:5000/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("year_input", Grade.getText().toString());
                    jsonParam.put("exclude_courses_input", Outclass.getText().toString());
                    jsonParam.put("include_courses_input", Inclass.getText().toString());
                    jsonParam.put("free_times_input", Emptytime.getText().toString());
                    jsonParam.put("major_count_input", Major.getText().toString());
                    jsonParam.put("liberal_arts_count_input", General.getText().toString());
                    // 다른 파라미터들 추가

                    Log.i("JSON", jsonParam.toString());
                    OutputStream os = conn.getOutputStream();
                    os.write(jsonParam.toString().getBytes("UTF-8"));
                    os.close();

                    // 응답 읽기
                    Scanner scanner = new Scanner(conn.getInputStream());
                    while(scanner.hasNext()) {
                        Log.i("RESPONSE", scanner.nextLine());
                    }
                    scanner.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    private void loadLectures() {
        ApiService apiService = RetrofitInstance.getApiService();

        apiService.getFilteredAndSortedLectures().enqueue(new Callback<List<Lecture>>() {

            @Override
            public void onResponse(Call<List<Lecture>> call, Response<List<Lecture>> response) {
                Log.i(TAG, "Response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    for (Lecture lecture : response.body()) {
                        anotherTextView.append(lecture.get강의명() + "\n");
                        anotherTextView.append(lecture.get교수명() + "\n");
                        anotherTextView.append(lecture.get강의시간() + "\n");
                    }
                }else {
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
