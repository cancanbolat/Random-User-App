package com.example.kotlinretrofit.Data

import com.example.kotlinretrofit.Model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    fun getUsers(@Query("results") result: Int): Call<UserResponse>

}