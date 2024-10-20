package com.example.myapplication

import okhttp3.ResponseBody
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface RetrofitAPI {
    @POST("LatLng")
    fun createPost(@Body body: Map<String, Double>): Call<ResponseBody>
}
