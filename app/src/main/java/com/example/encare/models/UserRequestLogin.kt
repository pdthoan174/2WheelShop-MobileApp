package com.example.encare.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserRequestLogin {
    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null
}