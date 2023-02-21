package com.example.wheelshop.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserRequestLogin {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null
}