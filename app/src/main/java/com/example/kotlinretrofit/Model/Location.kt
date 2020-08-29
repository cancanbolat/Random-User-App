package com.example.kotlinretrofit.Model

import com.google.gson.annotations.SerializedName

data class Location (
    @SerializedName("city")
    var city: String,

    @SerializedName("state")
    var state: String

)