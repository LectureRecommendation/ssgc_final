package com.example.ssgc_login_test.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        loadLectures();

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

    private void loadLectures() {
        ApiService apiService = RetrofitInstance.getApiService();

        apiService.getFilteredAndSortedLectures().enqueue(new Callback<List<Lecture>>() {

            @Override
            public void onResponse(Call<List<Lecture>> call, Response<List<Lecture>> response) {
                Log.i(TAG, "Response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    for (Lecture lecture : response.body()) {
                        textView.append(lecture.get강의명() + "\n");
                        textView.append(lecture.get교수명() + "\n");
                        textView.append(lecture.get강의시간() + "\n");
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
