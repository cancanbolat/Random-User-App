package com.example.kotlinretrofit.Model

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("results")
    var userList: ArrayList<Results>
)