package com.example.encare.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val data: DataX?,

):Response()

data class DataX(
    @SerializedName("accountId")
    val accountId: Int?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("token")
    val token: String?

)



