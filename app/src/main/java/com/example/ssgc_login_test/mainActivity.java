package com.example.ssgc_login_test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ssgc_login_test.Fragment.CalFragment;
import com.example.ssgc_login_test.Fragment.HomeFragment;
import com.example.ssgc_login_test.Fragment.TimetableFragment;
import com.example.ssgc_login_test.Fragment.UserFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class mainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "메인";

    // 프래그먼트 변수
    Fragment fragment_home;
    Fragment fragment_timetable;
    Fragment fragment_cal;
    Fragment fragment_user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //테이블 프레그먼트 추가
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.timetableLayout, new TimetableFragment());
        transaction.commit();

// DB에서 강의 정보 읽어오기
        DBHelper helper = new DBHelper(mainActivity.this, "mydb.db", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

// 강의 정보 삽입
        String lectureName = "컴퓨터 과학";
        String professorName = "John Doe";
        String lectureSchedule = "월/10-12,수/10-12";

        ContentValues values = new ContentValues();
        values.put("lecture_name", lectureName);
        values.put("professor_name", professorName);
        values.put("lecture_schedule", lectureSchedule);

        long rowId = db.insert("lecture_table", null, values);
        db.close();

        //db.close();

        // 프래그먼트 생성
        fragment_home = new HomeFragment();
        fragment_timetable = new TimetableFragment();
        fragment_cal = new CalFragment();
        fragment_user = new UserFragment();



        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //아래 코드로 수정
        getSupportFragmentManager().beginTransaction().add(R.id.main_layout, new TimetableFragment()).commit();
        // 초기 플래그먼트 설정
        //getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_home).commitAllowingStateLoss();


        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        // 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG, "바텀 네비게이션 클릭");

                switch (item.getItemId()){
                    case R.id.home:
                        Log.i(TAG, "home 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_timetable).commitAllowingStateLoss();
                        return true;
                    case R.id.cal:
                        Log.i(TAG, "cal 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_cal).commitAllowingStateLoss();
                        return true;
                    case R.id.user:
                        Log.i(TAG, "user 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_user).commitAllowingStateLoss();
                        return true;
                }
                return true;
            }
        });



    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Back to MainActivity after sign out.
                        startActivity(new Intent(mainActivity.this, LoginActivity.class));
                    }
                });
    }
}

