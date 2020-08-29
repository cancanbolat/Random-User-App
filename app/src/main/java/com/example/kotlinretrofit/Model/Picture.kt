package com.example.kotlinretrofit.Model

import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("large")
    var large: String
)