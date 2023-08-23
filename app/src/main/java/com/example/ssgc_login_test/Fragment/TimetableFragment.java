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


import androidx.fragment.app.Fragment;

import com.example.ssgc_login_test.DataStore;
import com.example.ssgc_login_test.Lecture;
import com.example.ssgc_login_test.R;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimetableFragment extends Fragment {

    private TableLayout timetableLayout;
    private String TAG = "프래그먼트";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);

        timetableLayout = view.findViewById(R.id.timetableLayout);

        //Map<String,String> dict = new HashMap<>()
        //if (lectures != null) {
            // 레이아웃에 데이터 표시하기
           // for (Lecture lecture : lectures) {
               // addLectureToTimetable(lecture);
           // }
       // }

        return view;
    }

    private void addLectureToTimetable(Lecture lecture) {
        // 강의 시간 정보 파싱
        String[] timeParts = lecture.get강의시간().split("/");
        String dayOfWeek = timeParts[0]; // "월", "화", ...
        int timeSlot = Integer.parseInt(timeParts[1].split("-")[0]);  // 항상 슬롯의 시작으로 가정

        // 해당 요일에 해당하는 TableRow 검색
        TableRow targetRow = findOrCreateDayRow(dayOfWeek, timeSlot);
        if (targetRow == null) {
            Log.e(TAG, "Failed to find or create TableRow for day: " + dayOfWeek + ", time slot: " + timeSlot);
            return; // 로그 후 메서드 종료
        }

        // 올바른 위치에 TextView 배치
        while (targetRow.getChildCount() <= timeSlot) {
            TextView emptyView = new TextView(getContext());
            emptyView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            targetRow.addView(emptyView);
        }

        // 강의 정보를 나타내는 TextView 생성 및 설정
        TextView lectureInfoTextView = new TextView(getContext());
        lectureInfoTextView.setText(lecture.get강의명());
        lectureInfoTextView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3));
        lectureInfoTextView.setGravity(Gravity.CENTER);

        // 기존 위치에 있는 뷰 (비어있는 TextView)를 제거하고 새로운 TextView를 추가
        targetRow.removeViewAt(timeSlot);
        targetRow.addView(lectureInfoTextView, timeSlot);
    }


    private TableRow findOrCreateDayRow(String dayOfWeek, int timeSlot) {
        // dayOfWeek을 영어 약어로 변환
        String dayAbbreviation = "";
        switch (dayOfWeek) {
            case "월":
                dayAbbreviation = "monday";
                break;
            case "화":
                dayAbbreviation = "tuesday";
                break;
            case "수":
                dayAbbreviation = "wednesday";
                break;
            case "목":
                dayAbbreviation = "thursday";
                break;
            case "금":
                dayAbbreviation = "friday";
                break;
        }

        // rowIndex를 계산할 때 요일과 시간 슬롯 모두 고려
        int rowIndex = -1;
        switch (dayAbbreviation) {
            case "monday":
                rowIndex = ((timeSlot - 1) * 5 + 1)/5 + 1;
                break;
            case "tuesday":
                rowIndex = ((timeSlot - 1) * 5 + 2)/5 + 1;
                break;
            case "wednesday":
                rowIndex = ((timeSlot - 1) * 5 + 3)/5 + 1;
                break;
            case "thursday":
                rowIndex = ((timeSlot - 1) * 5 + 4)/5 + 1;
                break;
            case "friday":
                rowIndex = ((timeSlot - 1) * 5 + 5)/5 + 1;
                break;
        }

        if (rowIndex != -1) {
            return (TableRow) timetableLayout.getChildAt(rowIndex);
        } else {
            return null;
        }
    }

}

