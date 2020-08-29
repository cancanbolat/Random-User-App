package com.example.kotlinretrofit.Model

import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("first")
    var first: String,

    @SerializedName("last")
    var last: String

)