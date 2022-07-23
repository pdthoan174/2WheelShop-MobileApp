package com.example.encare.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class LoginResponse{
    @SerializedName("data")
    @Expose
    var data: User? = null

    @SerializedName("status")
    @Expose
    val status: String? = null

    @SerializedName("description")
    @Expose
    val description: String? = null


}
