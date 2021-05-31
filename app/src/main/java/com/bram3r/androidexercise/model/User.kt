package com.bram3r.androidexercise.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    var name: String?,

    @SerializedName("birthdate")
    val birthdate: String?,

    @SerializedName("id")
    var id: Int,
)