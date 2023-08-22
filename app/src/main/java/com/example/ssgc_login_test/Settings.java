package com.example.ssgc_login_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ssgc_login_test.Fragment.UserFragment;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_settings);

        // 뒤로가기 버튼에 클릭 리스너 추가
        ImageButton btnPrev = findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // 기본 뒤로가기 동작 실행
            }
        });
    }

    @Override
    public void onBackPressed() {
        // 프래그먼트 매니저를 통해 프래그먼트 교체
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // UserFragment로 교체
        UserFragment userFragment = new UserFragment();
        fragmentTransaction.replace(R.id.fragment_user, userFragment); // fragment_container는 프래그먼트를 담을 레이아웃의 ID입니다.
        fragmentTransaction.addToBackStack(null); // 뒤로 가기 버튼을 눌렀을 때 이전 프래그먼트로 돌아갈 수 있도록 추가

        fragmentTransaction.commit();
    }
}

