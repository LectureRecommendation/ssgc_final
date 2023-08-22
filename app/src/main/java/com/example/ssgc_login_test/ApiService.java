package com.example.ssgc_login_test;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @GET("filter_and_sort")
    Call<List<Lecture>> getFilteredAndSortedLectures();

    @Headers("Content-Type: application/json")
    @POST("filter_and_sort")
    Call<List<Lecture>> postFilteredAndSortedLectures(@Body RequestBody requestBody);


}

