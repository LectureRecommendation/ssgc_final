package com.example.ssgc_login_test.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ssgc_login_test.ApiService;
import com.example.ssgc_login_test.Lecture;
import com.example.ssgc_login_test.LoginActivity;
import com.example.ssgc_login_test.R;
import com.example.ssgc_login_test.RetrofitInstance;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class HomeFragment extends Fragment {


    private TextView textView;
    private View view;
    private GoogleSignInClient mGoogleSignInClient;

    private String TAG = "프래그먼트";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        view = inflater.inflate(R.layout.fragment_home, container, false);

        textView = view.findViewById(R.id.textView);



        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // 프래그먼트에서는 `this` 대신 `getActivity()`를 사용해야 합니다.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

       // findViewById(R.id.sign_out_button).setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
               // signOut();
           //}
        //});
        // findViewById는 프래그먼트에서 직접 호출할 수 없으므로, 'view' 객체를 사용합니다.
        view.findViewById(R.id.sign_out_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        return view;
    }

    // 'private' 메서드는 'onCreateView' 메서드 내부에서 정의할 수 없습니다. 클래스 수준으로 이동시켜야 합니다.
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Back to MainActivity after sign out.
                        // 프래그먼트에서는 `this` 대신 `getActivity()`를 사용해야 합니다.
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                });
    }
}
