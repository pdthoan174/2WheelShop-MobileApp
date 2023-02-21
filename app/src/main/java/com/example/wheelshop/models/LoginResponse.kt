package com.example.wheelshop.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("address")
    val address: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("gender")
    val gender: Boolean,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("registerDate")
    val registerDate: String?,
    @SerializedName("roles")
    val roles: List<String>,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("token")
    val token: String?,
    @SerializedName("type")
    val type: String?

    )




