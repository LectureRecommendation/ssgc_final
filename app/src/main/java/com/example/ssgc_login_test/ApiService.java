package com.example.ssgc_login_test;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("filter_and_sort")
    Call<List<Lecture>> getFilteredAndSortedLectures();

}

