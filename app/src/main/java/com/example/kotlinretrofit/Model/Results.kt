package com.example.kotlinretrofit.Model

import com.google.gson.annotations.SerializedName

data class Results (
    @SerializedName("gender")
    var gender: String,

    @SerializedName("name")
    var name: Name,

    @SerializedName("location")
    var location: Location,

    @SerializedName("email")
    var email: String,

    @SerializedName("picture")
    var picture: Picture

    )