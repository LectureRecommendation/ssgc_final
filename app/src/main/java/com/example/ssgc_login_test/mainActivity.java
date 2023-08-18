package com.example.ssgc_login_test;

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
import com.example.ssgc_login_test.Fragment.UserFragment;
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
    Fragment fragment_cal;
    Fragment fragment_user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DB 생성
        DBHelper helper;
        SQLiteDatabase db;
        helper = new DBHelper(mainActivity.this, "newdb.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        // 프래그먼트 생성
        fragment_home = new HomeFragment();
        fragment_cal = new CalFragment();
        fragment_user = new UserFragment();



        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //아래 코드로 수정
        getSupportFragmentManager().beginTransaction().add(R.id.main_layout, new HomeFragment()).commit();
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
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_home).commitAllowingStateLoss();
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

