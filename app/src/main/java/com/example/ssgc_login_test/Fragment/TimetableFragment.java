package com.example.ssgc_login_test.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ssgc_login_test.Lecture;
import com.example.ssgc_login_test.R;
import com.example.ssgc_login_test.RetrofitInstance;
import com.example.ssgc_login_test.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class TimetableFragment extends Fragment {

    private TableLayout timetableLayout;
    private String TAG = "프래그먼트";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);

        timetableLayout = view.findViewById(R.id.timetableLayout);

        // 강의 정보를 받아올 리스트 (API 호출로 변경)
        loadLectures();

        return view;
    }

    private void addLectureToTimetable(Lecture lecture) {
        // 강의 시간 정보 파싱
        String[] timeParts = lecture.get강의시간().split("/");
        String dayOfWeek = timeParts[0]; // "월", "화", ...
        String[] timeRanges = timeParts[1].split("-");

        // 해당 요일에 해당하는 TableRow 찾기 또는 생성
        TableRow dayRow = findOrCreateDayRow(dayOfWeek);

        // TableRow에 강의 정보를 표시할 TextView 생성
        TextView lectureInfoTextView = new TextView(getContext());
        lectureInfoTextView.setText(lecture.get강의명() + "\n" + lecture.get교수명());
        // 시간표 셀의 너비와 높이 설정 (예시로 고정값 사용)
        lectureInfoTextView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3));
        lectureInfoTextView.setGravity(Gravity.CENTER);

        // TableRow에 TextView 추가
        dayRow.addView(lectureInfoTextView);
    }

    private TableRow findOrCreateDayRow(String dayOfWeek) {
        for (int i = 1; i < timetableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) timetableLayout.getChildAt(i);
            TextView dayTextView = (TextView) row.getChildAt(0);
            if (dayTextView.getText().toString().equals(dayOfWeek)) {
                return row; // 요일에 해당하는 TableRow가 이미 있으면 반환
            }
        }

        // 요일에 해당하는 TableRow가 없으면 생성
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // 요일을 나타내는 TextView 추가
        TextView dayTextView = new TextView(getContext());
        dayTextView.setText(dayOfWeek);
        dayTextView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        dayTextView.setGravity(Gravity.CENTER);
        newRow.addView(dayTextView);

        // 시간표 테이블 레이아웃에 새로 생성한 TableRow 추가
        timetableLayout.addView(newRow);
        return newRow;
    }

    // 강의 정보를 받아오는 메서드 (API 호출)
    private void loadLectures() {
        ApiService apiService = RetrofitInstance.getApiService();

        apiService.getFilteredAndSortedLectures().enqueue(new Callback<List<Lecture>>() {

            @Override
            public void onResponse(Call<List<Lecture>> call, Response<List<Lecture>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Lecture> lectureList = response.body();

                    // 시간표에 강의 정보를 배치
                    for (Lecture lecture : lectureList) {
                        addLectureToTimetable(lecture);
                    }
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

