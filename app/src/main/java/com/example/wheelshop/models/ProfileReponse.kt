package com.example.wheelshop.models

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    // name trong api: phai trung voi api
    @SerializedName("address")
    val address: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: Boolean,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("registerDate")
    val registerDate: String,
    @SerializedName("roles")
    val roles: List<Role>,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("token")
    val token: String,
    @SerializedName("userId")
    val userId: Int
)


data class Role(
    val id: Int,
    val name: String
)